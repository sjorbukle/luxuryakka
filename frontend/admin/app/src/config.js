requirejs.config({
    baseUrl: './src',
    paths: {
        angular: '../bower_components/angular/angular',
        'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
        'angular-resource': '../bower_components/angular-resource/angular-resource',
        'angular-route': '../bower_components/angular-route/angular-route',
        jquery: '../bower_components/jquery/dist/jquery',
        jwt_services: 'services/jwt_services',
        services: 'services/services',
        envconfig: 'services/env-config',
        controllers: 'controllers/controllers',
        requirejs: '../bower_components/requirejs/require',
        'bootstrap-sass': '../bower_components/bootstrap-sass/dist/js/bootstrap',
        underscore: '../bower_components/underscore/underscore'
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
        'bootstrap-sass': {
            deps: [
                'jquery'
            ]
        }
    },
    packages: [

    ]
});
