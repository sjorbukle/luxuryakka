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

    requirejs.config({
    baseUrl: './src',
    ,

    // ask Require.js to load these files (all our tests)
    deps: allTestFiles,

    // start test run, once Require.js is done
    callback: window.__karma__.start
});
