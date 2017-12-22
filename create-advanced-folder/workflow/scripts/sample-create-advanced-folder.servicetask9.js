function servicetask9(attempt, message) {
	
	//Variáveis globais com os dados do formulário
	var FOLDER_NAME = hAPI.getCardValue("txtFolderName");
	var PARENT_FOLDER_CODE = hAPI.getCardValue("txtFolderCode");
	var GROUP_CODE = hAPI.getCardValue("txtGroupCode");
	var INHERIT_SECURITY = hAPI.getCardValue("cbxInheritSecurity") == "true";
	
	//Tipo de documento pasta
	var DOCUMENT_TYPE_FOLDER = "1";
	
	//Usuário logado
	var USER_LOGGED = getValue("WKUser");
	
	//Número da solicitação para comentário uso na descrição da pasta criada
	var REQUEST_NUMBER = getValue("WKNumProces");
	
	// Descrição da pasta criada
	var ADDITIONAL_COMMENTS = "This folder was created automatically from fluig BPM - request ";
	
	//Atribuições de segurança
	var USER_ATTRIBUTION_TYPE = 1;
	var GROUP_ATTRIBUTION_TYPE = 2;
	var ALL_USER_ATTRIBUTION_TYPE = 3; 
	
	//Níveis de segurança
	var READING = 0;
	var RECORDING = 1;
	var MODIFICATION = 2;
	var ALL = 3;
	
    try {
    	
    	//Instancia um novo documento e define as propriedades básicas
    	var dto = docAPI.newDocumentDto();
        dto.setDocumentDescription(FOLDER_NAME);
        dto.setAdditionalComments(ADDITIONAL_COMMENTS + REQUEST_NUMBER);
        dto.setDocumentType(DOCUMENT_TYPE_FOLDER);
        dto.setParentDocumentId(parseInt(PARENT_FOLDER_CODE));
        dto.setInheritSecurity(INHERIT_SECURITY);
        
        /*
         Como é possível definir várias permissões, é necessário criar um array com todas as permissões
         que serão aplicadas. Cada item do array é uma permissão ou restrição
        */
        var dtosSecurity = new Array();
        
        //Definindo permissão total para grupo
        var dtoGroupSecurity = docAPI.newDocumentSecurityConfigDto();
        dtoGroupSecurity.setAttributionType(GROUP_ATTRIBUTION_TYPE);
        dtoGroupSecurity.setAttributionValue(GROUP_CODE);
        dtoGroupSecurity.setPermission(true);
        dtoGroupSecurity.setShowContent(true);
        dtoGroupSecurity.setSecurityLevel(ALL);

        //Definindo permissão total para usuário
        var dtoUserSecurity = docAPI.newDocumentSecurityConfigDto();
        dtoUserSecurity.setAttributionType(USER_ATTRIBUTION_TYPE);
        dtoUserSecurity.setAttributionValue(USER_LOGGED);
        dtoUserSecurity.setPermission(true);
        dtoUserSecurity.setShowContent(true);
        dtoUserSecurity.setSecurityLevel(ALL);
        
      //Definindo permissão de modificação para todos os usuários
        var dtoAllUsersSecurity = docAPI.newDocumentSecurityConfigDto();
        dtoAllUsersSecurity.setAttributionType(ALL_USER_ATTRIBUTION_TYPE);
        dtoAllUsersSecurity.setAttributionValue(""); //Para todos os usuários deve-se passar vazio 
        dtoAllUsersSecurity.setPermission(true);
        dtoAllUsersSecurity.setShowContent(true);
        dtoAllUsersSecurity.setSecurityLevel(MODIFICATION);

      //Definindo restrição para usuário
        var dtoGroupRestrictionSecurity = docAPI.newDocumentSecurityConfigDto();
        dtoGroupRestrictionSecurity.setAttributionType(GROUP_ATTRIBUTION_TYPE);
        dtoGroupRestrictionSecurity.setAttributionValue(GROUP_CODE);
        dtoGroupRestrictionSecurity.setPermission(false);
        dtoGroupRestrictionSecurity.setShowContent(true);
        dtoGroupRestrictionSecurity.setSecurityLevel(MODIFICATION);
        
        //Definindo restrição para todos os usuários
        var dtoAllUsersRestrictionSecurity = docAPI.newDocumentSecurityConfigDto();
        dtoAllUsersRestrictionSecurity.setAttributionType(ALL_USER_ATTRIBUTION_TYPE);
        dtoAllUsersRestrictionSecurity.setAttributionValue("");
        dtoAllUsersRestrictionSecurity.setPermission(true);
        dtoAllUsersRestrictionSecurity.setShowContent(true);
        dtoAllUsersRestrictionSecurity.setSecurityLevel(RECORDING);
        
        //Adicionando permissões no array de segurança
        dtosSecurity.push(dtoGroupSecurity);        
        dtosSecurity.push(dtoUserSecurity);
        dtosSecurity.push(dtoAllUsersSecurity);

        //Adicionando restrições no array de segurança
        dtosSecurity.push(dtoGroupRestrictionSecurity);
        dtosSecurity.push(dtoAllUsersRestrictionSecurity);
       
        var FOLDER = docAPI.createFolder(dto, dtosSecurity, null);
        log.info("Folder successfully createad: ID :" + FOLDER.getDocumentId());
        
    } catch (e) {
        log.error("Could not create folder: \n" + e);
    }	
}