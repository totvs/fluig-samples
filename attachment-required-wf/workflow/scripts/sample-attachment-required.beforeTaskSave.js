function beforeTaskSave(colleagueId,nextSequenceId,userList){
		
	var currentActivity = parseInt(getValue("WKNumState"));
	var ACTITIVY_HAPI_METHOD = 5;
	var ACTITIVY_DATASET_METHOD = 9;
	var ATTACHMENT_MINIMUM = 0;
	var ATTACHMENT_ERROR_MESSAGE = "Please add attachments to this request";
	
	if (currentActivity == ACTITIVY_HAPI_METHOD){
		
		log.info("------------------- Samples fluig: Validating attachments with hAPI method -------------------");
		
		var docs = hAPI.listAttachments();
		
		if(docs.size()== ATTACHMENT_MINIMUM){
			throw ATTACHMENT_ERROR_MESSAGE;
		}
	}
	
	if (currentActivity == ACTITIVY_DATASET_METHOD){
		
		log.info("------------------- Samples fluig: Validating attachments with dataset method  -------------------");

		var processConstraint = DatasetFactory.createConstraint("processAttachmentPK.processInstanceId", getValue("WKNumProces"),getValue("WKNumProces"), ConstraintType.MUST);
        var companyConstraint = DatasetFactory.createConstraint("processAttachmentPK.companyId",getValue("WKCompany"), getValue("WKCompany"), ConstraintType.MUST);
        var attachFields = new Array("documentId", "processAttachmentPK.attachmentSequence");
        var attachConstList = new Array(processConstraint, companyConstraint);
		var attachDataset = DatasetFactory.getDataset("processAttachment", attachFields, attachConstList, new Array("processAttachmentPK.attachmentSequence"));
		var hashMap = new java.util.HashMap();
		for(var i = 0; i < attachDataset.rowsCount; i++) {
			if(hashMap.containsKey(attachDataset.getValue(i,"documentId"))) {
			} else {
				hashMap.put(attachDataset.getValue(i,"documentId"), attachDataset.getValue(i,"processAttachmentPK.attachmentSequence"));
			}
		}
		if(hashMap.size() < 2) {
			throw ATTACHMENT_ERROR_MESSAGE;
		}

	}
}