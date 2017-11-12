akoApp.factory('authService', function ($http, $q, BASE_URL) {

    var login = function (userName, password) {
        var data = {
            'username': userName,
            'password': password
        };
        var deferred = $q.defer();
        $http.post(BASE_URL + "/auth/login", data).then(function (res) {
            // checking if the token is available in the response
            if (res.data.access_token) {
                // setting the Authorization Bearer token with JWT token
                $http.defaults.headers.common.Authorization = 'Bearer ' + res.data.access_token;

                getUser().then(deferred.resolve).catch(deferred.reject);
            } else {
                deferred.reject();
            }
        }).catch(deferred.reject);
        return deferred.promise;
    }

    var getUser = function () {
        return $http.get(BASE_URL + '/users/whoami');
    };
    
    var refreshToken = function () {
        console.error('refreshToken');
    };

    return {
        getUser: getUser,
        login: login,
        refreshToken: refreshToken
    };
});
