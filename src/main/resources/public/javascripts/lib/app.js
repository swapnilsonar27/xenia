var app = angular.module('app',
    ['ui.router',
    'oc.lazyLoad',
    'angularjs-dropdown-multiselect',
    'ui.grid',
    'ui.grid.resizeColumns',
    'ui.grid.pagination',
    'ui.grid.autoResize',
    'ngCookies',
    'ngFileUpload',
    'angularFileUpload',
    'ui.select'
    ]);

app.run(['$rootScope','$state', '$cookieStore', '$http', function($rootScope, $state, $cookieStore, $http) {
	   $("#spinner").show();
		if($cookieStore.get("access-token")){
		   $state.go("login");
	   }else {
		   $http.defaults.headers.common["X-AUTH-TOKEN"] = $cookieStore.get("access-token");
	   }
}]);