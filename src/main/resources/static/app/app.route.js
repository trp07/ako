akoApp.config(function ($stateProvider, $urlServiceProvider, $urlRouterProvider) {
    $urlServiceProvider.rules.otherwise({
        state: 'login'
    });

    $stateProvider.state('login', {
        url: '/login',
        component: 'login',
    });

    $stateProvider.state('index', {
        abstract: true,
        //url: '/',
        views: {
            '@': {
                component: 'layout',
            },
            'top@index': {
                templateUrl: '/app/component/layout/tpl.top.html',
            },
            'left@index': {
                component: 'sidenavComponent',
            },
            'main@index': {
                templateUrl: '/app/component/layout/tpl.main.html',
            },
        },
    });
    $stateProvider.state('home', {
        parent: 'index',
        url: '/home',
        views: {
            'detail@index': {
                component: 'home',
            },
        },
    });
    $stateProvider.state('userList', {
        url: '/userList',
        component: 'userList'
    });
    $stateProvider.state('userProfile', {
        url: '/userProfile',
        component: 'userProfile'
    });
    $stateProvider.state('syllabus', {
        url: '/syllabus',
        component: 'syllabus'
    });
});
