akoApp.config(function($stateProvider, $urlServiceProvider, $urlRouterProvider) {
  $urlServiceProvider.rules.otherwise({ state: 'login' });
  
  $stateProvider.state('login', {
    url: '/login',
    component: 'login',
  });
  $stateProvider.state('home', {
    url: '/home',
    component: 'home',
  });
  $stateProvider.state('userList', {
	    url: '/userList',
	    component: 'userList'
  });
  $stateProvider.state('userProfile', {
      url: '/userProfile',
      component: 'userProfile'
  });
});