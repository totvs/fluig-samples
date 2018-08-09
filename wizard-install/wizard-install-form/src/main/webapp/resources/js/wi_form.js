var wiform = SuperWidget.extend({

	instanceId: null,
	fileBase64: null,
	folderId: null,
	formId: null,
	installed: false,
	loading: false,
	folderName: 'MyAppFormFolder',
	
	init: function() {
		var that = this;		
		// verifica se o form já está instalado
		this.searchFolder(that.folderName, function(err, data){
			if(!err){
				
				var tpl = 'template-container-install-form';
				var tplBtn = 'template-btn-install';
				var docs = data.content.items[0];
				if(!jQuery.isEmptyObject(docs)){
					tpl = 'template-container-process-installed';
					tplBtn = 'template-btn-installed';					
				}
				
				$('#up-area-' + that.instanceId).append(that.processTemplate(that.templates[tpl], {}));
				$('#up-btn-area-' + that.instanceId).html(that.processTemplate(that.templates[tplBtn], {}));
			}
		})
	},
	
	processTemplate: function(template, data) {
		var html = Mustache.render(template, data);
		return html;
	},

	bindings: {
		local: {			
			'install': ['click_createGroupAndFolder']
		}
	},
	
	/************************************/
	/******* CRIA GRUPO E PASTA *********/
	/************************************/
	createGroupAndFolder: function(){
		var that = this;		
		this.createFolderService(this.folderName, function(err, data){
			if(!err){
				$('#up-folder-icon').removeClass('fluigicon-remove-sign').addClass('fluigicon-check-circle-on');
				that.folderId = data.content.id;
				that.getCreateCardXML();
			}
		});		
	},
	
	
	/*********** CREATE CARD: WS para criar o formulario ************/	
	
	/**
	 * Arquivo XML(ECMCardIndexService_createCard.xml) do WebService
	 */
	getCreateCardXML: function(){
		var that = this;
		$.ajax({
			  url: '/wi_form/resources/form/ECMCardIndexService_createCard.xml',
			  type: 'GET',
			  dataType: 'xml',
			  headers:{'Content-Type':'text/xml','X-Requested-With':'XMLHttpRequest'},
			  processData: false,
			  success: function(xml){	  
				  that.extractFormBase64($(xml));
			  }
		});		
	},	
	
	/**
	 * Tranasforma o formulario HTML(formulario-simples.html) em Base64
	 */
	extractFormBase64: function(xml){
		var that = this;		
		$.ajax({
			url: '/wi_form/resources/form/formulario-simples.html',
			type: 'GET',
			dataType: 'binary',
			headers:{'Content-Type':'text/html','X-Requested-With':'XMLHttpRequest'},
			processData: false,
			success: function(html){				
				var reader = new window.FileReader();		        
		        reader.readAsDataURL(html); 
		        reader.onloadend = function () {
		            that.buildXmltoWS(xml, reader.result.split(';base64,')[1]);
		        }				
			}, error: function (xhr, ajaxOptions, thrownError) {
		  	}
		});
	},
	 
	/**
	 * Monta o XML do WS(ECMCardIndexService_createCard.xml) com o HTML convertido para Base64 em anexo
	 */
	buildXmltoWS: function(xml, html){
		xml.find('username').text(WCMAPI.userLogin);
		xml.find('companyId').text(WCMAPI.getTenantId());
		xml.find('parentDocumentId').text(this.folderId);
		xml.find('publisherId').text(WCMAPI.userLogin);	
		
		var attachmentItem = '<item><attach>true</attach><fileName>formulario-simples.html</fileName><filecontent>' + html + '</filecontent><principal>true</principal></item>';
        xml.find('Attachments').append(attachmentItem);
        
		this.uploadFormWS(xml);
	},
	
	/**
	 * WebService para criar um formulário no fluig
	 */
	uploadFormWS: function(xml){
		var that = this;
		WCMAPI.Create({
		    async: false,
		    url: WCMAPI.serverURL + '/webdesk/ECMCardIndexService?wsdl',
		    contentType: 'text/xml; charset=utf-8',
		    dataType: 'xml',
		    data: xml[0],
		    success: function(data) {
		    	if($(data.getElementsByTagName('documentId')[0])){
		    		
		    		// o response do WS é o id do formulário. 
		    		var formId = $(data.getElementsByTagName('documentId')[0]).text();		    		
		    		if(formId){
		    			$('.up-form-icon').html('<span class="fluigicon fluigicon-check-circle-on fluigicon-md">');
						$('#up-btn-area-' + that.instanceId).html(that.processTemplate(that.templates['template-btn-installed'], {}));
		    			FLUIGC.toast({
		    		        title: '',
		    		        message: '${i18n.getTranslation("toast.form.installed")}',
		    		        type: 'success'
		    		    });
		    		}
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    }
		});
	},
	
	/**
	 * API Rest para criar a pasta para o formulario
	 */	
	createFolderService: function(folderName, cb){
		var options, url = '/api/public/ecm/document/createFolder';
		var params = {
				description: folderName,
				parentId: '0'
		};
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(params), 
			type: 'POST',
			loading: false
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * API Rest para buscar uma pasta
	 */	
	searchFolder: function(folderName, cb){
		var options, url = '/api/public/2.0/search/';
		var params = {
			"searchType" : "FOLDER",
			"pattern" : folderName,   
			"ordering" : "RELEVANT",
			"limit" : "10",
			"offset" : "0"
		};
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(params), 
			type: 'POST',
			loading: false
		};
		FLUIGC.ajax(options, cb);
	}	
})