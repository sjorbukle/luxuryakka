define(['angular'], function(angular) {
    angular.module('luxuryakka.organizationStructureBackend')
        .factory('orgStructureService', ['$q', '$http', 'TokenInspector', 'ENV',
            function ($q, $http, TokenInspector, ENV) {
                var baseBackendUrl = ENV.apiEndpoint;
                return {
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


