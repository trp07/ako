akoApp.component('userProfile', {
    templateUrl: "/app/component/user/profile/userProfileTemplate.html",
    bindings: {},
    controller: function (BASE_URL, authService, msgDialogService, userService, $http, $q) {
        var self = this;
        this.qrCodeURL = null;
        this.$onInit = function () {
            getCurrentUser();
        }

        function getCurrentUser() {
            authService.getUser().then(function (user) {
                self.user = angular.copy(user);
            }).catch(function (err) {
                console.log(err);
            });
        }
        this.setMfaActive = function (flag) {
            if (flag && !this.qrCodeURL) {
                this.getQRCodeURL();
            }
        }
        this.getQRCodeURL = function () {
            authService.getQRCodeURL().then(function (res) {
                self.qrCodeURL = res.data.result;
            }).catch(function (err) {
                msgDialogService.showError("Authorization error when fetching QR Code");
            });
        }
        this.saveChanges = function () {
            updateUser(self.user, self.user.id);
        }
        this.reset = function () {
            getCurrentUser();
        }


        function updateUser(user, id) {
            var deferred = $q.defer();
            $http.put(BASE_URL + '/users/' + id, user)
                .then(
                    function (response) {
                        deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.error('Error while updating User');
                        deferred.reject(errResponse);
                    }
                );
            return deferred.promise;
        }
    }
});
