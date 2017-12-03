akoApp.component('messages', {
    templateUrl: "/app/component/messages/messageTemplate.html",
    bindings: {},
    controller: function ($q, $rootScope, messageService, msgDialogService) {
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

        this.$onInit = function () {
            this.refreshMessages();
        };

        this.selectedMessageIndex = null;
        this.selectedMessage = null;
        this.showMessage = false;

        this.onMessageSelect = function (index, item) {
            if (this.selectedMessageIndex === null) {
                this.selectedMessageIndex = index;
                this.selectedMessage = item;

                this.showMessage = true;
            } else if (this.selectedMessageIndex === index) {
                this.selectedMessageIndex = null;
                this.selectedMessage = null;
            } else {
                this.selectedMessageIndex = index;
                this.selectedMessage = item;
                this.showMessage = true;
            }
        }
        this.isOpen = false;

        this.demo = {
            isOpen: false,
            selectedDirection: 'left'
        };

        this.getMatchingUsers = function (searchText) {
            var deferred = $q.defer();
            messageService.getAutocompleteResult(searchText).then(function (response) {
                deferred.resolve(response.data);
            });
            return deferred.promise;
        }

        this.addToUser = function () {

        }

        this.refreshMessages = function () {
            messageService.getAllMessages().then(function (messages) {
                ctrl.messages = messages.data;
            })
        }

        this.sendMessage = function () {
            if (this.selectedUser !== null) {
                var toUser = new messageUser("TO");

                toUser.userId = this.selectedUser.id;

                var fromUser = new messageUser("FROM");
                fromUser.userId = 1000000004;

                this.newMessage.messageUsers = [toUser, fromUser];
                messageService.postMessage(this.newMessage).then(ctrl.refreshMessages);
                msgDialogService.showInfo("Message Sent")
            } else {
                msgDialogService.showError("Please select the recipient")
            }
        }
    }
});
