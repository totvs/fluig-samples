var SampleWidget = SuperWidget.extend({
	
	datatableViewUsers: null,

	bindings: {
		// binding dos botões relacionados com cada função 
		local: {
			'do-something': ['click_someFunc'],
			'load-table': ['click_loadTable'],
			'remove-user': ['click_removeUser']
		}
	},

	init: function() {
		var that = this;
		// se desejar, coloque aqui instruções iniciais de cada widget
		that.loadPage();
	},

	/**
	 * Função para exibir a data atual
	 * @returns
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

	loadPage: function() {
		var that = this,
			template = that.templates['template-users-content'],
			html = '';
		
		html = Mustache.render(template, {});
		$('[data-users-content]').html(html);
	},

	loadTable: function(el, ev) {
		this.loadContentFromTemplate();
	},

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

	loadContentFromTemplate: function() {
		var that = this,
			template = that.templates['template-users-table'],
			html = '';
		
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

	buildDatatableViewUsers: function(data) {
		var that = this;
		that.datatableViewUsers = FLUIGC.datatable('[data-users-table]', {
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
			//multiSelect: true,
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
	}

});