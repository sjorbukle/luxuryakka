require([
    'angular',
    'jquery',
    'angular-resource',
    'angular-sanitize',
    'angular-route',
    'envconfig',

    'jwtModule',
    'coreServiceModule',
    'orgStructureServiceModule',

    'coreControllerModule',
    'orgStructureControllerModule',

    'bootstrap-sass',
    'underscore',
    'angular-ui-select',
    'textAngular'
], function(angular) {
    'use strict';

    /*App Module*/
    angular.element(document).ready(function () {
        /*smart works go here*/
        var $html = angular.element('html');

    angular.module('webApp', [
        'ngRoute',
        'ngResource',
        'ngSanitize',

        'luxuryakka.controller.coreModule',
        'luxuryakka.controller.orgStructureModule',

        'envconfig',
        'ui.select',

        'luxuryakka.service.jwtModule',
        'luxuryakka.service.coreModule',
        'luxuryakka.service.orgStructureModule',

        'textAngular'
    ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
        .when('/profile', {
            templateUrl: 'src/views/profile.html',
            controller: 'ProfileCtrl'
        })
        .when('/dashboard', {
            templateUrl: 'src/views/dashboard.html'
        })
        .when('/administration', {
            templateUrl: 'src/views/administration.html'
        })
        .when('/administration/organization-structure', {
            templateUrl: 'src/views/organizationstructure/view.html',
            controller: 'OrgStructureFirstCallCtrl'
        })
        .when('/administration/organization-structure/create', {
            templateUrl: 'src/views/organizationstructure/create.html',
            controller: 'OrgStructureCreateCtrl'
        })
        .when('/administration/organization-structure/:parentId', {
            templateUrl: 'src/views/organizationstructure/view.html',
            controller: 'OrgStructureCtrl'
        })
        .when('/administration/general-settings', {
            templateUrl: 'src/views/generalsettings/general-settings.html'
        })
        .when('/register', {
            templateUrl: 'src/views/register.html',
            controller: 'RegisterCtrl'
        })
        .when('/login', {
            templateUrl: 'src/views/login.html',
            controller: 'LoginCtrl'
        })
        .when('/logout', {
            template: '',
            controller: 'LogoutCtrl'
        })
        .otherwise({
            redirectTo: '/login'
        });
    }])
    .run(['$rootScope', '$location', 'TOKEN', '$timeout', '$templateCache', function ($rootScope, $location, TOKEN, $timeout, $templateCache) {
            $rootScope.$on('$routeChangeStart', function (event, next) {
                var token = localStorage.getItem(TOKEN);
                if (!token && !(next.templateUrl == 'src/views/register.html' || next.templateUrl == 'src/views/login.html')) {
                    event.preventDefault();
                    $timeout(function () {
                        $location.path('/login').replace();
                    });
                } else {
                    if (!$rootScope.hasOwnProperty('userSet')) {
                        $rootScope.userSet = token;
                    }
                }
            });


            var history = [];
            $rootScope.$on('$routeChangeSuccess', function() {
                history.push($location.$$path);
            });

            $rootScope.back = function () {
                var prevUrl = history.length > 1 ? history.splice(-2)[0] : "/";
                $location.path(prevUrl);
            };

            console.log( $templateCache.get('select2/select.tpl.html') );

            $('#textAngular-editableFix-010203040506070809').hide(); // FIXME: remove this when lib gets updated. It prevents white pixel bug.

        }]);

    /*bootstrap model*/
    angular.bootstrap($html, ['webApp']);
    });
});
