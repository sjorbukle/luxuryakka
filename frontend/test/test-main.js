var allTestFiles = [];
var TEST_REGEXP = /app\/src\/\w*\/\w*\.spec\.js$/;

var pathToModule = function(path) {
  return path.replace(/^\/base\//, '../../').replace(/\.js$/, '');
};

Object.keys(window.__karma__.files).forEach(function(file) {
  if (TEST_REGEXP.test(file)) {
    // Normalize paths to RequireJS module names.
    allTestFiles.push(pathToModule(file));
  }
});

requirejs.config({
    // Karma serves files from '/app'
    baseUrl: 'base/app/src',

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
        'controllers': 'controllers/controllers',
        'bootstrap': '../bower_components/bootstrap-sass-official/assets/javascripts/bootstrap'
        /*--insert code tag--do not remove*/
    },
    shim: {
        'angular': { exports: 'angular', deps: ['jquery'] },
        'angular-mocks': ['angular'],
        'angular-resource': ['angular'],
        'angular-route': ['angular'],
        'bootstrap': { exports: 'bootstrap', deps: ['jquery'] }
    },

    // ask Require.js to load these files (all our tests)
    deps: allTestFiles,

    // start test run, once Require.js is done
    callback: window.__karma__.start
});
