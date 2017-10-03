app.service('adminService',function(){
    
    var map;
    var editMap;
    var yourPosition=null;
    this.initPlacesSearch=function(){
        map=new google.maps.Map(document.getElementById("mapCanvas"),{
                center: {lat: 45.6580, lng: 25.6012},
                zoom: 10,
                styles: []
        });
        var autocompleat= new google.maps.places.Autocomplete(
          (document.getElementById('parkSearch')), {
            types: ['geocode']
          });
        return autocompleat;
    };
    
    this.initPlacesSearchEdit=function(){
        editMap=new google.maps.Map(document.getElementById("editMap"),{
            center: {lat: 45.6580, lng: 25.6012},
            zoom: 10,
            styles: []
        });
        var autocompleat= new google.maps.places.Autocomplete(
          (document.getElementById('editParkSearch')), {
            types: ['geocode']
          });
        return autocompleat;
    };
    this.getMap=function(){
        return map;
    };
    
    this.setMarker=function(myLatLng){
        if(yourPosition==null){
        yourPosition = new google.maps.Marker({
              position: myLatLng,
              map: map,
              title: 'Here is your parking!'
        });
        map.setCenter(myLatLng);
        }
        else{
            yourPosition.setPosition(myLatLng);
            map.setCenter(myLatLng);
        }
    };
});