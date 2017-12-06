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
        };
    }
});
