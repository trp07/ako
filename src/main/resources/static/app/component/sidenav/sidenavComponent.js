akoApp.component('sidenavComponent', {
    templateUrl: "/app/component/sidenav/sidenavTemplate.html",
    bindings: {},
    controller: function ($state) {
        this.gotoState = function (state) {
            $state.go(state);
        }
    }
});
