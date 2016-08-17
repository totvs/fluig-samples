\Exemplo para implementação de geolocalização com o Waze
=================================================================

	function getLocationUpdate(){
	    if(navigator.geolocation){
	      // timeout at 60000 milliseconds (60 seconds)
	      var options = {timeout:60000};
	      geoLoc = navigator.geolocation;
	      watchID = geoLoc.watchPosition(showLocation, 
	                                     errorHandler,
	                                     options);
	    }else{
	        alert("Sorry, esse dispositivo não suporta geolocalização!");
	    }
	}
	 
	function showLocation(position) {
	  var lat = position.coords.latitude;
	  var longi = position.coords.longitude;
	  document.getElementById("latitude").value = lat;
	  document.getElementById("longitude").value = longi;
	  var waze = document.getElementById('waze');
	    **Nesse caso abaixo, foi adicionado ao href da tag <a> o link com o scheme do waze mais a latitude e longitude adquiridas**
	  waze.href = "waze://?ll="+ lat + "," + longi;   
	}
	 
	function errorHandler(err) {
	  if(err.code == 1) {
	    alert("Erro: Acesso negado!");
	  }else if( err.code == 2) {
	    alert("Error: Posição está indisponível!");
	  }
	}


