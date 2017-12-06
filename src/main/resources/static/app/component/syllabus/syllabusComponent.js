akoApp.component('syllabus', ['ngMaterial', 'ngMessages'], {
    templateUrl: "/app/component/syllabus/syllabusTemplate.html",
    bindings: {},
    controller: function ($scope, $http) {

        this.name = "AKO Syllabus";

        $scope.userAdmin = false;
        $scope.editMode = false;
        $scope.id = '';
        $scope.syllabusLink = 'https://s3-us-west-2.amazonaws.com/enpm613-ako/Syllabus_Default.pdf';
        $scope.due_date = new Date();

        $scope.resetDate = {
            due_date: new Date(2017, 11, 06)
        };


        $scope.getUserType = function() {
            $scope.resetError();
            console.log("====syllabusComponent.js==== getUserType() called");
            $http.get('/users/whoami').success(function(response) {
                if (response.userTypeId == 2) {
                    $scope.userAdmin = true;
                }
            }).error(function () {
                $scope.setError('Unable to retrieve user type');
            });
            console.log('====syllabusComponent.js==== /whoami admin/teacher?: ' + $scope.userAdmin);
        };


        $scope.uploadSyllabus = function (syllabus_file) {
            $scope.resetError();
            console.log("====syllabusComponent.js==== uploadSyllabus() called");
            $http.post('/syllabus/upload/' + syllabus_file).success(function (response) {
                $scope.syllabusLink = response;
            }).error(function () {
                $scope.setError('Unable to upload file');
            });
        };


        $scope.getSyllabusLink = function () {
            $scope.resetError();
            console.log("====syllabusComponent.js==== getSyllabusLink() called");
            $http.get('/syllabus/download').success(function (response) {
                $scope.syllabusLink = response;
            }).error(function () {
                $scope.setError('Could not determine download path');
            });
        };


        $scope.getAllAssignments = function () {
            $scope.resetError();
            console.log("====syllabusComponent.js==== getAllAssignments() called");
            $http.get('/syllabus/all.json').success(function (response) {
                $scope.syllabus = response;
            }).error(function () {
                $scope.setError('Could not display all assignments');
            });
        };


        $scope.addAssignment = function (assignment) {
            $scope.resetError();
            console.log("====syllabusComponent.js==== addAssignment() called with: " +
                assignment + " " + $scope.resetDate.due_date.getTime());
            $http.post('/syllabus/add/' + assignment + '/' + scope.resetDate.due_date.getTime()).success(function (response) {
                $scope.getAllAssignments();
            }).error(function () {
                $scope.setError('Could not add assignment');
            });
            $scope.assignment = '';
        };


        $scope.deleteAssignment = function (assignment) {
            $scope.resetError();
            console.log("====syllabusComponent.js==== deleteAssignment() called with: " + assignment.assignment);
            $http.delete('/syllabus/delete/' + assignment.assignment).success(function (response) {
                $scope.getAllAssignments();
            }).error(function () {
                $scope.setError('Could not delete assignment');
            });
        };


        $scope.deleteAll = function () {
            $scope.resetError();
            console.log("====syllabusComponent.js==== deleteAll() called");
            $http.delete('/syllabus/deleteAll').success(function (response) {
                $scope.getAllAssignments();
            }).error(function () {
                $scope.setError('Could not delete all assignments');
            })
        };


        $scope.editAssignment = function (assignment) {
            console.log("====syllabusComponent.js==== editAssignment() called for: " + assignment.assignment);
            $scope.resetError();
            $scope.assignment = assignment.assignment;
            $scope.id = assignment.id;
            $scope.editMode = true;
        };


        $scope.updateAssignment = function (assignment) {
            $scope.resetError();
            console.log("====syllabusComponent.js==== updateAssignment() called for: " +
                $scope.assignment + " => " + assignment);
            $http.put('/syllabus/update/' + $scope.id + '/' + assignment).success(function (response) {
                $scope.getAllAssignments();
                $scope.id = '';
                $scope.assignment = '';
                $scope.editMode = false;
            }).error(function () {
                $scope.setError('Could not update assignment');
            })
        };


        $scope.resetAssignmentField = function () {
            $scope.resetError();
            console.log("====syllabusComponent.js==== resetAssignmentField() called");
            $scope.assignment = '';
            $scope.editMode = false;
        };


        $scope.resetError = function () {
            $scope.error = false;
            console.log("====syllabusComponent.js==== resetError() called");
            console.log("====syllabusComponent.js==== userAdmin: " + $scope.userAdmin);
            $scope.errorMessage = '';
        };


        $scope.setError = function (message) {
            console.log("====syllabusComponent.js==== setError() called");
            $scope.error = true;
            $scope.errorMessage = message;
        };

        $scope.getUserType();
        $scope.getAllAssignments();

    }
});
