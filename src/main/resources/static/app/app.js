var akoApp = angular.module("akoApp", ['ngMaterial', 'ui.router', 'angular-jwt', 'ngMaterialAccordion', 'angular-storage', 'ngResource', 'textSizeSlider']);

akoApp.constant('BASE_URL', 'http://localhost:8080');


akoApp.config(function ($httpProvider, $mdDateLocaleProvider, jwtOptionsProvider, $resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;

    // Please note we're annotating the function so that the $injector works when the file is minified
    jwtOptionsProvider.config({
        whiteListedDomains: ['api.myapp.com', 'localhost'],
        tokenGetter: function ($state, $http, jwtHelper, store, authService) {
            var jwt = store.get('access_token');
            var refreshToken = store.get('refresh_token');
            if (jwt && jwtHelper.isTokenExpired(jwt)) {

                authService.logout();

                /* return $http({
                     url: '/delegation',
                     // This will not send the JWT for this call
                     skipAuthorization: true,
                     method: 'POST',
                     refresh_token: refreshToken
                 }).then(function (response) {
                     store.set('JWT', response.data.jwt);
                     return jwt;
                 });*/
            } else {
                return jwt;
            }
        }
    });
    $httpProvider.interceptors.push('jwtInterceptor');
})
