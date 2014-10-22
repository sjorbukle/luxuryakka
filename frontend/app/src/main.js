require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-route',
    'controllers/controllers'
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
                templateUrl: 'src/views/dashboard.html',
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
