akoApp.component('layout', {
    templateUrl: "/app/component/layout/layoutTemplate.html",
    bindings: {},
    controller: function ($q, $rootScope, $scope, $state, authService, $transitions) {
        this.currentUser = null;
        this.subTitle = "Dashboard";
        this.currentState = $state.$current.name;

        this.$onInit = function () {
            var self = this;
            authService.getUser().then(function (user) {
                self.currentUser = user;
            });
        };

        var self = this;
        $scope.$watch(function () {
            return $state.$current.name
        }, function (newVal, oldVal) {
            onStateChangeSuccess.bind(self).call();
        })

        this.getCurrentUser = function () {
            var deferred = $q.defer();
            if (this.currentUser) {
                deferred.resolve(this.currentUser);
            }
            var self = this;
            authService.getUser().then(function (user) {
                self.currentUser = user;
                deferred.resolve(user);
            }).catch(deferred.reject);

            return deferred.promise;
        }

        $transitions.onSuccess({}, onStateChangeSuccess.bind(this));

        function onStateChangeSuccess() {
            this.currentNavItem = $state.$current.name;
            this.currentState = $state.$current.name;
        }
        this.logout = function () {
            authService.logout();
        }
    }
});
