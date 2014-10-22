requirejs.config({
    baseUrl: './src',
    paths: {
        'angular': '../bower_components/angular/angular',
        /*require angular mocks for testing*/
        'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
        /*require angular resource for easily handling sending and receiving request*/
        'angular-resource': '../bower_components/angular-resource/angular-resource',
        'angular-route': '../bower_components/angular-route/angular-route',
        /*require jquery*/
        'jquery': '../bower_components/jquery/dist/jquery',
        'constants': 'services/constants',
        'jwt_services': 'services/jwt_services',
        'services': 'services/services',
        'controllers': 'controllers/controllers'
        /*--insert code tag--do not remove*/
    },
    shim: {
        'angular': { exports: 'angular', deps: ['jquery'] },
        'angular-mocks': ['angular'],
        'angular-resource': ['angular'],
        'angular-route': ['angular']
    }/*--requirejs config copy tag--do not remove*/
});
