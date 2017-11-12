akoApp.component('login', {
    templateUrl: "/app/component/login/loginTemplate.html",
    bindings: {
        userId: '<',
        password: '<'
    },
    controller: function ($http, $state, authService, msgDialogService) {

        this.name = "AKO Login";

        this.login = function () {
            authService.login(this.userId, this.password).then(function (response) {
                this.password = null;
                msgDialogService.showInfo("Hello, " + response.data.firstName);
            }).catch(function (error) {
                // if authentication was not successful. Setting the error message.
                msgDialogService.showError("Authetication Failed !");
            });
        }
    }
});
