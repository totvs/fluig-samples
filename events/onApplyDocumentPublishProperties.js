function onApplyDocumentPublishProperties(fields) {

	//Recuperando dados do autor, no formato "{matricula} - {Nome}
	var autorDocumento = fields.get("Document.Author");
	var publicadorDocumento = fields.get("Document.Publisher");
	
	//Removendo a matrícula do aprovador
	var autor = autorDocumento.split(" - ")[1];
	var publicador = publicadorDocumento.split(" - ")[1];

	//Sobrescrevendo a variável com o nome valor - apenas o nome do autor, sem a matrícula
	fields.put("Document.Author", autor);
	fields.put("Document.Publisher", publicador);

	/* Alterando aprovadores */
	fields.put("Document.Level1.Approver1", "Sem verificador");
	fields.put("Document.Level2.Approver1", "Sem aprovador");
	
}