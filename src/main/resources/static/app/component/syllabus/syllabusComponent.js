akoApp.component('syllabus', {
    templateUrl: "/app/component/syllabus/syllabusTemplate.html",
    bindings: {},
    controller: function($scope, $http){

        this.name = "AKO Syllabus";

        $scope.editMode = false;
        $scope.id = '';
        $scope.syllabusLink = 'https://s3-us-west-2.amazonaws.com/enpm613-ako/Syllabus_Default.pdf';

        $scope.uploadSyllabus = function(syllabus_file){
             $scope.resetError();
             console.log("====syllabusComponent.js==== uploadSyllabus() called");
             $http.post('/syllabus/upload/' + syllabus_file).success(function(response){
                $scope.syllabusLink = response;
             }).error(function() {
                $scope.setError('Unable to upload file');
             });
        };

        $scope.getSyllabusLink = function(){
            $scope.resetError();
            console.log("====syllabusComponent.js==== getSyllabusLink() called");
            $http.get('/syllabus/download').success(function(response){
                $scope.syllabusLink = response;
            }).error(function() {
                $scope.setError('Could not determine download path');
            });
        };

        $scope.getAllAssignments = function(){
            $scope.resetError();
            console.log("====syllabusComponent.js==== getAllAssignments() called");
            $http.get('/syllabus/all.json').success(function(response){
                $scope.syllabus = response;
            }).error(function() {
                $scope.setError('Could not display all assignments');
            });
        };

        $scope.addAssignment = function(assignment){
            $scope.resetError();
            console.log("====syllabusComponent.js==== addAssignment() called with: " + assignment);
            $http.post('/syllabus/add/' + assignment).success(function(response){
                $scope.getAllAssignments();
            }).error(function() {
                $scope.setError('Could not add assignment');
            });
            $scope.assignment = '';
        };

        $scope.deleteAssignment = function(id){
            $scope.resetError();
            console.log("====syllabusComponent.js==== deleteAssignment() called");
            $http.delete('/syllabus/delete/'+id).success(function(response){
                $scope.getAllAssignments();
            }).error(function() {
                $scope.setError('Could not delete assignment');
            });
        };

        $scope.deleteAll = function(){
            $scope.resetError();
            console.log("====syllabusComponent.js==== deleteAll() called");
            $http.delete('/syllabus/deleteAll').success(function(response){
                $scope.getAllAssignments();
            }).error(function() {
                $scope.setError('Could not delete all assignments');
            })
        };

        $scope.editAssignment = function(id, assignment){
            console.log("====syllabusComponent.js==== ediAssignment() called");
            $scope.resetError();
            $scope.assignment = assignment;
            $scope.id = id;
            $scope.editMode = true;
        };

        $scope.updateAssignment = function(id, assignment){
            $scope.resetError();
            console.log("====syllabusComponent.js==== updateAssignment() called");
            $http.put('/syllabus/update/'+ $scope.id + '/' + assignment).success(function(response){
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
            console.log("====syllabusComponent.js==== resetAssignmentField() called");
            $scope.assignment = '';
            $scope.editMode = false;
        };

        $scope.resetError = function() {
            $scope.error = false;
            console.log("====syllabusComponent.js==== resetError() called");
            $scope.errorMessage = '';
        };

        $scope.setError = function(message) {
            console.log("====syllabusComponent.js==== setError() called");
            $scope.error = true;
            $scope.errorMessage = message;
        };

        $scope.getAllAssignments();
        //$scope.getSyllabusLink();

    }
});
