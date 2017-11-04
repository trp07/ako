akoApp.component('login', {
    templateUrl: "/app/component/login/loginTemplate.html",
    bindings: {
        userId: '<',
        password: '<'
    },
    controller: function (loginService, msgDialogService) {

        this.name = "AKO Login";
        this.login = function () {
            var response = loginService.login(this.userId, this.password);
            if(response){
                msgDialogService.showInfo("Login service called");
            }
        }
    }
});
