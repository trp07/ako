akoApp.config(function($stateProvider, $urlServiceProvider, $urlRouterProvider) {
  $urlServiceProvider.rules.otherwise({ state: 'login' });
  
  $stateProvider.state('login', {
    url: '/login',
    component: 'login',
  });
});