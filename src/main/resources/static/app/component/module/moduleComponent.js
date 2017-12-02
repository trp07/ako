akoApp.component('module', {
    templateUrl: "/app/component/module/moduleTemplate.html",
    bindings: {
        userId: '<',
        password: '<',
        courseId: '<'
    },
    require: {
        parent: '^^layout'
    },
    controller: function($scope, $http, moduleService){
    	console.log('Getting all of the modules');
    	$scope.modules = moduleService.viewModules('3');
    }
});