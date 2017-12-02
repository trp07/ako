akoApp.component('home', {
    templateUrl: "/app/component/home/homeTemplate.html",
    bindings: {
        userId: '<',
        password: '<'
    },
    require: {
        parent: '^^layout'
    },
    controller: function ($state, msgDialogService, authService) {
        /**
         * On init
         */
        var self = this;
        this.$onInit = function () {
            //Do other stuff
            this.somethingElse = true;
            this.parent.headerAction = function (action) {
                console.log("from home " + action);
                self.performHeaderAction(action);
            }
        };

        authService.getUser().then(function (user) {
            msgDialogService.showInfo("hi " + user.firstName);
        });

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
