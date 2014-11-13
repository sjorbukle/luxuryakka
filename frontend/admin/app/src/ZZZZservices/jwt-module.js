define(['angular'], function(angular) {
    angular.module('luxuryakka.service.jwtModule', ['envconfig'])
        .factory('Helper',
        function () {
            return {
                decodeBase64: function (data) {
                    var b64 = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
                    var o1, o2, o3, h1, h2, h3, h4, bits, i = 0,
                        ac = 0,
                        tmp_arr = [];

                    if (!data) {
                        return data;
                    }

                    data += '';

                    do {
                        // unpack four hexets into three octets using index points in b64
                        h1 = b64.indexOf(data.charAt(i++));
                        h2 = b64.indexOf(data.charAt(i++));
                        h3 = b64.indexOf(data.charAt(i++));
                        h4 = b64.indexOf(data.charAt(i++));

                        bits = h1 << 18 | h2 << 12 | h3 << 6 | h4;

                        o1 = bits >> 16 & 0xff;
                        o2 = bits >> 8 & 0xff;
                        o3 = bits & 0xff;

                        if (h3 == 64) {
                            tmp_arr[ac++] = String.fromCharCode(o1);
                        } else if (h4 == 64) {
                            tmp_arr[ac++] = String.fromCharCode(o1, o2);
                        } else {
                            tmp_arr[ac++] = String.fromCharCode(o1, o2, o3);
                        }
                    } while (i < data.length);

                    return decodeURIComponent(escape(tmp_arr.join('').replace(/\0+$/, '')));
                },
                deserializeJWT: function (token) {
                    var tokenComponents = token.split('.');
                    return JSON.parse(this.decodeBase64(tokenComponents[1]));
                }
            };
        })
        .factory('TokenInspector', ['$q', 'REFRESH_TOKEN_VALID_TIME', 'TOKEN', 'Helper', 'ENV', '$http',
            function ($q, REFRESH_TOKEN_VALID_TIME, TOKEN, Helper, ENV, $http) {
                return {
                    getOrRefreshToken: function () {
                        var deferred = $q.defer();
                        var token = localStorage.getItem(TOKEN);

                        if (token) {
                            var payload = Helper.deserializeJWT(token);

                            var expirationTimeStamp = new Date(payload.expiration);
                            var currentTimeStamp = new Date;

                            if (expirationTimeStamp - currentTimeStamp > REFRESH_TOKEN_VALID_TIME) {
                                deferred.resolve(token);
                            } else {
                                var currentToken = {
                                    token: token
                                };
                                console.log('Refresh token!');
                                $http.post(ENV.apiEndpoint + '/api/refresh-token', angular.toJson(currentToken))
                                    .error(function (reason, code) {
                                        console.log('Error: refreshToken' + JSON.stringify(reason) + ' Code:' + code);
                                        deferred.reject('Refused to get refresh token!');
                                    })
                                    .then(function (response) {
                                        var token = response.data.data.token;
                                        localStorage.setItem(TOKEN, token);
                                        deferred.resolve(token);
                                    });
                            }
                        } else {
                            deferred.reject('Token is missing!');
                        }
                        return deferred.promise;
                    }
                }
            }]);
});