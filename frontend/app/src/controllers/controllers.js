define(['angular'], function(angular) {
    angular.module('app.controllers', [])
    .controller('MainCtrl', ['$scope', function($scope) {
        $scope.pageTitle = 'JMedic';
        }])
    .controller('Widget', ['$scope', function($scope) {
    }])
});
