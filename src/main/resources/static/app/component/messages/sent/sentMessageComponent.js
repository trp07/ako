akoApp.component('sentMessage', {
    templateUrl: "/app/component/messages/sent/sentMessageTemplate.html",
    bindings: {},
    require: {
        parent: '^^layout'
    },
    controller: function ($q, $rootScope, messageService, msgDialogService, authService, $state) {
        var ctrl = this;
        this.currentUser = null;
        this.$onInit = function () {
            var self = this;
            this.parent.getCurrentUser().then(function (user) {
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

        this.refreshMessages = function () {
            messageService.getAllMessages(this.currentUser.id).then(function (messages) {
                ctrl.messages = messages.data;
                ctrl.sentMessages = [];
                ctrl.messages.forEach(function (message) {
                    if (message.fromUsers.user.id == ctrl.currentUser.id) {
                        ctrl.sentMessages.push(message);
                    }
                });
            })
        }
    }
});
