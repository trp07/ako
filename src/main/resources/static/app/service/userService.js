akoApp.factory('userService', ['$resource', function ($resource) {
    return $resource('/users/:id', null,
        {
            'getAllUsers': { method: 'GET', isArray: true },
            'addUser': { method: 'POST' },
            'updateUser': { method: 'PUT' }
        });
}]);