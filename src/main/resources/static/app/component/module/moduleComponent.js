akoApp.component('module', {
    templateUrl: "/app/component/module/moduleTemplate.html",
    bindings: {
        user: '<'
    },
    require: {
        parent: '^^layout'
    },
    controller: function (BASE_URL, $scope, $http, moduleService, authService, $mdDialog, $state) {
        /**
         * On init
         */
        $scope.isAdmin = false
        courseId = '3'
        // Variables used when adding/editing/deleting a module
        $scope.moduleId = null
        $scope.name = null
        $scope.description = null
        $scope.isPublished = null
        var reload = function () {
            console.log('Getting all of the modules');
            $scope.modules = null;
            if ($scope.isAdmin) {
                moduleService.viewModules(courseId).then(function (data) {
                    $scope.modules = data;
                });
            } else {
                moduleService.viewPublishedModules(courseId).then(function (data) {
                    $scope.modules = data;
                });
            }

        }

        var getUserType = function () {
            $http.get(BASE_URL + '/users/whoami').success(function (userData) {
                console.log("User data is " + userData.firstName);
                if (userData.userTypeId == 2) {
                    $scope.isAdmin = true;
                    moduleService.viewModules(courseId).then(function (data) {
                        $scope.modules = data;
                    });
                } else {
                    $scope.isAdmin = false;
                    moduleService.viewPublishedModules(courseId).then(function (data) {
                        $scope.modules = data;
                    });
                }

            })
        }
        getUserType();

        // Add  Dialog controller
        var AddModuleDialogController = function () {
            return ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                $scope.moduleAdd = function () {
                    if (!$scope.isPublished) {
                        $scope.isPublished = false;
                        moduleService.createModule(courseId, $scope.name, $scope.description, false).then(function (data) {

                        });
                    } else {
                        $scope.isPublished = true;
                        moduleService.createModule(courseId, $scope.name, $scope.description, true).then(function (data) {

                        });
                    }
                    $mdDialog.hide();
                };
                $scope.closeDialog = function () {
                    $mdDialog.hide();
                }
			}]
        }

        $scope.displayAddModuleForm = function (ev) {
            if ($scope.isAdmin) {
                $mdDialog.show({
                        clickOutsideToClose: true,
                        templateUrl: '/app/component/module/moduleAddTemplate.html',
                        parent: angular.element(document.body),
                        targetEvent: ev,
                        controller: AddModuleDialogController()
                    })
                    .then(function () {
                        console.log("Reloading")
                        reload();
                    }, function () {
                        console.log('You cancelled the dialog.');
                    });
            }
        }

        // Add a file to the module
        $scope.addFile = function (moduleId) {
            if ($scope.isAdmin) {
                console.log('Hitting add file for module ' + moduleId + '.');
            }
        }

        // Delete module module
        $scope.deleteModule = function (moduleId) {
            if ($scope.isAdmin) {
                console.log('Deleting module with id ' + moduleId + '.');
                moduleService.deleteModule(moduleId).then(function (data) {
                    $scope.modules = data;
                });
            }
        }

        // Publishing module 
        $scope.publish = function (module) {
            if ($scope.isAdmin) {
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
        }

        // Un-publish module 
        $scope.unpublish = function (module) {
            if ($scope.isAdmin) {
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
        // Edit the module
        var EditModuleDialogController = function (module) {
            return ['$scope', '$mdDialog', function ($scope, $mdDialog) {
                $scope.moduleEdit = function () {
                    console.log("Hit the edit module button");
                    if ($scope.name) {
                        module.name = $scope.name;
                    }
                    if ($scope.description) {
                        module.description = $scope.description;
                    }
                    $mdDialog.hide();
                    moduleService.editModule(module).then(function (data) {
                        reload();
                    });
                };
                $scope.closeDialog = function () {
                    $mdDialog.hide();
                }
			}]
        }
        $scope.displayEditForm = function (module) {
            if ($scope.isAdmin) {
                $scope.name = null
                $scope.description = null
                $scope.isPublished = null
                $mdDialog.show({
                        clickOutsideToClose: true,
                        templateUrl: '/app/component/module/moduleEditTemplate.html',
                        parent: angular.element(document.body),
                        controller: EditModuleDialogController(module)
                    })
                    .then(function () {
                        reload();
                    }, function () {
                        console.log('You cancelled the dialog.');
                    });
            }
        }
    }
});
