akoApp.component('layout', {
    templateUrl: "/app/component/layout/layoutTemplate.html",
    bindings: {},
    controller: function ($state, authService) {
        this.headers = [
            {
                name: "Profile",
                action: "user_profile"
                        },
            {
                name: "Logout",
                action: "logout"
                        }]

        this.setHeaders = function (headers) {
            this.headers.unshift(headers);
        }
        this.headerAction = function (action) {
            this.performHeaderAction(action);
        }
        this.performHeaderAction = function (action) {
            switch (action) {
                case "logout":
                    authService.logout();
                    break;
                case "user_profile":
                    $state.go("userProfile");
                    break;
            }
        }
    }
});
