akoApp.service('messageService', function ($http, $q, BASE_URL) {
    this.getAllMessages = function (id) {
        return $http.get(BASE_URL + '/messages/all/' + id);
    };

    this.postMessage = function (message) {
        return $http({
            method: 'POST',
            url: BASE_URL + '/messages/send',
            data: message
        });
    };

    this.getAutocompleteResult = function (searchText) {
        return $http({
            url: BASE_URL + '/users/find',
            method: "GET",
            params: {
                "search": searchText
            }
        });
    };
});
