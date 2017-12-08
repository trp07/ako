akoApp.component('userProfile', {
    templateUrl: "/app/component/user/profile/userProfileTemplate.html",
    bindings: {},
    require: {
        parent: '^^layout'
    },
    controller: function (BASE_URL, authService, msgDialogService, userService, $http, $q) {
        var self = this;
        this.qrCodeURL = null;
        this.user = null;
        this.$onInit = function () {
            var self = this;
            this.parent.getCurrentUser().then(function (user) {
                self.user = angular.copy(user);
                self.setMfaActive(self.user.hasMfaActive);
            });
        };

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
            updateUser(self.user, self.user.id).then(function (data) {
                msgDialogService.showInfo("User details updated!");

            }).catch(function (err) {
                msgDialogService.showError("Error occured while updating user details.");
            });;
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
