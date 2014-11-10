requirejs.config({
    baseUrl: './src',
    paths: {
        angular: '../bower_components/angular/angular',
        'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
        'angular-resource': '../bower_components/angular-resource/angular-resource',
        'angular-route': '../bower_components/angular-route/angular-route',
        'angular-sanitize': ['//ajax.googleapis.com/ajax/libs/angularjs/1.3.2/angular-sanitize'],
        jquery: '../bower_components/jquery/dist/jquery',
        jwt_services: 'services/jwt_services',
        services: 'services/services',
        envconfig: 'services/env-config',
        controllers: 'controllers/controllers',
        requirejs: '../bower_components/requirejs/require',
        textAngular: ['//cdnjs.cloudflare.com/ajax/libs/textAngular/1.2.2/textAngular.min'],
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
        'angular-sanitize': [
            'angular'
        ],
        'bootstrap-sass': {
            deps: [
                'jquery'
            ]
        },
        textAngular: ['angular','angular-sanitize']
    },
    packages: [

    ]
});
