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
        params: {
            user: null
        },
        views: {
            'detail@index': {
                component: 'home',
            },
        },
    });
    $stateProvider.state('userList', {
        parent: 'index',
        url: '/userList',
        views: {
            'detail@index': {
                component: 'userList',
            },
        },
    });
    $stateProvider.state('userProfile', {
        parent: 'index',
        url: '/userProfile',
        views: {
            'detail@index': {
                component: 'userProfile',
            },
        },
    });
    $stateProvider.state('syllabus', {
        parent: 'index',
        url: '/syllabus',
        params: {
            user: null
        },
        views: {
            'detail@index': {
                component: 'syllabus',
            },
        },
    });
});
