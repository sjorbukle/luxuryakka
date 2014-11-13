requirejs.config({
    baseUrl: './src',
    paths: {
        angular: '../bower_components/angular/angular',
        'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
        'angular-resource': '../bower_components/angular-resource/angular-resource',
        'angular-route': '../bower_components/angular-route/angular-route',
        'angular-sanitize': [
            '//ajax.googleapis.com/ajax/libs/angularjs/1.3.2/angular-sanitize'
        ],
        jquery: '../bower_components/jquery/dist/jquery',
        envconfig: 'env-config',

        user: 'user/module',
        userFactory: 'user/factory',
        userController: 'user/controller',

        authentication: 'authentication/module',
        authenticationFactory: 'authentication/factory',

        administration: 'administration/module',
        organizationStructure: 'administration/organization-structure/module',
        organizationStructureFactory: 'administration/organization-structure/factory',
        organizationStructureController: 'administration/organization-structure/controller',
        generalSettings: 'administration/general-settings/module',

        requirejs: '../bower_components/requirejs/require',
        textAngular: [
            '//cdnjs.cloudflare.com/ajax/libs/textAngular/1.2.2/textAngular.min'
        ],
        'bootstrap-sass': '../bower_components/bootstrap-sass/dist/js/bootstrap',
        underscore: '../bower_components/underscore/underscore',
        'angular-ui-select': '../bower_components/angular-ui-select/dist/select'
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
        'angular-route': [
            'angular'
        ],
        'angular-sanitize': [
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
