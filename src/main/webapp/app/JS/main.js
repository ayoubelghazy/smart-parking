var app = angular.module('myApp', [ 'ngCookies', 'ngRoute', 'ngMaterial' ]);

app.controller("mainController", [ '$scope', '$http', '$cookies', 'cookie',
		function($scope, $http, $cookies, cookie) {

			$scope.x = cookie;
            
			$scope.deleteCookies = function() {
				$cookies.remove("globals");
				$scope.x.cookie = "";
			};
			$scope.init = function() {
				$scope.x.cookie = $cookies.getObject('globals');
			};
			$scope.init();
		} ]);

app.controller("mapController",['$scope','$http','changeTheme','$cookies','cookie','$interval',function($scope, $http, changeTheme,$cookies,cookie,$interval) {
			
	/*
	$(document).on({
	    'DOMNodeInserted': function() {
	        $('.pac-item, .pac-item span', this).addClass('needsclick');
	    }
	}, '.pac-container');
	*/
	

//	$scope.userReferencePosition.lat = 45.653297;
//	$scope.userReferencePosition.lng =25.601954;
//	$scope.$apply();
//	setSearchMarker($scope.userReferencePosition);
//	changeDistance();
	
    $scope.goToLogin=function(){
        document.location.href = "#/login";
        document.getElementsByClassName("modal-backdrop")[0].remove();
    }
    
    $scope.reservedCategory="";
    $scope.collapse=true;
    $scope.openModal=function(){
        document.getElementById("myModal").style.display="block";
    }
    $scope.percent=function(busy,total){
        return Math.round(busy*100/total);
    }
    
    $scope.collapseWindow=function(){
        document.getElementById("map-left-menu-content").style.width="0px";
        document.getElementById("map").style.paddingLeft="0px";
        document.getElementById("map-left-menu").style.width="0px"
        document.getElementById("map-colaps-left-menu").style.left="0";
    }
    
    $scope.mouseOver=function(){
        document.getElementById("map-colaps-left-menu").style.opacity="0.8";
    }
    $scope.mouseExit=function(){
         document.getElementById("map-colaps-left-menu").style.opacity="0.6";
    }
    
    $scope.expandWindow=function(){
        document.getElementById("map-left-menu-content").style.width="400px";
        document.getElementById("map").style.paddingLeft="400px";
        document.getElementById("map-left-menu").style.width="400px"
        	document.getElementById("map-colaps-left-menu").style.left="400";
    }

    $scope.checkFilter=function(maxPrice,minPrice,maxDistance,park){
        if(park.distance || park.distance==0){
            if(park.distance!=-1){
                if(parseInt(maxDistance)>park.distance){
                    if(parseInt(maxPrice)>park.categories[0].price){
                        if(parseInt(minPrice)<park.categories[0].price){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
        
    }
    
    
    $scope.selectCategoryToReserv=function(cat){
        $scope.reservedCategory=cat;
        for(var i=0;i<$scope.currentParking.categories.length;i++){
            if(cat.id_category==$scope.currentParking.categories[i].id_category){
                document.getElementById(cat.id_category.toString()).style.backgroundColor="#d6dadb";
            }
            else{
                document.getElementById($scope.currentParking.categories[i].id_category).style.backgroundColor="aliceblue";
            }
        }
    }
                            
							$scope.currentParking;
							$scope.x = cookie;
                            
							// initialize map when the app run
							$scope.parkingData = [];
							$scope.userReferencePosition = {};

							var infoWindow = new google.maps.InfoWindow({maxWidth: 340});
							var markerCluster; // marker cluster reference
							var markers = []; // markers for the parking
							var yourPosition = null; // search box position
							var map; // google map
							var service = new google.maps.DistanceMatrixService();

							
							
							$scope.rememberSelectedPark=function(park)
							{
								$scope.currentParking=park;
							}
							
							
							
                            //calculate and set the distance from the curent point to a specified parking place
                            //
                            //i - index of the parking ... required because of callback function
							var calculateDistance = function(i, destinations, origins) {
								service
										.getDistanceMatrix(
												{
													origins : [ origins ],
													destinations : [ destinations ],
													travelMode : 'DRIVING',
													unitSystem : google.maps.UnitSystem.METRIC,
													avoidHighways : false,
													avoidTolls : false
												},
												function(response, status) {
													if (response.rows[0].elements[0].status == "OK") {
														$scope.parkingData[i].distance = parseInt(parseFloat(response.rows[0].elements[0].distance.value/1000).toFixed(2));
                                                     
                                                        $scope.$apply();
													} else {
														$scope.parkingData[i].distance = -1;
                                                        $scope.$apply();
													}
												});
                                    service
										.getDistanceMatrix(
												{
													origins : [ origins ],
													destinations : [ destinations ],
													travelMode : 'WALKING',
													unitSystem : google.maps.UnitSystem.METRIC,
													avoidHighways : false,
													avoidTolls : false,
												},
                                                
												function(response, status) {
													if (response.rows[0].elements[0].status == "OK") {
                                                        var time=response.rows[0].elements[0].duration.value;
                                                        var timeTxt="";
                                                        if(Math.floor(time/3600)>=1)
                                                        {
                                                            timeTxt=timeTxt.concat(Math.floor(time/3600).toString()).concat(" ore ");
                                                            time=time%3600;
                                                        }
                                                        if(time/60>=1){
                                                            timeTxt=timeTxt.concat(Math.floor(time/60).toString()).concat(" minute ");
                                                       
                                                        }
                                                        $scope.parkingData[i].wlakingTime = timeTxt;
                                                        $scope.$apply();                                                             
													} else {
														$scope.parkingData[i].distance = -1;
                                                        $scope.$apply();
                                                    }
                                                    addEventListeners($scope.parkingData);
												});

							}
                            
                            //change parking distnace from the selected point
							var changeDistance = function() {
								var origins = new google.maps.LatLng(
										$scope.userReferencePosition.lat,
										$scope.userReferencePosition.lng);
								if ($scope.userReferencePosition != {}) {
									for (var i = 0; i < $scope.parkingData.length; i++) {
										var destinations = new google.maps.LatLng(
												$scope.parkingData[i].lat,
												$scope.parkingData[i].lng);

										calculateDistance(i, destinations, origins);
									};
								}
							};

                            $scope.triggerMarker=function(id){
                                map.setCenter(markers[id].getPosition());
                                map.setZoom(17);
                                markers.zoom
                                setTimeout(function(){
                                    google.maps.event.trigger(markers[id],'mouseover');
                                }, 400);
                                
                            }
                            $scope.triggerExitMarker=function(id){
                                  google.maps.event.trigger(markers[id],'mouseout');
                            }
                            
							// create markers and put them into clusters
							var createMarkerForParkings = function(locations,
									map) {
								// Clear out the old markers.
								markers.forEach(function(marker) {
									marker.setMap(null);
								});
								markers = [];
								for (var i = 0; i < locations.length; i++) {
									var info = locations[i];
                                    $scope.parkingData[i].minPrice=$scope.takeMinPriceForParking($scope.parkingData[i]);
									var marker = new google.maps.Marker({
										position : new google.maps.LatLng(
												info.lat, info.lng),
                                        icon:"app/Img/blue.png"
									});
                                    $scope.parkingData[i].markerIndex=i;
									markers.push(marker);
								}
                                changeDistance();
								markerCluster.clearMarkers();
								markerCluster.addMarkers(markers);
							};

							// getting the markers
                            
                           $http.get("parkings").then(function(response) {
								    $scope.parkingData = response.data;
								    console.log(response);
								    createMarkerForParkings($scope.parkingData,$scope.map);
							})
                            
                            $interval(function(){
                                    $http.get("http://localhost:8081/SmartParking/parkings").then(function(response) {
								    $scope.parkingData = response.data;
								    console.log(response);
								    createMarkerForParkings($scope.parkingData,$scope.map);
							})},30000);
                 
							
	
							
							$scope.checkReserve = function(x){
								$scope.checkReserved=false;
						    	$scope.reservedComplete=false;
								$scope.cancelReserve1=false;
								document.getElementById("output2").innerHTML = "";


									var req = {
											url : "http://localhost:8081/SmartParking/reserveCheck",
											method : "GET",
											params : {
												x:(x.cookie.data.id_normaluser),
											}
										};
									
									$http(req).then(function(response) {
										if(response.data==0)  //He already reserved a place
											{
											$scope.checkReserved=true;
											jQuery(function(){
												jQuery('#output2').qrcode($scope.somedata);
											})			
											}
									});

							}
					

					
							$scope.cancelReserve = function(x)
							{
								$scope.cancelReserve1=false;
								//$scope.cancelReserve1=true;

								$scope.load=true;


								var req = {
										url : "http://localhost:8081/SmartParking/cancelReserve",
										method : "GET",
										params : {
											x:(x.cookie.data.id_normaluser)
										}
									};

									$http(req).then(function(response) {
										$scope.load=false;
										$scope.cancelReserve1=false;

										if(response.data==1)
											{
											$scope.cancelReserve1=true;
											document.getElementById("output").innerHTML = "";

											//sa faca iar request la parcari
											}
									});	  
							}
					
					
					
					
							$scope.somedata;
							$scope.load=false;
							//for reservation
							$scope.reserve = function(selectedCategory,x) {
								$scope.cancelReserve1=false;
								$scope.load=true;

								    if(!$scope.x.cookie) //Nu logat, rezolvat cu ng-show-uri
										{
										//document.getElementById('check2').innerHTML = '<br>Trebuie sa fiti inregistrat pentru a face o rezervare ! ';
										}
								    else   //Logat
								    {		
								    	$scope.places=false;
								    	$scope.reservedComplete=false;
												var req = {
														url : "http://localhost:8081/SmartParking/reserve",
														method : "GET",
														params : {
															selectedCategory : (selectedCategory),
															x:(x.cookie.data.id_normaluser)
														}
													};

													$http(req).then(function(response) {
														$scope.load=false;
														if(response.data.uniqueCode == ""){
															$scope.places=true;

														}
														else{
														$scope.load=false;
														$scope.reservedComplete=true;
														$scope.somedata=response.data.uniqueCode;
														
														//document.getElementById("output").innerHTML = "";

																jQuery(function(){
																	jQuery('#output').qrcode($scope.somedata);
																})			
														
														}
													});	  
								}
							}
							
							

							// set only one marker and move it's position
							// only for use with google maps search box
							var setSearchMarker = function(myLatLng) {
								if (yourPosition == null) {
									yourPosition = new google.maps.Marker({
										position : myLatLng,
										map : map,
										title : 'You are here!',
                                        icon:"app/Img/me.png"
									});
									map.setCenter(myLatLng);
								} else {
									yourPosition.setPosition(myLatLng);
									map.setCenter(myLatLng);
								}
							};

    
    
                            $scope.initInterface=function(){
                                   var pageHeight=$(window).height();
                                    if(pageHeight<900){
                                    document.getElementById("map-page").style.height=pageHeight-52;
                                        
                                    
                                    document.getElementById("parking-list").style.height=pageHeight-300;
                                    document.getElementById("map-colaps-left-menu").style.height=pageHeight-52;
           
                                    }
                            }
							// create the map
							// set the autocompleat searchbox
							$scope.initMap = function() {

           
                                
								map = new google.maps.Map(document
										.getElementById('map'), {
									center : {
										lat : 45.6580,
										lng : 25.6012
									},
									zoom : 10,
									styles : [],
									mapTypeControl: true,
                                    mapTypeControlOptions: {
                                        style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
                                        position: google.maps.ControlPosition.TOP_CENTER
                                    },
                                    zoomControl: true,
                                    zoomControlOptions: {
                                        position: google.maps.ControlPosition.LEFT_CENTER
                                    },
                                    scaleControl: true,
                                    streetViewControl: true,
                                    streetViewControlOptions: {
                                        position: google.maps.ControlPosition.BOTTOM_RIGHT
                                    },
                                    fullscreenControl: true
								});
								

								
								$scope.takeMinPriceForParking=function(parking){
                                    var min=parking.categories[0].price;
                                    for(var i=1;i<parking.categories.length;i++){
                                        if(min>parking.categories[i].price)
                                            {
                                                min=parking.categories[i].price;
                                            }
                                    }
                                    return min;
                                }

								// Create the search box and link it to the UI
								// element.
								var input = document
										.getElementById('pac-input');

								var autocompleat = new google.maps.places.Autocomplete(
										(document.getElementById('pac-input')),
										{
											types : [ 'geocode' ]
										});

								map.controls[google.maps.ControlPosition.TOP]
										.push(input);
								

								// Listen for the event fired when the user
								// selects a prediction and retrieve
								// more details for that place.
								google.maps.event.addListener(autocompleat,'place_changed',function() {
													var place = autocompleat.getPlace();
													$scope.userReferencePosition.lat = place.geometry.location.lat();
													$scope.userReferencePosition.lng = place.geometry.location.lng();
													$scope.$apply();
													setSearchMarker($scope.userReferencePosition);
													changeDistance();
												});

								// Initialize markerclusterer
								markerCluster = new MarkerClusterer(
										map,
										markers,
										{
											imagePath : 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'
										});

								// Set directionDisplay to show road on map

								google.maps.event.addListenerOnce(map, 'idle',
										function() {
											google.maps.event.trigger(map,
													'resize');
										});
				
								infoWindow = new google.maps.InfoWindow;
								
														        // Try HTML5 geolocation.
														        if (navigator.geolocation) {
														          navigator.geolocation.getCurrentPosition(function(position) {
														            var pos = {
														              lat: position.coords.latitude,
														              lng: position.coords.longitude
														              
														            };
								
														            infoWindow.setPosition(pos);
														            infoWindow.setContent('Locatie curenta');
														            infoWindow.open(map);
														            map.setCenter(pos);
														            
														            $scope.userReferencePosition.lat = pos.lat;
																	$scope.userReferencePosition.lng = pos.lng ;
																	$scope.$apply();
																	setSearchMarker($scope.userReferencePosition);
																	changeDistance();
														            
														            
														          }, function() {
														            handleLocationError(true, infoWindow, map.getCenter());
														          });
														        } else {
														          // Browser doesn't support Geolocation
														          handleLocationError(false, infoWindow, map.getCenter());
														        }
														      
								
														      function handleLocationError(browserHasGeolocation, infoWindow, pos) {
														        infoWindow.setPosition(pos);
														        infoWindow.setContent(browserHasGeolocation ?
														                              'Error: The Geolocation service failed.' :
														                              'Error: Your browser doesn\'t support geolocation.');
								
														      }
														        infoWindow.open(map);			
};

							// call to initialize the map
							$scope.initMap();
                            
    
                            var createMapListenersForMarker=function(info,marker,id){
                                    google.maps.event.addListener(marker,
											'mouseover', function() {
var html = '<div class="info-window">'+
'<div class="info-window-head">'+
'<p class="park-name">'+ info.name +'</p>'+
'</div>'+
'<div class="info-window-content">'+
'<p class="desc">'+info.description+' </p>'+
'<p class="distance">'+info.distance.toString()+' km</p>'+
'<p class="price">'+info.minPrice+' lei</p>';
if(info.creditCard)
{
    html=html+'<p class="credit-card">Accepta card de credit</p>';
}
else
    {
        html=html+ '<p class="credit-card">Nu accepta card de credit</p>';
    }
html=html+
'</div>'+
'</div>';

												infoWindow.setContent(html);
												infoWindow.open(map, this);
											});

									google.maps.event.addListener(marker,
											'mouseout', function() {
												infoWindow.close(map, this);
											});
                            }
                                
							// set listeners for markers ... NOT WORKING
							// PROPERLY
							var addEventListeners = function(infos) {
	
								for (var i = 0; i < markers.length; i++) {
									var marker = markers[i];
									var info = infos[i];
	                                createMapListenersForMarker(info,marker,i);
								}
							}

							// change the theme of the map
							$scope.changeMapColor = function(theme) {
								changeTheme.changeMapColor(theme, map);
							};

							var i = 0;
							$('#filters-in')
									.click(
											function() {
												if (i == 0) {
													$('#filters').css('width',
															'33%');
													$('#filters-in').css(
															'left', '33%');
													$('#map').css('width',
															'65%');
													$('#rightArrow').hide();
													$('#leftArrow').show();
													$('#leftArrow').css(
															'visibility',
															'initial');

													i = 1;
												} else {
													$('#filters').css('width',
															'0%');
													$('#filters-in').css(
															'left', '0%');
													$('#map').css('width',
															'98%');
													$('#rightArrow').show();
													$('#leftArrow').hide();

													i = 0;
												}

											});

						} ]);

app.controller("registerController",['$scope','$http','$cookies','cookie',
						function($scope, $http, $cookies, cookie) {
							$scope.userData; // for register
							$scope.loginData; // for login
							$scope.x = cookie; // for cookies
                            
							$scope.register = function() {
								var req = {
									url : "http://localhost:8081/SmartParking/register",
									method : "GET",
									params : {
										userData : ($scope.userData)
									}
								};

								$http(req).then(function(response) {
									console.log(response);

									if (response.data == 3)
										console.log("deja exista")
									else if (response == 2)
										console.log("eroare")
									else
										console.log("S-a salvat cu succes!")
									window.location.href = "#/login";
								});

							};

							// *********************Login*************************
							$scope.login = function() {
								$scope.loginData;
								console.log($scope.loginData);
								var req = {
									url : "http://localhost:8081/SmartParking/login",
									method : "GET",
									params : {
										loginData : ($scope.loginData)
									}
								};
								$http(req)
										.then(
												function(response) {
													if (response.data == "")// user
																			// not
																			// found
													{
														document
																.getElementById('log').innerHTML = '<br>Nu am gasit acest user';
													} else // user found
													{
														document
																.getElementById('log').innerHTML = '<br>Bun venit!';
														$scope.x.cookie = response;
														window.location.href = "#/home";
														if ($scope.rememberMe == true) {
															$cookies.putObject(
																	'globals',
																	response);
														}
													}
												});
							};

					
							
							
						} ]);



app.directive("carousel", function() {
	return {
		restrict : 'E',
		templateUrl : "Directives/carousel.html"
	}
});

app.controller("adminController", ['$scope','$http','adminService','$cookies','cookie',function($scope, $http, adminService,$cookies,cookie) {
    
    $scope.x=cookie;
    $scope.newParking = {
        categories : []
    };
    $scope.adminParkings=[];
    $scope.rememberAdmin=false;
    $scope.category;

    var setChart=function(id,data){
        setTimeout(function(){
         Highcharts.chart('chart'.concat(id.toString()), {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
            backgroundColor:'rgba(41, 57, 101, 0.63)',
        },
        title: {
            text: ''
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                }
            }
        },
        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: [{
                name: 'Rezervate',
                y:data.reserved
            }, {
                name: 'Ocupate',
                y: data.ocupied,
                sliced: true,
                selected: true
            }, {
                name: 'Libere',
                y: data.free
            }]
        }]
    });
    },300);
    };
    
    
    $scope.init = function() {
        var autocompleate = adminService.initPlacesSearch();
        google.maps.event.addListener(autocompleate, 'place_changed',
                function() {
                    var place = autocompleate.getPlace();
                    $scope.newParking.lat = place.geometry.location
                            .lat();
                    $scope.newParking.lng = place.geometry.location
                            .lng();
                    $scope.$apply();
                    adminService.setMarker($scope.newParking);

                });
        
      
        
		if(!$scope.x.adminData){
			$scope.x.adminData=$cookies.getObject("adminData");
			if($scope.x.adminData){
                $scope.takeParkingsData($scope.x.adminData.id);
				$scope.uiAfterLogin();
				$scope.menuBtnCLick('homeBtn');
			}
			else{
				$scope.uiBeforLogin();
				$scope.menuBtnCLick('loginBtn');
			}
		}
		else{
			$scope.menuBtnCLick('homeBtn');
            $scope.takeParkingsData($scope.x.adminData.id);
		}

    }
	
    $scope.initMapInEdit=function(){
        
        var autocompleate = adminService.initPlacesSearchEdit();
        google.maps.event.addListener(autocompleate, 'place_changed',
                function() {
                    var place = autocompleate.getPlace();
                    $scope.editParking.lat = place.geometry.location
                            .lat();
                    $scope.editParking.lng = place.geometry.location
                            .lng();
                    $scope.$apply();
                    adminService.setMarker($scope.editParking);

        });
    }
    
    
    $scope.editParkingData=function(){
        var req={
            url:"http://localhost:8081/SmartParking/editParking",
            method:"GET",
            params:{
                parkingData:$scope.editParking
            }
        }
        $http(req).then(function(response){
            $scope.menuBtnCLick('homeBtn');
        })
        
    }
    
    $scope.setEditedParking=function(park){
        $scope.editParking=park;
    }
    
    $scope.addCategory=function(id,cat){
       var req={
            url:"http://localhost:8081/SmartParking/addCategory",
            method:"GET",
            params:{
                parkId:id,
                category:cat
            }
        }
       $http(req).then(function(response){
            console.log(response); 
           $scope.menuBtnCLick('homeBtn');
       });
       
    };
    
    $scope.addNewParking = function() {
        var req = {
            url : "http://localhost:8081/SmartParking/createParking",
            method : "GET",
            params : {
                newParking : ($scope.newParking)
            }
        };

        $http(req).then(function(response) {
            $scope.menuBtnCLick('homeBtn');
        });
    };


    $scope.takeParkingsData=function(id){
        var req={
            url : "http://localhost:8081/SmartParking/getParkingsForAdmin",
            method : "GET",
            params : {
                adminId : id
            }
        }
        
        $http(req).then(function(response){
           $scope.adminParkings=response.data; 
            
            for(var i=0;i<$scope.adminParkings.length;i++){
                
                var data={};
                data.ocupied=$scope.adminParkings[i].busyPlaces;
                data.reserved=$scope.adminParkings[i].totalReservations;
                data.free=$scope.adminParkings[i].totalPlaces-data.ocupied;
                
                    setChart($scope.adminParkings[i].id_parking,data);
            }
        });
    };

    

    $scope.adminLogout=function(){
		$cookies.remove("adminData");
		$scope.x.adminData="";
		$scope.uiBeforLogin();
		$scope.menuBtnCLick("loginBtn");
	}
    $scope.adminLogin=function(){
        var req={
            url:"http://localhost:8081/SmartParking/adminLogin",
            method:"GET",
            params:{
                email:$scope.adminEmail,
                password:$scope.adminPassword
            }
        };
        $scope.wrongData=false;
        $http(req).then(function(response){
           $scope.adminData=response.data; 
            if($scope.adminData!=""){

                if($scope.rememberAdmin){
                    $cookies.putObject("adminData",$scope.adminData);
                    $scope.x.adminData=$scope.adminData;
                    console.log("remembered")
                    $scope.uiAfterLogin();
                    $scope.menuBtnCLick("homeBtn");
                    $scope.takeParkingsData($scope.x.adminData.id);
                }
                else{
                    $scope.x.adminData=$scope.adminData;
                    console.log("login");
                    $scope.uiAfterLogin();
                    $scope.menuBtnCLick("homeBtn");
                    $scope.takeParkingsData($scope.x.adminData.id);
                }
            }
            else{
                //wrong user or password
                $scope.wrongData=true;

            }
        });
    };
    //how the ui will be changed after user is loged in 
	$scope.uiAfterLogin=function(){
		document.getElementById("homeBtn").style.display="inline-block";
		document.getElementById("parkingBtn").style.display="inline-block";
	}
	 //how ui will lock before user is loged in
	$scope.uiBeforLogin=function(){
		document.getElementById("homeBtn").style.display="none";
		document.getElementById("parkingBtn").style.display="none";
	}
    // menu workflow
    $scope.menuBtnCLick = function(menuItemId) {
        var menuitems = document.getElementsByClassName("menu-item");
        for (var i = 0; i < menuitems.length; i++) {
            if (menuitems[i].id == menuItemId) {
                menuitems[i].style.backgroundColor = "#101c27";
                menuitems[i].style.opacity = "1";
            } else {
                menuitems[i].style.backgroundColor = "#2a3f54";
                menuitems[i].style.opacity = "0.6";
            }
        }

        if(menuItemId=="homeBtn"){
            document.getElementById("admin-home").style.display="inline-block";
            document.getElementById("admin-parking").style.display="none";
            document.getElementById("admin-login").style.display="none";
            $scope.takeParkingsData($scope.x.adminData.id);
        }
        else if(menuItemId=="parkingBtn"){
            document.getElementById("admin-home").style.display="none";
            document.getElementById("admin-parking").style.display="inline-block";
            document.getElementById("admin-login").style.display="none";
        }
        else if(menuItemId=="loginBtn"){
            document.getElementById("admin-login").style.display="inline-block";
            document.getElementById("admin-home").style.display="none";
            document.getElementById("admin-parking").style.display="none";
        }
    };

    $scope.clickExpandButton=function(id){
        if($("#".concat(id).concat("-btn")).attr("class")=="fa fa-arrow-down"){  
            var totalHeight=0;
            $("#".concat(id).concat("-category")).children().each(function(){
                totalHeight = totalHeight + $(this).outerHeight(true);
            });
            $("#".concat(id).concat("-category")).css("max-height",totalHeight);
            $("#".concat(id).concat("-btn")).attr("class","fa fa-arrow-up")
        }
        else{
            $("#".concat(id).concat("-category")).css("max-height",0);
            $("#".concat(id).concat("-btn")).attr("class","fa fa-arrow-down")
        }
    };
    
    $scope.selectedCategory=null;
    $scope.setSelectedCategory=function(category){
        $scope.selectedCategory=category;
    }
    
	
	$scope.expandAddNewCategoryForParking=function(id){
		$("#".concat(id).concat("-add-btn")).css("display","none");
		$("#".concat(id).concat("-add-new-category")).css("max-height",503);
		$("#".concat(id).concat("-subtract-btn")).css("display","inline-block");
	}
	
	$scope.subtractAddCategoryForParking=function(id){
		$("#".concat(id).concat("-subtract-btn")).css("display","none");
		$("#".concat(id).concat("-add-new-category")).css("max-height",0);
		$("#".concat(id).concat("-add-btn")).css("display","inline-block");
	}
	
    $scope.updateCategory=function(){
        var req={
            url:"http://localhost:8081/SmartParking/updateCategory",
            method:"GET",
            params:{
                category:($scope.selectedCategory)
            }
        };
        
        $http(req).then(function(response){
            $scope.ok=response.data; 
            $scope.menuBtnCLick('homeBtn');
        });
        
    }
    
    
    $scope.deleteParking=function(id){
        var req={
            url:"http://localhost:8081/SmartParking/deleteParking",
            method:"GET",
            params:{
                idParking:id
            }
        }
        
        $http(req).then(function(response){
            $scope.menuBtnCLick('homeBtn');
        });
    }
    
    $scope.deleteCategory=function(id){
        var req={
            url:"http://localhost:8081/SmartParking/deleteCategory",
            method:"GET",
            params:{
                categoryId:(id)
            }
        }
        $http(req).then(function(response){
           $scope.ok=response.data; 
            $scope.menuBtnCLick('homeBtn');
        
        });
        
    }
    

    
	
    
	} ]);

app.directive("createParking", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/create-parking-form.html"
	}
});
app.directive("adminHome", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/admin-home.html"
	}
});
app.directive("adminParking", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/admin-parking.html"
	}
});
app.directive("createCategory", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/create-category.html"
	}
});

app.directive("editCategory", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/edit-category.html"
	}
});
app.directive("editParking", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/edit-parking.html"
	}
});

app.directive("adminLogin", function() {
	return {
		restrict : 'E',
		templateUrl : "app/Directives/admin-login.html"
	}
});

app.controller("loginController", [ '$scope', '$http', '$cookies',
		function($scope, $http, $cookies) {

		} ]);

app.config(function($routeProvider, $locationProvider) {

	$routeProvider.when("/", {
		templateUrl : "app/index.html"
	}).when("/home", {
		templateUrl : "app/index.html"
	}).when("/login", {
		templateUrl : "app/Login.html",
		controller : "registerController"
	}).when("/register", {
		templateUrl : "app/Register.html",
		controller : "registerController"
	}).when("/map", {
		templateUrl : "app/harta2.html",
		controller : "mapController"
	}).when("/admin", {
		templateUrl : "app/admin.html",
		controller : "adminController"
	}).otherwise({redirectTo:'/'})
});
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip();   
});