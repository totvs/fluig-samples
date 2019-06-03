var SampleWidget = SuperWidget.extend({
	
	datatableViewUsers: null,
	slotId: 4012,

	i18n: {
		'msg.welcome': function () {
			return '${i18n.getTranslationP1("msg.welcome", "' + arguments[0] + '")}';
		}
	},

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
		that.welcomeMsg();
		that.loadPage();
		that.checkLicense();
	},
	
	welcomeMsg: function(){
		var that = this;
		FLUIGC.toast({			
			message: that.i18n['msg.welcome'](WCMAPI.getUser()),
			type: 'success'
		});		
	},
	
	/**
	 * Função para verificar se o cliente possui licença para utilizar o app, através do SlotID 
	 */
	checkLicense: function(){
		var that = this;
		this.serviceCheckSlotId(function(err, data){			
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
			        message: '${i18n.getTranslation("msg.error")}',
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
		    emptyMessage: '<div class="text-center">${i18n.getTranslation("msg.no.data.found")}</div>',
			header: [
				{'title': '${i18n.getTranslation("label.name")}'},
				{'title': '${i18n.getTranslation("label.login")}'},
				{'title': '${i18n.getTranslation("label.address")}'},
				{'title': '${i18n.getTranslation("label.email")}'},
				{'title': '${i18n.getTranslation("label.phone")}'},
				{'title': '${i18n.getTranslation("label.delete")}'}
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
		    		message: '${i18n.getTranslation("msg.error")}',
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
		    		message: '${i18n.getTranslation("msg.error")}',
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
			emptyMessage: '<div class="text-center">${i18n.getTranslation("msg.no.data.found")}</div>',
			header: [
				{'title': '${i18n.getTranslation("label.id")}'},
				{'title': '${i18n.getTranslation("label.login")}'},
				{'title': '${i18n.getTranslation("label.name")}'},
				{'title': '${i18n.getTranslation("label.email")}'}
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
		    		message: '${i18n.getTranslation("msg.error")}',
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
			url = '/samplecomponent/v1/myrest',
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: true
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * Request para a API de License do fluig: verificar slotId
	 */
	serviceCheckSlotId: function(cb){
		var options,
		url = '/license/api/v1/slots/' + this.slotId,
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: true
		};
		FLUIGC.ajax(options, cb);
	},
	
	/**
	 * Request para a API de License do fluig: verificar o nº de licenças contratadas
	 */
	serviceCheckAvailableUsers: function(cb){
		var options,
		url = '/license/api/v1/licenses/' + this.slotId,
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json',
			loading: true
		};
		FLUIGC.ajax(options, cb);
	}

});