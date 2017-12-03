//akoApp.service('userService', function (BASE_URL, $http) {
//    this.updateUser = function (user) {
//        return $http.put(user);
//    }
//    this.addUser = function (user) {
//        return $http.put(user);
//    }
//    this.getAllUsers = function (user) {
//        return $http.put(user);
//    }
//});

akoApp.factory('userService', function (BASE_URL, $resource) {



    var userService = $resource(BASE_URL + '/users/:id', null, {
        'getUserById': {
            method: 'GET',
            isArray: true
        },
        'getUsers': {
            method: 'GET',
            isArray: true
        },
        'addNewUser': {
            method: 'POST'
        },
        'updateUser': {
            method: 'PUT'
        }
    });
    return userService;
});
