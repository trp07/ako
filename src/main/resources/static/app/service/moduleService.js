akoApp.factory('moduleService', function ($http, $q, BASE_URL, store, $state) {
	var courseModules = null;
	
	// Get all modules for the course
	var viewModules = function(courseId) {
		console.log('Getting all of the modules in the service.');
		var deferred = $q.defer();
        if (!courseModules) {
            $http.get(BASE_URL + '/module/' + courseId).then(function (moduleData) {
            	courseModules = moduleData.data;
                deferred.resolve(courseModules);
            }).catch(deferred.reject);
        } else {
            deferred.resolve(courseModules);
        }
        return deferred.promise;
    }
	
	// Create a module for this course
	var createModule = function(courseId, name, description) {
		var deferred = $q.defer();
		var data = {
				'courseId':courseId,
				'name':name,
				'description':description
			}
        $http.post(BASE_URL + '/module/', data).then(function (moduleData) {
        	deferred.resolve(moduleData);
        }).catch(deferred.reject);
        return deferred.promise;
	}
	
	// Update a module
	var editModule = function(moduleId, courseId, name, description) {
		var deferred = $q.defer();
		var data = {
				'moduleId':moduleId,
				'courseId':courseId,
				'name':name,
				'description':description
			}
        $http.put(BASE_URL + '/module/', data).then(function (moduleData) {
        	deferred.resolve(moduleData);
        }).catch(deferred.reject);
        return deferred.promise;
	}
	
	// Delete a module
	var deleteModule = function(moduleId) {
		var deferred = $q.defer();
        $http.delete(BASE_URL + '/module/'+ moduleId).then(function (moduleData) {
        	courseModules = moduleData.data;
            deferred.resolve(courseModules);
        }).catch(deferred.reject);
        return deferred.promise;
	}
	
	// Add a file to a module
	var addModuleFile = function(moduleId, name, description, fileS3Url) {
		var deferred = $q.defer();
		var data = {
				'moduleId':moduleId,
				'name':name,
				'description':description,
				'fileS3Url':fileS3Url
			}
        $http.put(BASE_URL + '/module/file/' + moduleId, data).then(function (moduleData) {
        	deferred.resolve(moduleData);
        }).catch(deferred.reject);
        return deferred.promise;
	}
	
	// Delete a file from a module
	var deleteModuleFile = function(fileId) {
		var deferred = $q.defer();
        $http.delete(BASE_URL + '/module/file/' + fileId).then(function (moduleData) {
        	deferred.resolve(moduleData);
        }).catch(deferred.reject);
        return deferred.promise;
	}
    return {
    	viewModules: viewModules,
    	createModule: createModule,
    	editModule: editModule,
    	deleteModule: deleteModule,
    	addModuleFile: addModuleFile,
    	deleteModuleFile: deleteModuleFile
    };
	
});