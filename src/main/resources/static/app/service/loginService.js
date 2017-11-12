akoApp.service('loginService', function() {

  this.login = function(userId, password) {
     return true;
  };
    
  this.verifyAuthenticationCode = function(authCode) {
     return true;
  };
});
