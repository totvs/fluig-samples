function beforeStateEntry(sequenceId) {
	//Define qual atividade será necessário adicionar os anexos à solicitação
	if (sequenceId == 2) {

		//busca no formulário campo do zoom que armazena o código da pasta
		var folderToAttach = hAPI.getCardValue("folderID");

		//cria as constraints necessárias e consulta o dataset 
		var c1 = DatasetFactory.createConstraint("activeVersion", "true", "true", ConstraintType.MUST);
		var c2 = DatasetFactory.createConstraint("documentType", "2", "2", ConstraintType.MUST);
		var c3 = DatasetFactory.createConstraint("parentDocumentId", folderToAttach, folderToAttach, ConstraintType.MUST);
		var constraints   = new Array(c1, c2, c3);
		var sortingFields = new Array("documentPK.documentId");	    
		var dataset = DatasetFactory.getDataset("document", null, constraints, sortingFields);

	    if (dataset.rowsCount > 0){
		    for (var i = 0; i < dataset.rowsCount; i++) {
				try {
					hAPI.attachDocument(dataset.getValue(i, "documentPK.documentId"));
				} catch (e) {
					throw "Não foi possível anexar os documentos da pasta informada. Verifique as permissões e tente novamente."
				}
			}
	    } else {
	    	throw "A pasta informada não possui documentos publicados. Verifique a pasta informada e tente novamente."
	    }
	}
}