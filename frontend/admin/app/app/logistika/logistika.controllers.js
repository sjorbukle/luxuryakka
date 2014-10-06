/**
 * Created by Laplacian d.o.o. on 5/14/14.
 */

angular.module('logistika.core')
.controller('GeneralSettingsCtrl', ['$scope', function ($scope) {
    $scope.token = 'navigation@app tOkEN';
    console.log($scope.token);
}]);
