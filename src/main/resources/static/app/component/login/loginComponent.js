akoApp.component('login', {
    templateUrl: "/app/component/login/loginTemplate.html",
    bindings: {
        userId: '<',
        password: '<'
    },
    controller: function ($http, $state, authService, msgDialogService, $mdDialog) {

        this.user = null;
        var self = this;
        this.login = function () {
            authService.login(this.userId, this.password).then(function (user) {
                this.password = null;
                var url = "";
                self.user = user;
                if (user.hasMfaActive) {
                    showPrompt();
                } else {
                    $state.go('home', {
                        user: self.user
                    });
                }
            }).catch(function (error) {
                // if authentication was not successful. Setting the error message.
                msgDialogService.showError("Authetication Failed !");
            });
        }


        var showPrompt = function (ev) {
            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.prompt()
                .title('Enter the OTP')
                .textContent('Use Google Autheticator!')
                .placeholder('Code')
                .ok('Okay!')
                .cancel('Cancel!');

            $mdDialog.show(confirm).then(function (code) {
                authService.verifyCode(code).then(function (res) {
                    $state.go('home', {
                        user: self.user
                    });
                }).catch(function (err) {
                    console.log(err);
                });
            }, function () {

            });
        };

    }
});
