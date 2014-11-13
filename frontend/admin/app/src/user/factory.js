define(['angular'], function(angular) {
    angular.module('luxuryakka.userBackend')
        .factory('userService', ['$q', '$http', 'TokenInspector', 'ENV',
            function ($q, $http, TokenInspector, ENV) {
                var baseBackendUrl = ENV.apiEndpoint;
                return {
                    registerAccount: function (payload) {
                        return $http.post(baseBackendUrl + '/api/v1/users', angular.toJson(payload))
                            .error(function (reason, code) {
                                console.log('Error: registerAccount ' + JSON.stringify(reason) + ' Code:' + code);
                            })
                            .then(function (res) {
                                return res.data;
                            });
                    },
                    getUserById: function (id) {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/users/' + id,
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
});


