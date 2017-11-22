akoApp.component('login', {
    templateUrl: "/app/component/login/loginTemplate.html",
    bindings: {
        userId: '<',
        password: '<'
    },
    controller: function ($http, $state, authService, msgDialogService, $mdDialog) {

        this.name = "AKO Login";

        this.login = function () {
            authService.login(this.userId, this.password).then(function (response) {
                this.password = null;
                var url = "";

                msgDialogService.showInfo("Username password verified");

                if (response.data.hasMfaActive) {
                    showPrompt();
                } else {
                    testAuth();
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
                .textContent('Bowser is a common name.')
                .placeholder('Code')
                .ok('Okay!')
                .cancel('Cancel!');

            $mdDialog.show(confirm).then(function (code) {
                authService.verifyCode(code).then(function (res) {
                    msgDialogService.showInfo("code verified= " + res.data.mfaAuth);
                    testAuth();
                }).catch(function (err) {
                    console.log(err);
                });
            }, function () {

            });
        };

        var testAuth = function () {
            authService.getQRCodeURL().then(function (res) {
                msgDialogService.showInfo("Authorization successful");
            }).catch(function (err) {
                msgDialogService.showInfo("Authorization error");
            });
        }
    }
});
