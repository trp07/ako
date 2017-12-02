akoApp.factory('authService', function ($http, $q, BASE_URL, store, $state) {
    var loggedInUser = null;
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
                setJWTToken(res.data.access_token);

                getUser().then(function (userData) {
                    loggedInUser = userData.data;
                    deferred.resolve(userData);
                }).catch(deferred.reject);
            } else {
                deferred.reject();
            }
        }).catch(deferred.reject);
        return deferred.promise;
    }

    var logout = function () {
        store.remove('access_token');
        $state.go('login');
    }

    var getUser = function () {
        var deferred = $q.defer();
        if (!loggedInUser) {
            $http.get(BASE_URL + '/users/whoami').then(function (userData) {
                loggedInUser = userData.data;
                deferred.resolve(loggedInUser);
            }).catch(deferred.reject);
        } else {
            deferred.resolve(loggedInUser);
        }
        return deferred.promise;
    };

    var getQRCodeURL = function () {
        return $http.get(BASE_URL + '/auth/get-qr-code');
    };

    var verifyCode = function (codec) {
        var deferred = $q.defer();
        var data = {
            code: codec,
        };
        $http.post(BASE_URL + "/auth/verify-code", data).then(function (res) {
            // checking if the token is available in the response
            if (res.data.access_token) {
                // setting the Authorization Bearer token with JWT token
                setJWTToken(res.data.access_token);
            } else {
                deferred.reject();
            }
            deferred.resolve(res);
        }).catch(deferred.reject);
        return deferred.promise;
    };

    var refreshToken = function () {
        //TODO: make a refresh token call
    };
    var setJWTToken = function (token) {
        store.set('access_token', token);

    };
    return {
        getUser: getUser,
        login: login,
        refreshToken: refreshToken,
        getQRCodeURL: getQRCodeURL,
        verifyCode: verifyCode,
        logout: logout
    };
});
