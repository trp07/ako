akoApp.component('sidenavComponent', {
    templateUrl: "/app/component/sidenav/sidenavTemplate.html",
    bindings: {},
    controller: function (BASE_URL, $state) {
        this.gotoState = function (state) {
            $state.go(state);
        }
        this.getLogo = function () {
            return BASE_URL + "/assets/images/logo.png";
        }
    }
});
