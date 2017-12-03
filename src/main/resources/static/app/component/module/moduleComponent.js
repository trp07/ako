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
	controller: function($scope, $http, moduleService, $mdDialog){
		/**
		 * On init
		 */
		this.data = null
		courseId = '3'
		console.log('Getting all of the modules');
		moduleService.viewModules(courseId).then(function (data) {
			$scope.modules = data;
		});
		
		$scope.cancel = function() { // NOT WORKING 
		     $mdDialog.cancel();
		};
		
		$scope.displayAddModuleForm = function (ev) {
			$mdDialog.show({
			      templateUrl: '/app/component/module/moduleAddTemplate.html',
			      parent: angular.element(document.body),
			      targetEvent: ev,
			      clickOutsideToClose:true
			})
			.then(function(module) {
			  $scope.status = 'You said the information was "' + module.description + '".';
			}, function() {
			  $scope.status = 'You cancelled the dialog.';
			});
		}
	}
});