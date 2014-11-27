define(['angular'], function(angular) {
    angular.module('luxuryakka.generalSettings')
        .controller('GeneralSettingsCtrl', ['$scope', '$routeParams', 'generalSettingsService',
            function($scope, $routeParams, generalSettingsService) {

              $scope.settings = {};

              generalSettingsService.getGeneralSettings()
                .then(function (result) {
                    $scope.settings.general = angular.copy(result.data);
                });

            }])
});
