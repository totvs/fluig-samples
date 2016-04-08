function displayFields(form,customHTML){
	
	var currentActivity = parseInt(getValue("WKNumState"));
	
	if(currentActivity == 0 || currentActivity == 4){
		form.setEnabled("rdValidationType",true);
	}else{
		form.setEnabled("rdValidationType",false);
	}
	
	form.setShowDisabledFields(true);
	form.setHidePrintLink(true);
	
}