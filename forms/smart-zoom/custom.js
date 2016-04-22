function setSelectedZoomItem(selectedItem) {
	
	var NAME = "colleagueName";
	var EMAIL = "colleagueMail";
	var ID = "colleagueId";
	var itemSelected;	
	var FIELD = selectedItem.inputId;
	
	if(selectedItem.inputId == NAME){		
		itemSelected = {
			     'colleagueId': selectedItem["colleagueId"],
			     'colleagueName': selectedItem["colleagueName"],
			     'login': selectedItem["login"],
			     'mail': selectedItem["mail"]
			};
		
		setZoomData(filter_colleagueMail, itemSelected);
		setZoomData(filter_colleagueId, itemSelected);
		
	}else if(selectedItem.inputId == EMAIL){
		
		itemSelected = {
			     'colleagueId': selectedItem["colleagueId"],
			     'colleagueName': selectedItem["colleagueName"],
			     'login': selectedItem["login"],
			     'mail': selectedItem["mail"]
			};
		
		setZoomData(filter_colleagueName, itemSelected);
		setZoomData(filter_colleagueId, itemSelected);		
	
	}else if(selectedItem.inputId == ID){
		
		itemSelected = {
			     'colleagueId': selectedItem["colleagueId"],
			     'colleagueName': selectedItem["colleagueName"],
			     'login': selectedItem["login"],
			     'mail': selectedItem["mail"]
			};
		
		setZoomData(filter_colleagueName, itemSelected);
		setZoomData(filter_colleagueMail, itemSelected);				
	}
	removeZoomData();
}

function setZoomData(inputName, item){
	inputName.add(item);
}

function removeZoomData(){
	filter_colleagueName.on('fluig.autocomplete.itemRemoved', function(ev) {
		filter_colleagueId.removeAll();
		filter_colleagueMail.removeAll();
	});
	
	filter_colleagueId.on('fluig.autocomplete.itemRemoved', function(ev) {
		filter_colleagueName.removeAll();
		filter_colleagueMail.removeAll();
	});
	
	filter_colleagueMail.on('fluig.autocomplete.itemRemoved', function(ev) {
		filter_colleagueName.removeAll();
		filter_colleagueId.removeAll();		
	});
}