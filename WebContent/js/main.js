/**
 * AngularJS Tutorial 1
 * @author Nick Kaye <nick.c.kaye@gmail.com>
 */

/**
 * Main AngularJS Web Application
 */
var app = angular.module('tutorialWebApp', [
  'ngRoute'
]);

/**
 * Configure the Routes
 */
app.config(['$routeProvider', function ($routeProvider) {
  $routeProvider
    // Home
    .when("/", {templateUrl: "partials/home.html", controller: "PageCtrl"})
    // Pages
    .when("/about", {templateUrl: "partials/about.html", controller: "PageCtrl"})
    .when("/customer", {templateUrl: "partials/customer.html", controller: "PageCtrl"})
    .when("/faq", {templateUrl: "partials/faq.html", controller: "PageCtrl"})
    .when("/pricing", {templateUrl: "partials/pricing.html", controller: "PageCtrl"})
    .when("/services", {templateUrl: "partials/services.html", controller: "PageCtrl"})
    .when("/contact", {templateUrl: "partials/contact.html", controller: "PageCtrl"})
    // Blog
    .when("/blog", {templateUrl: "partials/blog.html", controller: "BlogCtrl"})
    .when("/blog/post", {templateUrl: "partials/blog_item.html", controller: "BlogCtrl"})
    // else 404
    .otherwise("/404", {templateUrl: "partials/404.html", controller: "PageCtrl"});
}]);

/**
 * Controls the Blog
 */
app.controller('BlogCtrl', function (/* $scope, $location, $http */) {
  console.log("Blog Controller reporting for duty.");
});

/**
 * Controls all other Pages
 */
app.controller('PageCtrl', ['$scope', '$http', '$interval', function($scope, $http, $interval) {
  console.log("Page Controller reporting for duty.");
  	
	$scope.firstMachineDetails = [];
	$scope.secondMachineDetails = [];
	$scope.ResponseDetails = [];
	$scope.Details = [];
	$scope.firstMachineTemp = [];
	$scope.secondMachineTemp = [];
	
	
	
	$scope.GetAllDataInFirstMachine = function() {
		$http.get('/iot-project/product/list/1')
      .then(function (result) {
          $scope.firstMachineDetails = result.data;
      }, function (data, status, header, config) {
          $scope.ResponseDetails = data;
      });
	};
	
	$scope.GetAllDataInSecondMachine = function() {
		$http.get('/iot-project/product/list/2')
      .then(function (result) {
          $scope.secondMachineDetails = result.data;
      }, function (data, status, header, config) {
          $scope.ResponseDetails = data;
      });
	};

	$scope.refillProductsInFirstMachine = function() {
		
		$http.get('/iot-project/product/updateAll/1')
	    .then(function (result) {
	        $scope.firstMachineDetails = result.data;
	    }, function (data, status, header, config) {
	        $scope.ResponseDetails = "problem occurred";
	    });
	};
	
	$scope.refillProductsInSecondMachine = function() {
		console.log("refillProductsInSecondMachine");
		$http({
			method:'GET',
			url: '/iot-project/product/updateAll/2'
		}).then(function (result) {
	        $scope.secondMachineDetails = result.data;
	    }, function (data, status, header, config) {
	        $scope.ResponseDetails = data;  
	    });
	};
		
	  $scope.consumeProductInFirstMachine = function (productId) {
	  	 $http({
	  		 method: 'GET',
	  		 url: '/iot-project/product/update/' + productId + '/-1/1'
	  	 }).then(function (result) {
              $scope.firstMachineDetails = result.data;
          }, function (result) {
              $scope.ResponseDetails = result.status;
          });
	  };
	  
	  $scope.consumeProductInSecondMachine = function (productId) {
		  $http({
		  		 method: 'GET',
		  		 url: '/iot-project/product/update/' + productId + '/-1/2'
		  	 }).then(function (result) {
	              $scope.secondMachineDetails = result.data;
	          }, function (result) {
	              $scope.ResponseDetails = result.status;
	          });
	  };
	  
	  $scope.refreshTemperatureFirstMachine = function () {
		  $http({
		  		 method: 'GET',
		  		 url: '/iot-project/product/temperature/1'
		  	 }).then(function (result) {
	              $scope.firstMachineTemp = result.data;
	          }, function (result) {
	              $scope.ResponseDetails = result.status;
	          });
	  };
	  
	  $scope.refreshTemperatureSecondMachine = function () {
		  $http({
		  		 method: 'GET',
		  		 url: '/iot-project/product/temperature/2'
		  	 }).then(function (result) {
	              $scope.secondMachineTemp = result.data;
	          }, function (result) {
	              $scope.ResponseDetails = result.status;
	          });

	  };
	  
	  // initialization calls for first view
	  $scope.GetAllDataInFirstMachine();
	  $scope.GetAllDataInSecondMachine();
	  $scope.refreshTemperatureFirstMachine();
	  $scope.refreshTemperatureSecondMachine();
	  
	  // 10000 = 10 seconds to refresh temperature values of machines
	  $interval(function() {
		  $scope.refreshTemperatureFirstMachine();         
	    }, 10000);
	  
	  $interval(function() {
		  $scope.refreshTemperatureSecondMachine();      
	    }, 10000);
	  
	  // 10000 = 1 second to refresh item amounts in machines
	  $interval(function() {
		  $scope.GetAllDataInSecondMachine();      
	    }, 1000);
	  
	  $interval(function() {
		  $scope.GetAllDataInFirstMachine();      
	    }, 1000);
	  
}]);