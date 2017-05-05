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