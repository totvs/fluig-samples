function validateForm(form){
	
	if(form.getValue('rdValidationType') == ""){
		throw 'Please select a method.';
	}
	
}