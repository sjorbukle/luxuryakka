define(['angular'], function(angular) {
    angular.module('luxuryakka.organizationStructureBackend')
        .factory('orgStructureService', ['$q', '$http', 'TokenInspector', 'ENV',
            function ($q, $http, TokenInspector, ENV) {
                var baseBackendUrl = ENV.apiEndpoint;
                return {
                    createOrganizationStructure: function (formJson) {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/organization-structure',
                                    method: 'POST',
                                    headers: { Authorization: token },
                                    data: angular.toJson(formJson)
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
                    getOrganizationStructureById: function (id) {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/organization-structure/single/' + id,
                                    method: 'GET',
                                    headers: { Authorization: token }
                                })
                                    .error(function (reason, code) {
                                        console.log('Error: getOrganizationStructureById' + JSON.stringify(reason) + ' Code:' + code);
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
                    },
                    getAllOrganizationStructureTypes: function () {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/organization-structure/type/all',
                                    method: 'GET',
                                    headers: { Authorization: token }
                                })
                                    .error(function (reason, code) {
                                        console.log('Error: getAllOrganizationStructureTypes' + JSON.stringify(reason) + ' Code:' + code);
                                        return reason;
                                    })
                                    .then(function (res) {
                                        return res.data;
                                    });
                            });
                    },
                    getAllOrganizationStructureByType: function (type) {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/organization-structure/all/type/' + type,
                                    method: 'GET',
                                    headers: { Authorization: token }
                                })
                                    .error(function (reason, code) {
                                        console.log('Error: getAllOrganizationStructureTypes' + JSON.stringify(reason) + ' Code:' + code);
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


