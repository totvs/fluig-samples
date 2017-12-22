function validateForm(form){
	
	if(form.getValue('txtFolderName') == ""){
		throw 'Hey! All fields are required.';
	}
	
	if(form.getValue('txtFolderCode') == ""){
		throw 'Hey! All fields are required.';
	}
	if(form.getValue('txtGroup') == ""){
		throw 'Hey! All fields are required.';
	}
	if(form.getValue('txtApprover') == ""){
		throw 'Hey! All fields are required.';
	}
	
}