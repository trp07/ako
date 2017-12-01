akoApp.service('syllabusService', function($scope, $http){

    $scope.editMode = false;
    $scope.id = '';
    $scope.syllabusLink = '';

    $scope.uploadSyllabus = function(syllabus_file){
         $scope.resetError();
         $http.post('syllabus/upload/' + syllabus_file).success(function(response){
            $scope.syllabusLink = response;
         }).error(function() {
            $scope.setError('Unable to upload file');
         });
    };

    $scope.getSyllabusLink = function(){
        $scope.resetError();
        $http.get('syllabus/download').success(function(response){
            $scope.syllabusLink = response;
        }).error(function() {
            $scope.setError('Could not display all assignments');
        });
    };

    $scope.getAllAssignments = function(){
        $scope.resetError();
        $http.get('syllabus/all.json').success(function(response){
            $scope.syllabus = response;
        }).error(function() {
            $scope.setError('Could not display all assignments');
        });
    };

    $scope.addAssignment = function(assignment){
        $scope.resetError();
        $http.post('syllabus/add/' + assignment).success(function(response){
            $scope.getAllAssignments();
        }).error(function() {
            $scope.setError('Could not add assignment');
        });
        $scope.assignment = '';
    };

    $scope.deleteAssignment = function(id){
        $scope.resetError();
        $http.delete('syllabus/delete/'+id).success(function(response){
            $scope.getAllAssignments();
        }).error(function() {
            $scope.setError('Could not delete assignment');
        });
    };

    $scope.deleteAll = function(){
        $scope.resetError();
        $http.delete('syllabus/deleteAll').success(function(response){
            $scope.getAllAssignments();
        }).error(function() {
            $scope.setError('Could not delete all assignments');
        })
    };

    $scope.editAssignment = function(id, assignment){
        $scope.resetError();
        $scope.assignment = assignment;
        $scope.id = id;
        $scope.editMode = true;
    };

    $scope.updateAssignment = function(id, assignment){
        $scope.resetError();
        $http.put('syllabus/update/'+ $scope.id + '/' + assignment).success(function(response){
            $scope.getAllAssignments();
            $scope.position = '';
            $scope.assignment = '';
            $scope.editMode = false;
        }).error(function(){
            $scope.setError('Could not update assignment');
         })
    };

    $scope.resetAssignmentField = function() {
        $scope.resetError();
        $scope.assignment = '';
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };

    $scope.getAllAssignments();
    $scope.getSyllabusLink();

});
