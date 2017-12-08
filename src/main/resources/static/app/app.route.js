akoApp.config(function ($stateProvider, $urlServiceProvider) {
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
    $stateProvider.state('module', {
        parent: 'index',
        url: '/module',
        params: {
            user: null
        },
        views: {
            'detail@index': {
                component: 'module',
            },
        },
    });
    $stateProvider.state('inbox', {
        parent: 'index',
        url: '/inbox',
        params: {
            user: null
        },
        views: {
            'detail@index': {
                component: 'inbox',
            },
        },
    });
    $stateProvider.state('sent', {
        parent: 'index',
        url: '/sentMessage',
        params: {
            user: null
        },
        views: {
            'detail@index': {
                component: 'sentMessage',
            },
        },
    });
    $stateProvider.state('compose', {
        parent: 'index',
        url: '/composeMessage',
        params: {
            user: null
        },
        views: {
            'detail@index': {
                component: 'composeMessage',
            },
        },
    });
});
