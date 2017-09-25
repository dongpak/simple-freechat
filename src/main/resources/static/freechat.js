/**
 * 
 */
'use strict';


angular.module('freechatApp', [
	'ngRoute'
]);

angular.module('freechatApp').run(function($window, usernameService) {
	window.onbeforeunload = function(event) {
		console.log('beforeunload event detected: cancelable=' + event.cancelable);
		if (event.cancelable) {			
			usernameService.archiveUsername();
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

angular.module('freechatApp').factory('usernameService', function($http) {
	this.username = null;
	
	
	var setUsername = function(item) {
		//console.log("Setting the username [" + name + "]")
	    this.username = item;	    
	}
	
	var getUsername = function() {
		return this.username;
	}
	
	var archiveUsername = function() {
		if (this.username) {
			console.log("chat destroyed: [" + this.username.id +"]");
			$http({
				method: 'DELETE',
        		url:	'api/username/' + this.username.id,
	        	headers: {
	        		   'Authorization': 'Bearer ' + this.username.token
	        	}
			}).then(function(response) {
				console.log("ArchiveUsername: Success status: " + response.status + "/" + response.statusText);
			},
			function(response) {
				console.log("ArchiveUsername: Failure status: " + response.status + "/" + response.statusText);
			});
		}
		else {
			console.log("username destroyed already!");
		}
	}
	
	return {
		setUsername: setUsername,
		getUsername: getUsername,
		archiveUsername: archiveUsername
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
        		// console.log("Success status: " + response.status + "/" + response.statusText);        		
        		usernameService.setUsername(response.data);
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
			usernameService.archiveUsername();      	
		}
		
		this.$onInit = function() {
			ctrl.username = usernameService.getUsername();
		    		    
		    if (!ctrl.username) {
		    	$location.path("/")
		    }
		    else {		    	
		    	$interval(function () {
	        		$http({
	        			method: 'GET',
	            		url:	'api/messaging?timestamp='+ctrl.lastTimestamp,
	    	        	headers: {
	    	        		   'Authorization': 'Bearer ' + ctrl.username.token
	    	        	}
	        		}).then(function(response) {
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
	        		
			console.log("send: Username=" + ctrl.username.id);
	        
	        $http({
	        	method: 'POST',
        		url:	'api/messaging',
        		headers: {
	        		   'Authorization': 'Bearer ' + ctrl.username.token
	        	},
        		data:	{
        			username: ctrl.username.id,
        			message: ctrl.newMessage
        		}
	        }).then(function(response) {
	        	// console.log("Success status: " + response.status + "/" + response.statusText);
	        },
	        function(response) {
	        	console.log("Failure status: " + response.status + "/" + response.statusText);
	        	$location.path("/")
	        });
	        	
	        ctrl.newMessage = '';
		}
	}
});



