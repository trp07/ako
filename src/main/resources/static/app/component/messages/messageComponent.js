akoApp.component('messages', {
    templateUrl: "/app/component/messages/messageTemplate.html",
    bindings: {},
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

        var st = $state.current.name;
        var ctrl = this;
        this.currentUser = null;
        this.$onInit = function () {
            //Do other stuff
            var self = this;
            var st = $state.current.name;
            console.log("aaaaaa");
            if (st == "inbox")
                this.selectedIndex = 0;
            else if (st == "sent")
                this.selectedIndex = 1;
            else if (st == "compose")
                this.selectedIndex = 2;

            authService.getUser().then(function (user) {
                self.currentUser = user;
                self.refreshMessages();
            });
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
            messageService.getAllMessages(this.currentUser.id).then(function (messages) {
                ctrl.messages = messages.data;
                ctrl.sentMessages = [];
                ctrl.receivedMessages = [];
                ctrl.messages.forEach(function (message) {
                    if (message.fromUsers.user.id == ctrl.currentUser.id) {
                        ctrl.sentMessages.push(message);
                    }
                });
                ctrl.messages.forEach(function (message) {
                    message.toUsers.forEach(function (messageUser) {
                        if (messageUser.user.id == ctrl.currentUser.id) {
                            ctrl.receivedMessages.push(message);
                        }
                    })
                });
            })
        }

        this.sendMessage = function () {
            if (this.selectedUser !== null) {
                var toUser = new messageUser("TO");

                toUser.userId = this.selectedUser.id;

                var fromUser = new messageUser("FROM");
                fromUser.userId = this.currentUser.id;

                this.newMessage.messageUsers = [toUser, fromUser];
                messageService.postMessage(this.newMessage).then(function (response) {
                    ctrl.refreshMessages();
                    msgDialogService.showInfo("Message Sent")
                });
            } else {
                msgDialogService.showError("Please select the recipient")
            }
        }
    }
});
