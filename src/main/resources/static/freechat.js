/**
 * 
 */
'use strict';

angular.module('freechatApp', [
	'ngRoute'
]);

angular.module('freechatApp').config(['$locationProvider', '$routeProvider', 
	function config($locationProvider, $routeProvider) {		
      
		$routeProvider.when('/', {
			template: '<freechat-main-article/>'
        })
        .when('/NameEntered', {
        	template: '<freechat-main-chat/>'
        })
        .otherwise('/');
    }
]);


angular.module('freechatApp').component('freechatMainArticle', {
	templateUrl: "/FreeChat.html",			
	controller: ['$location', function($location) {
		this.errorMessage = '';		
		
		this.checkName = function() {
        	if (!this.name) {
        		this.errorMessage = 'Name is already taken.  Please enter a different name';
        	}
        	else {
        		$location.path('/NameEntered');
        	}
        }
        
	}]
});

angular.module('freechatApp').component('freechatMainChat', {
	templateUrl: "/MainChat.html",
	controller: function() {
		this.selection = 1;		// main page		
	}
});



