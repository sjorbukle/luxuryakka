angular.module('com.laplacian.luxuryakka.services', [
    'com.laplacian.luxuryakka.jwt_services',
    'com.laplacian.luxuryakka.constants'
])
    .factory('luxuryakka', ['$q', '$http', 'TokenInspector', 'CLOUD',
        function ($q, $http, TokenInspector, CLOUD) {
            var baseBackendUrl = CLOUD;
            return {
                registerAccount: function (payload) {  // has no token in headers
                    return $http.post(baseBackendUrl + '/api/v1/user', angular.toJson(payload))
                        .error(function (reason, code) {
                            console.log('Error: registerAccount ' + JSON.stringify(reason) + ' Code:' + code);
                        })
                        .then(function (res) {
                            return res.data;
                        });
                },
                authenticate: function (username, password) {  // has no token in headers
                    var payload = {
                        username: username,
                        password: password
                    };
                    return $http.post(baseBackendUrl + '/api/authenticate', angular.toJson(payload))
                        .error(function (reason, code) {
                            console.log('Error: authenticate ' + JSON.stringify(reason) + ' Code:' + code);
                            return reason;
                        })
                        .then(function (res) {
                            return res.data;
                        });
                },
                refreshToken: function (token) { // has no token in headers
                    var payload = {
                        token: token
                    };
                    return $http.post(baseBackendUrl + '/api/refresh-token', angular.toJson(payload))
                        .error(function (reason, code) {
                            console.log('Error: refreshToken ' + JSON.stringify(reason) + ' Code:' + code);
                        })
                        .then(function (res) {
                            return res.data;
                        });
                },
                getUserById: function (id) {
                    return TokenInspector.getOrRefreshToken()
                        .then(function (token) {
                            return $http({
                                url: baseBackendUrl + '/api/v1/user/' + id,
                                method: 'GET',
                                headers: { Authorization: token }
                            })
                                .error(function (reason, code) {
                                    console.log('Error: getUserById' + JSON.stringify(reason) + ' Code:' + code);
                                    return reason;
                                })
                                .then(function (res) {
                                    return res.data;
                                });
                        });
                }
            }
        }]);


