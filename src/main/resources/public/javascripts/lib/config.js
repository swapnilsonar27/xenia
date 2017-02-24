app.config(function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $httpProvider){

    $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
    });

$urlRouterProvider.otherwise("/");
    $stateProvider
	    .state('login', {
	        url: "/",
	        templateUrl: "html/login.html",
	        controller: "loginController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('event', {
	        url: "/event",
	        templateUrl: "html/event/eventGrid.html",
	        controller: "eventController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('createEvent', {
	        url: "/createEvent",
	        templateUrl: "html/event/addEvent.html",
	        controller: "eventController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('token', {
	        url: "/token/:token",
	        templateUrl: "html/membership/membershipRenewal.html",
	        controller: "membershipRenewalController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('inventory', {
	        url: "/inventory",
	        templateUrl: "html/inventory/inventoryGrid.html",
	        controller: "inventoryController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('createInventory', {
	        url: "/createInventory",
	        templateUrl: "html/inventory/addInventory.html",
	        controller: "inventoryController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('user', {
	        url: "/user",
	        templateUrl: "html/user/userGrid.html",
	        controller: "userController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }) .state('editEvent', {
	        url: "/editEvent/:rowid",
	        templateUrl: "html/event/editEvent.html",
	        controller: "eventController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    }).state('createUser', {
	        url: "/createUser",
	        templateUrl: "html/user/addUser.html",
	        controller: "userController",
	        resolve: {
	            loadPlugin: function ($ocLazyLoad) {
	                return $ocLazyLoad.load([
	
	                ]);
	            }
	        }
	
	    });
    
    
    $httpProvider.interceptors.push(httpInterceptor);
    
    function httpInterceptor($q, $rootScope, $log, $window,$location){
    return {
    request: function (config) {
    	$("#spinner").show();
        return config || $q.when(config)
    },
    response: function (response) {
    	$("#spinner").hide();
      return response || $q.when(response);
    },
    responseError: function (response) {
    	$("#spinner").hide();
        console.log(response);
        if (response.status == 403) {
            $location.path('/login');
        }

        if(response.data.message == "JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted."){
            $location.path('/login');
        }
        return $q.reject(response);
    }
    };
}
});