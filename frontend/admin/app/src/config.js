requirejs.config({
    baseUrl: './src',
    paths: {
        angular: '../bower_components/angular/angular',
        'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
        'angular-resource': '../bower_components/angular-resource/angular-resource',
        'angular-route': '../bower_components/angular-route/angular-route',
        jquery: '../bower_components/jquery/dist/jquery',
        envconfig: 'services/env-config',
        user: 'user/module',
        userFactory: 'user/factory',
        userController: 'user/controller',
        authentication: 'authentication/module',
        authenticationFactory: 'authentication/factory',
        administration: 'administration/module',
        organizationStructure: 'administration/organization-structure/module',
        organizationStructureFactory: 'administration/organization-structure/factory',
        organizationStructureController: 'administration/organization-structure/controller',
        organizationStructureDirectives: 'administration/organization-structure/directives',
        generalSettings: 'administration/general-settings/module',
        generalSettingsFactory: 'administration/general-settings/factory',
        generalSettingsController: 'administration/general-settings/controller',
        requirejs: '../bower_components/requirejs/require',
        'bootstrap-sass': '../bower_components/bootstrap-sass/dist/js/bootstrap',
        underscore: '../bower_components/underscore/underscore',
        'angular-ui-select': '../bower_components/angular-ui-select/dist/select',
        'angular-sanitize': '../bower_components/angular-sanitize/angular-sanitize',
        textAngular: '../bower_components/textAngular/dist/textAngular.min'
    },
    shim: {
        angular: {
            exports: 'angular',
            deps: [
                'jquery'
            ]
        },
        'angular-mocks': [
            'angular'
        ],
        'angular-resource': [
            'angular'
        ],
        'angular-sanitize': [
            'angular'
        ],
        'angular-route': [
            'angular'
        ],
        'angular-ui-select': {
            deps: [
                'angular'
            ]
        },
        textAngular: [
            'angular',
            'angular-sanitize'
        ],
        'bootstrap-sass': {
            deps: [
                'jquery'
            ]
        }
    },
    packages: [

    ]
});
