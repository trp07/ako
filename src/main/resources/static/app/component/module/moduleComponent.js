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
			// Variables used when adding/editing/deleting a module

			$scope.moduleId = null
			$scope.name = null
			$scope.description = null
			$scope.isPublished = null
			console.log('Getting all of the modules');
		moduleService.viewModules(courseId).then(function (data) {
			$scope.modules = data;
		});

		// Add  Dialog controller
		var AddModuleDialogController = function ($scope, $mdDialog) {
			return ['$scope', '$mdDialog', function ($scope, $mdDialog) {
				$scope.closeDialog = function () {
					if ($scope.isPublished = undefined) {
						$scope.isPublished = false;
					}
					$mdDialog.hide();
				};
				$scope.addModule = function () {
					console.log("Accessed add module");
				};
			}]
		}

		$scope.addModule =  function(ev){
			console.log("Hit the add module button");
			if (scope.isPublished = undefined) {
				scope.isPublished = false;
			}
			console.log('You said the isPublished was ' + $scope.isPublished + '.');
//			moduleService.createModule($scope.name , $scope.description , $scope.isPublshed).then(function (data) {
//			moduleService.viewModules(courseId).then(function (data) {
//			$scope.modules = data;
//			});
//			});
			$mdDialog.hide();
		}

		$scope.displayAddModuleForm = function (ev) {
			$mdDialog.show({
				clickOutsideToClose: true,
				templateUrl: '/app/component/module/moduleAddTemplate.html',
				parent: angular.element(document.body),
				targetEvent: ev,
				controller: AddModuleDialogController()
			})
			.then(function() {

			}, function() {
				console.log('You cancelled the dialog.');
			});
		}

		// Add a file to the module
		$scope.addFile = function(moduleId) {
			console.log('Hitting add file for module ' + moduleId + '.');
		}

		// Delete module module
		$scope.deleteModule = function (moduleId){
			console.log('Deleting module with id ' + moduleId  + '.');
			moduleService.deleteModule(moduleId).then(function (data) {
				$scope.modules = data;
			});
		}

		// Publishing module 
		$scope.publish = function (module){
			console.log('Publishing module with id ' + module.id + ' with courseId ' + module.courseId + '.');
			if (!module.isPublished) {
				console.log('Calling the module service');
				module.isPublished = true;
				moduleService.editModule(module).then(function (data) {
					moduleService.viewModules(courseId).then(function (data) {
						$scope.modules = data;
					});
				});
			}
		}

		// Un-publish module 
		$scope.unpublish = function (module){
			console.log('Publishing module with id ' + module.id + '.');
			if (module.isPublished) {
				console.log('Calling the module service');
				module.isPublished = false;
				moduleService.editModule(module).then(function (data) {
					moduleService.viewModules(courseId).then(function (data) {
						$scope.modules = data;
					});
				});
			}
		}
	}
});