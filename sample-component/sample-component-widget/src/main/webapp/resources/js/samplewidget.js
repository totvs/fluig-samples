var SampleWidget = SuperWidget.extend({
	
	datatableViewUsers: null,
	slotId: 4012,

	bindings: {
		// binding dos botões relacionados com cada função 
		local: {
			'do-something': ['click_someFunc'],
			'load-table': ['click_loadTable'],
			'remove-user': ['click_removeUser'],
			'sample-rest': ['click_loadSampleRest']
		}
	},

	init: function() {
		var that = this;
		// se desejar, coloque aqui instruções iniciais de cada widget
		that.loadPage();
		that.checkLicense();		
	},
	
	/**
	 * Função para verificar se o cliente possui licença para utilizar o app, através do SlotID 
	 */
	checkLicense: function(){
		var that = this;
		this.serviceCheckLicense(function(err, data){			
				var template = that.templates['template-license-ok'],				
					html = '';
				
			if(!data.valid){
				template = that.templates['template-license-non-ok'];
			}
			
			html = Mustache.render(template, {});			
			$('#mainDiv').append(html);
		});		
	},

	/**
	 * Função para exibir a data atual	 * 
	 */
	someFunc: function(el, ev) {	
		var d = new Date();
	    var curr_date = d.getDate();
	    var curr_month = d.getMonth() + 1;
	    var curr_year = d.getFullYear();
		FLUIGC.toast({
			message: '${i18n.getTranslation("msg.today.is")}: ' + curr_date + '/' + curr_month + '/' + curr_year,
			type: 'success'
		});
	},

	/**
	 * Função que carrega os dados da widget
	 */
	loadPage: function() {
		var that = this,
			template = that.templates['template-users-content'],
			html = '';
		
		html = Mustache.render(template, {});
		$('[data-users-content]').append(html);
	},
	
	/**
	 * Função para criar o datatable
	 */
	loadTable: function(el, ev) {
		this.loadContentFromTemplate();
	},

	/**
	 * Remove um usuário da tabela. Apenas em tela
	 */
	removeUser: function(el, ev) {
		var that = this,
			itemSelect = that.datatableViewUsers.selectedRows()[0];
		console.log(itemSelect);
		if(itemSelect >= 0) {
			FLUIGC.message.confirm({
			    message: '${i18n.getTranslation("msg.remove.selected.user")}',
			    title: '${i18n.getTranslation("label.remove.user")}',
			    labelYes: '${i18n.getTranslation("label.yes")}',
			    labelNo: '${i18n.getTranslation("label.no")}',
			}, function(result, el, ev) {
				if(result) {
					that.datatableViewUsers.removeRow(itemSelect);
					that.datatableViewUsers.reload();
					FLUIGC.toast({
						message: '${i18n.getTranslation("msg.alert.ok")}',
						type: 'success'
					});
				}
			});
		}
	},

	/**
	 * Função para request da API
	 */
	loadContentFromTemplate: function() {
		var that = this;
		
		that.serviceGetUsers(function(err, data) {
			if(err) {
		    	FLUIGC.toast({
			        message: 'Erro',
			        type: 'danger'
				});
				return false;
		    }
			that.buildDatatableViewUsers(data)
		});
	},

	/**
	 * Constrói o datatable com os dados da API serviceGetUsers
	 */
	buildDatatableViewUsers: function(data) {
		var that = this;
		that.datatableViewUsers = FLUIGC.datatable('[data-sample-table]', {
		    emptyMessage: '<div class="text-center">Não há dados para exibir.</div>',
			header: [
				{'title': 'Name'},
				{'title': 'Username'},
				{'title': 'Address'},
				{'title': 'Email'},
				{'title': 'Phone'},
				{'title': 'Delete'}
		    ],
		    dataRequest: data,
			renderContent: '.template-list-users',
		    classSelected: 'active',
		    actions: {enabled: false},
		    search: {enabled: false},
		    navButtons: {enabled: false}
		}, function(err, data) {
		    if(err) {
		    	FLUIGC.toast({
			        message: 'Erro',
			        type: 'danger'
			    });
		    }
		});
	},
	
	/**
	 * Função para uma api do próprio sample component
	 */
	loadSampleRest: function(){
		var that = this;
		this.serviceSampleRest(function(err, data){
			if(err) {
		    	FLUIGC.toast({
			        message: 'Erro',
			        type: 'danger'
				});
				return false;
		    }			
			that.buildDatatableItems(data);
		});		
	},
	
	/**
	 * Constrói o datatable da API serviceSampleRest
	 */
	buildDatatableItems: function(data) {
		var that = this;
		that.datatableViewUsers = FLUIGC.datatable('[data-sample-table]', {
		    emptyMessage: '<div class="text-center">Não há dados para exibir.</div>',
			header: [
				{'title': 'id'},
				{'title': 'login'},
				{'title': 'Nome'},
				{'title': 'eamil'}
		    ],
		    dataRequest: data,
			renderContent: '.template-list-item',
		    classSelected: 'active',
		    actions: {enabled: false},
		    search: {enabled: false},
		    navButtons: {enabled: false}

		}, function(err, data) {
		    if(err) {
		    	FLUIGC.toast({
			        message: 'Erro',
			        type: 'danger'
			    });
		    }
		});
	},

	/**
	 *  Request para uma API externa ao fluig
	 */
	serviceGetUsers: function(cb) {
		var options,
			url = 'https://jsonplaceholder.typicode.com/users',
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: true
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * Request para uma api do próprio sample-component, desenvolvida em Java
	 *
	 */
	serviceSampleRest: function(cb) {
		var options,
			url = '/samplecomponentweb/v1/samplerest',
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: true
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * Request para a API de License do fluig
	 */
	serviceCheckLicense: function(cb){
		var options,
		url = '/license/api/v1/slots/' + this.slotId,
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: true
		};
		FLUIGC.ajax(options, cb);		
	}

});