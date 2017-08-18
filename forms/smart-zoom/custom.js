function setSelectedZoomItem(selectedItem) {

	var NAME = "colleagueName";
	var EMAIL = "colleagueMail";
	var ID = "colleagueId";

	var FIELD = selectedItem.inputId;

	if(selectedItem.inputId == NAME){
		setZoomData("colleagueMail", selectedItem["Email"]);
		setZoomData("colleagueId", selectedItem["ID"]);

	}else if(selectedItem.inputId == EMAIL){
		setZoomData("colleagueName", selectedItem["Nome"]);
		setZoomData("colleagueId", selectedItem["ID"]);

	}else if(selectedItem.inputId == ID){
		setZoomData("colleagueName", selectedItem["Nome"]);
		setZoomData("colleagueMail", selectedItem["Email"]);
	}
}

function setZoomData(instance, value){
	window[instance].setValue(value);
}

function removedZoomItem(removedItem) {

	var NAME = "colleagueName";
	var EMAIL = "colleagueMail";
	var ID = "colleagueId";

	if (item.inputId == NAME) {
		console.log("Retornando resultado removedZoomItem");
		console.log(item);
	}
}
