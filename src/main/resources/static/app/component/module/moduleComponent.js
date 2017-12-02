akoApp.component('module', {
    templateUrl: "/app/component/module/moduleTemplate.html",
    bindings: {
        userId: '<',
        password: '<',
        courseId: '<'
    },
    controller: function($scope, $http){

    }
});