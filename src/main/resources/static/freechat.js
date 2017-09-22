/**
 * 
 */
'use strict';


angular.module('freechatApp', [
	'ngRoute'
]);

angular.module('freechatApp').run(function($window) {
	window.onbeforeunload = function(event) {
		console.log('beforeunload event detected: cancelable=' + event.cancelable);
		if (event.cancelable) {
			console.log('Canceling the page unload')
			event.preventDefault();
		}
	}
});

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

angular.module('freechatApp').factory('usernameService', function() {
	this.username = null;
	
	var setUsername = function(name) {
		//console.log("Setting the username [" + name + "]")
	    this.username = name;	    
	}
	
	var getUsername = function() {
		return this.username;
	}
	
	return {
		setUsername: setUsername,
		getUsername: getUsername
	}
});

angular.module('freechatApp').component('freechatMainArticle', {	
	templateUrl: "FreeChat.html",			
	controller: function($http, $location, usernameService) {
		this.errorMessage = '';		
		
		this.checkName = function() {
        	if ((!this.name) || (this.name.trim() == "")) {
        		this.errorMessage = "Name is empty.  Pelase enter a valid name";
        		return;
        	}
        	
        	// has a valid name
        	$http({
        		method: 'POST',
        		url:	'api/username',
        		data:	{
        			id: this.name
        		},
        		parent: this,        		
        	}).then(function(response) {
        		console.log("Success status: " + response.status + "/" + response.statusText);
        		
        		usernameService.setUsername(response.config.data.id);
        		$location.path('/NameEntered');
        	},
        	function(response) {
        		console.log("Failure status: " + response.status + "/" + response.statusText);
        		
        		response.config.parent.errorMessage = 'Name is already taken.  Please enter a different name';        		
        	});
        }  
	}
});

angular.module('freechatApp').component('freechatMainChat', {
	templateUrl: "MainChat.html",
	controller: function($http, $interval, $location, usernameService) {
		var ctrl	= this;
		
		ctrl.lastTimestamp	= new Date().getTime();
		
		this.$onDestroy = function() {
			console.log("chat destroyed: [" + ctrl.username +"]");
			$http.delete('api/username/'+ctrl.username).then(function(response) {
				console.log("Success status: " + response.status + "/" + response.statusText);
        	},
        	function(response) {
        		console.log("Failure status: " + response.status + "/" + response.statusText);
        	});        	
		}
		
		this.$onInit = function() {
			ctrl.username = usernameService.getUsername();			
		    console.log("init: User name is [" + ctrl.username + "]");
		    
		    if (!ctrl.username) {
		    	$location.path("/")
		    }
		    else {
		    	
		    	$interval(function () {
	        		$http.get('api/messaging?timestamp='+ctrl.lastTimestamp).then(function(response) {
	        			response.data.forEach(function(item) {
	        				var mstamp = item.timestamp;
	        				
	        			    if (ctrl.lastTimestamp < mstamp) {
	        			    	ctrl.lastTimestamp = mstamp
	        			    }
	        			    
	        			    if (ctrl.messageArea == null) {
	        					ctrl.messageArea = "";        		
	        		        }
	        			    
	        			    console.log("Item: " + item);
	        			    ctrl.messageArea += item.username;
	        			    ctrl.messageArea += " ==> ";
	        			    ctrl.messageArea += item.message;
	        			    ctrl.messageArea += "\n";
	        			    
	        			    var textarea = document.getElementById('message-area');
	        	        	textarea.scrollTop = textarea.scrollHeight;
	        			});
	        			ctrl.lastTimestamp += 1;
	                });
	            
	        	}, 1000);
		    }
		}	
		
		this.sendMessage = function() {
	        		
			console.log("send: Username=" + ctrl.username);
	        var msgBody = {
	        			"username": ctrl.username,
	        			"message": ctrl.newMessage
	        	}
	        $http.post('api/messaging', msgBody).then(function(response) {
	        	
	        },
	        function(response) {
	        	
	        });
	        	
	        ctrl.newMessage = '';
		}
	}
});



