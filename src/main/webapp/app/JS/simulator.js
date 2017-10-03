var app = angular.module('myApp', [ ]);

app.controller("simulatorController", [ '$scope', '$http', function($scope, $http) {
	
	
	$scope.getPark=function(){
	$http.get("http://localhost:8081/SmartParking/parkings").then(function(response) {
		$scope.parkingData = response.data;
		console.log($scope.parkingData);
	});
}
	$scope.getPark();
	
	
	$scope.carEnter = function(category){

				var req = {
						url : "http://localhost:8081/SmartParking/simulator/parkEnter",
						method : "GET",
						params : {
							category:(category),
						}
					};
				
				$http(req).then(function(response) {
					if(response.data==true)
					    document.getElementById("cons").innerHTML = document.getElementById("cons").innerHTML+ "A intrat o masina</br>";
					$scope.getPark();
				});
		
	}
	
	$scope.carExit = function(category){

		var req = {
				url : "http://localhost:8081/SmartParking/simulator/parkExit",
				method : "GET",
				params : {
					category:(category),
				}
			};
		
		$http(req).then(function(response) {
			if(response.data==true)
			    document.getElementById("cons").innerHTML =   document.getElementById("cons").innerHTML +"A iesit o masina </br>";
				$scope.getPark();
		});

}
	
	$scope.getReservations=function(){
		$http.get("http://localhost:8081/SmartParking/simulator/getReservations").then(function(response) {
			$scope.reservations = response.data;
			console.log($scope.reservations);
		});
	}
	$scope.getReservations();
	
	
	
	$scope.userArrived=function(code){
		var req = {
				url : "http://localhost:8081/SmartParking/simulator/userArrived",
				method : "GET",
				params : {
					code:(code)
				}
			};
		
		$http(req).then(function(response) {
			if(response.data==true)
				$scope.getPark();
				$scope.getReservations();

			
		});
		
	}
	
	
	window.setInterval(function(){
		
		$http.get("http://localhost:8081/SmartParking/simulator/checkReservations").then(function(response) {
			//document.getElementById("output").innerHTML = "";
			

		});
		
		}, 10000);
	
	
}]);