akoApp.service('msgDialogService', function ($mdDialog) {
    var DialogController = function (message, type) {
        return ['$scope', '$mdDialog', function ($scope, $mdDialog) {
            $scope.message = message;
            $scope.type = type;
            $scope.closeDialog = function () {
                $mdDialog.hide();
            }
            }]
    }
    var showDialog = function (message, type) {
        $mdDialog.show({
            clickOutsideToClose: true,
            templateUrl: '/app/template/messageDialog.html',
            parent: angular.element(document.body),
            controller: DialogController(message, type)
        });
    }
    this.showError = function (message) {
        showDialog(message, "Error");
    }
    this.showWarning = function (message) {
        showDialog(message, "Warning");
    }
    this.showInfo = function (message) {
        showDialog(message, "Information")
    }
});
