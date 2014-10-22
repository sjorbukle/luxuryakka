define(['angular'], function(angular) {
    angular.module('app.controllers', [])
    .controller('MainCtrl', ['$scope', function($scope) {
        $scope.pageTitle = 'Luxury Akka';
    }])
    .controller('Widget', ['$scope', function($scope) {
    }])
});
