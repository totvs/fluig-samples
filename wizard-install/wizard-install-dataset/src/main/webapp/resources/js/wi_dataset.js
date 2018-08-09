var Widataset = SuperWidget.extend({

	/**
	 *  Lista de datsets com: nome, campos e se ele deve ser sincronizado ou não
	 *  name: nome do dataset
	 * 	columns: campos(colunas) do dataset
	 *  sync: informa se o dataset será sincronizado ou não
	 * 
	 * obs1.: lembre-se colocar nomes bem específicos, para não conflitar com algum dataset já existentes
	 * obs2.. lembra-se que esses são datasets customizados, onde somente dados estáticos(para consulta). Caso queira atualizar algum dado, é preciso remover o dataset e recriá-lo
	 */
	datasets: [
		{'name':'ds_categorias', 'columns':['idCategoria', 'nomeCategoria'], 'sync': true},
		{'name':'ds_produtos', 'columns':['idProduto', 'nomeProduto'], 'sync': true}
	],

	loading: null,

	// bind dos botões na tela
	bindings: {
		local: {
			'install': ['click_doInstall']
		}
	},

	/**
	 * Função inicial. Verifica se os datasets já está instalados
	 */
	init: function() {
		var that = this;		
		var installed = false;
		for (var i = 0; i < this.datasets.length; i++) {
			var dt = DatasetFactory.getDataset(this.datasets[i].name, null,null,null);
			if(dt.values == undefined){
				this.datasets[i].installed = false;
			} else{
				this.datasets[i].installed = true;
				installed = true;
			}
		}

		var html;
		if(installed){
			html = this.processTemplate(that.templates['template-btn-installed'], {});
		} else{
			html = this.processTemplate(that.templates['template-btn-install'], {});
		}
		$('#dt-btn-area').html(html);
		
		// monta a tabela com os datasets a serem instalados		
		FLUIGC.datatable('#dt-table', {
			emptyMessage: '<div class="text-center">Não há dados para exibir.</div>',		    		    
		    renderContent: '.template-single-dataset',
		    header: [
		        {'title': '${i18n.getTranslation("label.dt.code")}'},
		        {'title': '${i18n.getTranslation("label.dt.fields")}'},
		        {'title': '${i18n.getTranslation("label.dt.sync")}'},
		        {'title': '${i18n.getTranslation("label.dt.installed")}'},
		    ],
			dataRequest: that.datasets,
			multiSelect: false,
		    actions: {enabled: false},
		    search: {enabled: false},
		    navButtons: {enabled: false}
		}, function(err, data) {		    
		});
	},
	
	processTemplate: function(template, data) {
		var html = Mustache.render(template, data);
		return html;
	},

	/**
	 * Percorre a lista para criar os datasets
	 */
	doInstall: function(){
		var l = FLUIGC.loading('#dt-table');
		l.show();
		for (var i = 0; i < this.datasets.length; i++) {
			var dt = this.datasets[i];
			this.buildDataSetFunction(dt.name, dt.columns, dt.sync);
		}
		this.changelabel();
		FLUIGC.toast({
	        title: '',
	        message: '${i18n.getTranslation("toast.dt.installed")}',
	        type: 'success'
	    });	
		l.hide();
	},
	
	// Monta a função do dataset
	buildDataSetFunction: function(dsName, columns, isSync){
		var that = this;
		var impl = 'function createDataset(fields, constraints, sortFields){var dataset = DatasetBuilder.newDataset(); ';
		for (var i = 0; i < columns.length; i++) {
			impl += ' dataset.addColumn("'+ columns[i] +'");';
		}
		impl += ' dataset.addRow(new Array(1, "Nome 1"));';
		impl += ' dataset.addRow(new Array(2, "Nome 2"));';
		impl += ' return dataset};';		

		this.criarDataset(dsName, impl, isSync);
	},
	
	/**
	 * WEbService SOAP para criar o dataset
	 */
	 criarDataset: function(dsName, impl, isSync){
		var that = this;
    	that.xml = null;
    	$.ajax({
    		url : "/wi_dataset/resources/xml/ECMDatasetService_addDataset.xml",
    		type: "GET",
    		datatype: "xml",
			headers:{'Content-Type':'text/xml','X-Requested-With':'XMLHttpRequest'},
			processData: false,
    		success: function(xml){
    			that._xml = $(xml);
    	    	that._xml.find("companyId").text(WCMAPI.getTenantId());
    	    	that._xml.find("username").text(WCMAPI.userLogin);
    	    	that._xml.find("name").text(dsName);
    	    	that._xml.find("description").text(dsName);
    	    	that._xml.find("impl").text(impl);
    	    	var xml = that._xml[0];
    	    	WCMAPI.Create({
    	    		url : WCMAPI.serverURL + "/webdesk/ECMDatasetService?wsdl",
    	    		contentType: "text/xml; charset=utf-8",
    	    		dataType: "xml",
    	    		data: that._xml[0],
    	    		success: function(data){    	    			
    	    			// caso o dataset for Sincronizado, executa uma requisição Rest para editar o dataset
						if(isSync){
							that.setDatasetToSync(dsName, impl);
						}
    	    		},
    	    		error: function(err){
    	    			console.log(err);
    	    		}
    	    	});
    		},
    		error: function(){
    		}
    	});    	
    },
	
	/**
	 * Monta o objeto para tornar o dataset sincronizado
	 */
    setDatasetToSync: function(dsName, impl){    	
    	var data = {
			'datasetId': dsName,
		    'datasetDescription': dsName,
		    'datasetImpl': impl,
		    'datasetBuilder': 'com.datasul.technology.webdesk.dataset.CustomizedDatasetBuilder',
		    'serverOffline': true,
		    'mobileCache': false,
		    'internal': false,
		    'custom': true,
		    'generated': false,
		    'offlineMobileCache': false,
		    'mobileOfflineSummary': 'Não',
		    'updateInterval': 0,
		    'lastReset': new Date().getTime(),
		    'lastRemoteSync': 0,
		    'jobLastExecution': '-',
		    'jobNextExecution': '-',
		    'type': 'CUSTOM',
		    'journalingAdherenceFull': false,
		    'journalingAdherenceHalf': false,
		    'journalingAdherenceNone': false,
		    'syncStatusSuccess': false,
		    'syncStatusWarning': false,
		    'syncStatusError': false,
		    'syncDetails': 'DatasetMetalistId: 5\n\n\n0 of delete scripts ran succesfully\n0 of update scripts ran succesfully\n0 of insert scripts ran succesfully'
		}
    	
    	this.updateDataset(data, function(){});
	},
	
	changelabel: function(){
		$('.dt-status').html(this.processTemplate(this.templates['template-dt-installed'], {}));
		$('#dt-btn-area').html(this.processTemplate(this.templates['template-btn-installed'], {}));
	},
	
	/**
	 * API Rest para editar o dataset e torná-lo sincronizado
	 */
    updateDataset: function(data, cb){    	
		var options = {
			url: '/ecm/api/rest/ecm/dataset/updateServerSync',
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify(data),
			type: 'POST'			
		};
		FLUIGC.ajax(options, cb);
    }
	
});