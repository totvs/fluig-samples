var wiprocess = SuperWidget.extend({

	instanceId: null,
	fileBase64: null,
	folderId: null,
	formId: null,
	installed: false,
	loading: false,
	groupName: 'novo-grupo',
	folderName: 'nova-pasta-form',
	
	init: function() {
		var that = this;		
		// verifica se o componente já está instalado, através do code do grupo
		this.searchGroupService(function(err, data){
			if(!err){
				for (var i = 0; i < data.length; i++) {
					if(data[i] == that.groupName)
						that.installed = true;
				}
				var tpl = 'template-container-install-process';
				var tplBtn = 'template-btn-install';
				if(that.installed){
					tpl = 'template-container-process-installed';
					tplBtn = 'template-btn-installed';
					$("#botao_saveToken_" + that.instanceId).show();
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
	/**
	 * O grupo é criado pois uma atividade no workflow depende desse grupo
	 * A pasta é criada para ser a pasta do formulário
	 */
	createGroupAndFolder: function(){
		var that = this;
		this.loading = FLUIGC.loading('#uploadprocess_' + this.instanceId);
		this.loading.show();
		this.createGroupService(function(err, data){
			if(!err){
				$('#up-group-icon').removeClass('fluigicon-remove-sign').addClass('fluigicon-check-circle-on');
				that.createFolderService(function(err, data){
					if(!err){
						$('#up-folder-icon').removeClass('fluigicon-remove-sign').addClass('fluigicon-check-circle-on');
						that.folderId = data.content.id;
						that.getCreateCardXML();
					}
				});
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
			  url: '/wi_process/resources/process/ECMCardIndexService_createCard.xml',
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
			url: '/wi_process/resources/process/formulario-simples.html',
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
		    			$('#up-form-icon').removeClass('fluigicon-remove-sign').addClass('fluigicon-check-circle-on');
		    			that.formId = formId;
		    			that.getImportProcessXML();
		    		}
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    }
		});
	},
	
	
	/********** IMPORT PROCESS **********/
	/**
	 * Arquivo XML(ECMWorkflowEngineService_importProcess.xml) do WebService
	 */	
	getImportProcessXML: function(){
		var that = this;
		$.ajax({
			  url: '/wi_process/resources/process/ECMWorkflowEngineService_importProcess.xml',
			  type: 'GET',
			  dataType: 'xml',
			  headers:{'Content-Type':'text/xml','X-Requested-With':'XMLHttpRequest'},
			  processData: false,
			  success: function(ws){	  
				  that.buildProcessXML($(ws));
			  }
		});
	},
	
	/**
	 * Monta o XML do WS com o XML(em Base64) do processo em anexo
	 */
	buildProcessXML: function(ws){
		var that = this;
		$.ajax({
			url: '/wi_process/resources/process/processo-simples.xml',
			type: 'GET',
			dataType: 'binary',
	        contentType: "charset=utf-8",
			headers:{'Content-Type':'text/html','X-Requested-With':'XMLHttpRequest'},
			processData: false,
			success: function(xml){
				var reader = new window.FileReader();
		        reader.readAsDataURL(xml); 
		        reader.onloadend = function () {
		        	ws.find('username').text(WCMAPI.userLogin);
		    		ws.find('companyId').text(WCMAPI.getTenantId());
		    		ws.find('processId').text('processo-simples');
		    		ws.find('newProcess').text('true');
		    		ws.find('overWrite').text('true');
		    		ws.find('colleagueId').text(WCMAPI.userLogin);		    		
		    		
		    		// anexa o worflow no XML do WS
		    		var attachmentItem = '<item>\
		    			<attach>true</attach>\
		    			<fileName>processo-simples.xml</fileName>\
		    			<filecontent>' + reader.result.split(';base64,')[1] + '</filecontent>\
		    			</item>';
		    		ws.find('attachments').append(attachmentItem);
		    		
		            that.uploadProcessWS(ws);
		        }				
			}, error: function (xhr, ajaxOptions, thrownError) {
		  	}
		});
	},
	
	/**
	 * WebService para criar um processo no fluig 
	 */
	uploadProcessWS: function(ws){
		var that = this;
		WCMAPI.Create({
		    async: false,
		    url: WCMAPI.serverURL + '/webdesk/ECMWorkflowEngineService?wsdl',
		    contentType: 'text/xml; charset=utf-8',
		    dataType: 'xml',
		    data: ws[0],
		    success: function(data) {		    	
		    	if(data){		    		
		    		that.updateProcess();
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    }
		});
	},
	
	
	/**
	 * Após criar o processo, é necessário editar o processo com os dados corretos;
	 */
	updateProcess: function(){
		var that = this;
		
		// coloca o processo em modo de edição. No callback, faz a atualização como id do formulario correto
		this.processEditModeService(function(){			
			$.ajax({
				url: '/wi_process/resources/process/processo-simples.xml',
				type: 'GET',
				dataType: 'xml',
				headers:{'Content-Type':'text/xml','X-Requested-With':'XMLHttpRequest'},
				processData: false,
				success: function(xml){
	                var wf = $(xml);
	                wf.find('formId').text(that.formId);
					wf.find('companyId').text(WCMAPI.getTenantId());
					wf.find('version').text('1');
					
					// atualiza processo pela API rest
					var x = (new XMLSerializer()).serializeToString(xml);
					that.saveProcessService(x, function(error, data){
						if(!error){							
							// liberar o processo para uso
							that.releaseProcessService(function(err, data){
								if(!err){									
									FLUIGC.toast({
								        title: '',
								        message: '${i18n.getTranslation("msg.alert.items.created")}',
								        type: 'success'
								    });	
									$('#up-process-icon').removeClass('fluigicon-remove-sign').addClass('fluigicon-check-circle-on');
									$('#up-btn-area-' + that.instanceId).html(that.processTemplate(that.templates['template-btn-installed'], {}));
									that.loading.hide();
								} else{
									that.loading.hide();
								}
							});
						}
					});
					
				}, error: function (xhr, ajaxOptions, thrownError) {
			  	}
			});			
		});
	},
	
	/**
	 * API Rest para colocar o processo em mode de edição
	 */
	processEditModeService: function(cb) {
		var options, url = '/ecm/api/rest/ecm/workflowModeling/newversion?processId=processo-simples';		
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: false
		};
		FLUIGC.ajax(options, cb);
	},	
	
	/**
	 * API Rest para salvar processo
	 */
	saveProcessService: function(xml, cb) {
		var options, url = '/ecm/api/rest/ecm/workflowModeling/saveProcess';
		var params = {
				processDiagramXML: xml
		};
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			data: xml, 
			type: 'POST',
			loading: false
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * API Rest para liberar o processo
	 */
	releaseProcessService: function(cb){
		var options, url = '/ecm/api/rest/ecm/workflowModeling/releaseprocess?processId=processo-simples';		
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: false
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * API Rest para criar a pasta para o formulario
	 */	
	createFolderService: function(cb){
		var that = this;
		var options, url = '/api/public/ecm/document/createFolder';
		var params = {
				description: that.folderName,
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
	 * API Rest para criar o grupo
	 */	
	createGroupService: function(cb){
		var options, url = '/api/public/2.0/groups/create';
		var params = {
				code : this.groupName,
				description : "Grupo para usuarios aprovadores do Processo Simples",
				isInternal : "false"
		}
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
	 * API Rest para pesquisar grupo
	 */
	searchGroupService: function(cb){
		var options, url = '/api/public/wcm/group';		
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: false
		};
		FLUIGC.ajax(options, cb);
	}	
})