function beforeDocumentViewer(){ 

	//Informações do documento
	var doc = getValue("WKDocument");
	var docExpires = doc.getExpires();
	var docExpirationDate = doc.getExpirationDate();

	//Variáveis para manipular datas
	var sdfDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
	var now = new Date();
	
	//Formatando datas
	var strDate = sdfDate.format(now);
    var documentExpirationDate = sdfDate.format(docExpirationDate);
	
    //Verificando se a data de validade do documento é igual a data atual
    var documentExpiresToday = strDate.equals(documentExpirationDate)
    
	if(docExpires && documentExpiresToday){
		log.warn("The document " + doc.getDocumentId() + " it cannot be viewed by user " + getValue("WKUser") + " because the due date is equal to the current date");
		throw ("Esse documento está vencendo hoje, não pode mais ser visualizado.")
	}else{
		log.info("This documment is allowed to visualization.")
	}
}