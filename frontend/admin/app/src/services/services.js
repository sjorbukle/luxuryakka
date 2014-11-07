define(['angular'], function(angular) {
    angular.module('com.laplacian.luxuryakka.services', [
        'com.laplacian.luxuryakka.jwt_services',
        'envconfig'
    ])
        .factory('luxuryakka', ['$q', '$http', 'TokenInspector', 'ENV',
            function ($q, $http, TokenInspector, ENV) {
                var baseBackendUrl = ENV.apiEndpoint;
                return {
                    registerAccount: function (payload) {  // has no token in headers
                        return $http.post(baseBackendUrl + '/api/v1/users', angular.toJson(payload))
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
                    },
                    getAllOrganizationStructureByParent: function (parentId) {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/organization-structure/all/parent/' + parentId,
                                    method: 'GET',
                                    headers: { Authorization: token }
                                })
                                    .error(function (reason, code) {
                                        console.log('Error: getOrganizationStructureByParent' + JSON.stringify(reason) + ' Code:' + code);
                                        return reason;
                                    })
                                    .then(function (res) {
                                        return res.data;
                                    });
                            });
                    },
                    getAllOrganizationStructureParentLess: function () {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/organization-structure/all/parent',
                                    method: 'GET',
                                    headers: { Authorization: token }
                                })
                                    .error(function (reason, code) {
                                        console.log('Error: getAllOrganizationStructureParentLess' + JSON.stringify(reason) + ' Code:' + code);
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


