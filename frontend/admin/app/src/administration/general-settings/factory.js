define(['angular'], function(angular) {
    angular.module('luxuryakka.generalSettingsBackend')
        .factory('generalSettingsService', ['$q', '$http', 'TokenInspector', 'ENV',
            function ($q, $http, TokenInspector, ENV) {
                var baseBackendUrl = ENV.apiEndpoint;
                return {
                    getGeneralSettings: function () {
                        return TokenInspector.getOrRefreshToken()
                            .then(function (token) {
                                return $http({
                                    url: baseBackendUrl + '/api/v1/general-settings',
                                    method: 'GET',
                                    headers: { Authorization: token }
                                })
                                    .error(function (reason, code) {
                                        console.log('Error: getGeneralSettings' + JSON.stringify(reason) + ' Code:' + code);
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


