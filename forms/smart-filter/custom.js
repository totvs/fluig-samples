function setSelectedZoomItem(selectedItem) {

	var NAME = "colleagueName";
	var EMAIL = "colleagueMail";

	if(selectedItem.inputId == NAME){
		console.log("------ Limpando zoom de e-mail --------------");
		$("#colleagueMail").val('');
		console.log("------ Atualizando zoom de e-mail --------------");
		reloadZoomFilterValues(EMAIL, "mail," + selectedItem["Email"]);
	}
}

function removedZoomItem(removedItem) {

	var NAME = "colleagueName";
	var EMAIL = "colleagueMail";

	if (item.inputId == NAME) {
		console.log("Retornando resultado removedZoomItem");
		console.log(item);
	}
}
