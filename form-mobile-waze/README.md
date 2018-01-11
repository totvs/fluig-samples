# Exemplo para implementação de geolocalização com o Waze
---

Com o **fluig Mobile**, você pode abrir outros aplicativos instalados no dispositivo e um deles é o Waze (aplicativo de navegação GPS). Isso torna possível repassar as coordenadas obtidas através de um formulário com geolocalização para o Waze realizar a navegação.

O exemplo a seguir pode ser implementado em uma tag script no *header* do formulário ou externamente em um arquivo *javascript*. 

	function getLocationUpdate(){
	    if(navigator.geolocation){
	      // timeout at 60000 milliseconds (60 seconds)
	      var options = {timeout:60000};
	      geoLoc = navigator.geolocation;
        	    watchID = geoLoc.watchPosition(showLocation, errorHandler, options);
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

Consulte nossa documentação [Protocolo fluig://](http://tdn.totvs.com/x/ztVSDg) e descubra as formas de interação com o aplicativo fluig Mobile através do URL scheme "fluig://", bem como a utilização de outros schemes a partir do próprio aplicativo.