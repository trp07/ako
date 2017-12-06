akoApp.component('composeMessage', {
    templateUrl: "/app/component/messages/composeMessage/composeMessageTemplate.html",
    bindings: {},
    require: {
        parent: '^^layout'
    },
    controller: function ($q, $rootScope, messageService, msgDialogService, authService, $state) {
        this.newMessage = {
            messageUsers: [],
            previousMessageId: null,
            subject: null,
            body: null,
            courseId: 3
        };

        function messageUser(messageUserTypeId) {
            this.messageUserTypeId = messageUserTypeId;
            this.userId = null;
        }

        var ctrl = this;
        this.currentUser = null;
        this.$onInit = function () {
            var self = this;
            this.parent.getCurrentUser().then(function (user) {
                self.currentUser = user;
            });
        };

        this.getMatchingUsers = function (searchText) {
            var deferred = $q.defer();
            messageService.getAutocompleteResult(searchText).then(function (response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        }

        this.sendMessage = function () {
            if (this.selectedUser !== null) {
                var toUser = new messageUser("TO");

                toUser.userId = this.selectedUser.id;

                var fromUser = new messageUser("FROM");
                fromUser.userId = this.currentUser.id;

                this.newMessage.messageUsers = [toUser, fromUser];
                messageService.postMessage(this.newMessage).then(function (response) {
                    msgDialogService.showInfo("Message Sent")
                });
            } else {
                msgDialogService.showError("Please select the recipient")
            }
        }
    }
});
