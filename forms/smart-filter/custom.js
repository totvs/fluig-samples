function setSelectedZoomItem(selectedItem) {
	
	var NAME = "colleagueName";
	var EMAIL = "colleagueMail";

	if(selectedItem.inputId == NAME){
		console.log("------ Atualizando zoom de e-mail --------------");
		reloadZoomFilterValues(EMAIL, "mail," + selectedItem["mail"]);
	}
	
	removeZoomData()
}

function removeZoomData(){
	filter_colleagueName.on('fluig.autocomplete.itemRemoved', function(ev) {
		filter_colleagueMail.removeAll();
	});
	
	filter_colleagueMail.on('fluig.autocomplete.itemRemoved', function(ev) {
		filter_colleagueName.removeAll();

	});
}
