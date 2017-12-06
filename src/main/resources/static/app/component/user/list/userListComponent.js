akoApp.component('userList', {
    templateUrl: "/app/component/user/list/userListTemplate.html",
    bindings: {},
    controller: function ($state, userService) {
        this.users = null;
        this.$onInit = function () {
            var self = this;


            userService.getUsers().$promise.then(function (users) {
                self.users = users;
            });
        };
        this.onUserSelect = function (item) {
            $state.go("userProfile")
        }
    }
});
