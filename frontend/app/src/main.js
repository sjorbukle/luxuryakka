require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-route',
    'home/home'
], function(angular) {
    'use strict';

    /*App Module*/
    angular.module('webApp', [
        'ngRoute',
        'ngResource',
        'app.controllers'
    ])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'src/home/dashboard.html',
                controller: 'MainCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    })
    .run( function () {
        console.log('Started App')
    });
});
