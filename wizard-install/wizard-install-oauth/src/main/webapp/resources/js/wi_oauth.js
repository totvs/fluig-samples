var Wioauth = SuperWidget.extend({
	
	provider: {
		'code': 'codeForOAuthProvider',
		'description': 'descriptionForOAuthProvider',
		'name': 'wcm'			
	},

	app: {
		'consumerKey': 'myConsumerKeyForMyApp',
		'consumerSecret': 'myConsumerSecretForMyApp',
		'description': 'OAuth App for my fluig Store App'			
	},

	/**
	 * 
	 */	
	loading: null,

	// bind dos botões na tela
	bindings: {
		local: {
			'install': ['click_doInstall']			
		}
	},

	/**
	 * Função inicial. Verifica se o oauth app já está instalado
	 */
	init: function() {
		var that = this;

		$('[data-consumer-key]').val(that.app.consumerKey);
		$('[data-consumer-secret]').val(that.app.consumerSecret);		
		
		this.serivceSearchOauthApp(that.app.consumerKey, function(err, data){
			var tplBtn = 'template-btn-install';
			if(!err){
				$('.fluigicon-remove-sign').each(function(index){
					$(this).removeClass('fluigicon-remove-sign').addClass('fluigicon-verified');
				});
				tplBtn = 'template-btn-installed';
				
				var token = data.token;
				$('[data-access-token]').val(token.tokenAccess);
				$('[data-token-secret]').val(token.tokenSecret);
			}
			$('#oa-btn-area').html(that.processTemplate(that.templates[tplBtn], {}));
		});		
	},
	
	processTemplate: function(template, data) {
		var html = Mustache.render(template, data);
		return html;
	},

	/**
	 * 
	 */
	doInstall: function(){
		var that = this;
		var l = FLUIGC.loading('#oa-table');
		l.show();
		
		var oap = this.buildObjetcToOauthProvider();
		
		//cria o oauth provider
		this.serviceCreateOauthProvider(oap, function(err, data){
			if(!err){				
				var oaapp = that.buildObjectToOauthApp();
				
				// cria o oauth app
				that.serviceCreateOauthApp(oaapp, function(err, data){
					if(!err){
						var obj = 'consumerKey='+that.app.consumerKey;
						
						// gera os tokes para o app
						that.serviceGenerateKeys(obj, function(err, data){
							if(!err){
								that.chnageLabelAndInput();
							}							
						});
					}
				});
			}
		});

		l.hide();
	},
	
	chnageLabelAndInput: function(oauth){
		FLUIGC.toast({
	        title: '',
	        message: '${i18n.getTranslation('toast.oa.installed')}',
	        type: 'success'
	    });
		
		this.serivceSearchOauthApp(this.app.consumerKey, function(err, data){
			if(!err){
				var token = data.token;
				
				$('[data-access-token]').val(token.tokenAccess);
				$('[data-token-secret]').val(token.tokenSecret);
				
				$('.fluigicon-remove-sign').each(function(index){
					$(this).removeClass('fluigicon-remove-sign').addClass('fluigicon-verified');
				}); 
				$('#oa-btn-area').html(that.processTemplate(that.templates['template-btn-installed'], {}));				
			}
		});		
	},
	
	buildObjetcToOauthProvider: function(){		
		var that = this;
		var data = {
		    'formData': {
		        'key': '',
		        'idEdit': '',
		        'codeEdit': '',
		        'code': that.provider.code,
		        'providerName': that.provider.name,
		        'description': that.provider.description,
		        'accessTokenURL': '',
		        'requestTokenURL': '',
		        'userAuthorizationURL': '',
		        'requestMethod': 'GET',
		        'signatureMethod': 'HMAC-SHA1'
		    },
		    'config': {
		        'validateFields': [{
		            'key': 'code'
		        }, {
		            'key': 'description'
		        }]
		    }
		}
		
		return data;
	},	
	
	buildObjectToOauthApp: function(){
		var that = this;
		var data = {
		    'formData': {
		        'key': '',
		        'providerCode': '',
		        'idEdit': '',
		        'consumerKeyEdit': '',
		        'consumerKey': that.app.consumerKey,
		        'description': that.app.description,
		        'selectedProvider': that.provider.code,
		        'msprotheus-client-communication': '',
		        'msprotheus-environment': '',
		        'msprotheus-language': '',
		        'msprotheus-url-ajax-server': '',
		        'msprotheus-url-webservice': '',
		        'rm-alias': '',
		        'rm-url-content': '',
		        'rm-url-webservice': '',
		        'logix-companyId': '',
		        'logix-environment': '',
		        'logix-language': '',
		        'logix-url-ajax-server': '',
		        'logix-url-webservice': '',
		        'consumerSecret': that.app.consumerSecret
		    },
		    'config': {
		        'validateFields': [{
		            'key': 'consumerKey'
		        }, {
		            'key': 'description'
		        }, {
		            'key': 'consumerSecret'
		        }]
		    }
		}
		return data;		
	},

	serviceCreateOauthProvider: function(data, cb) {
		var options,			
			url = '/portal/api/rest/wcm/service/oauth/provider/create';
		data = JSON.stringify(data);		
		options = {
				url: url,
				contentType: 'application/json',
				dataType: 'json',
				data: data,
				type: 'POST'
		};
		FLUIGC.ajax(options, cb);
	},
	
	serviceCreateOauthApp: function(data, cb) {
		var options,			
			url = '/portal/api/rest/wcm/service/oauth/application/create';
		data = JSON.stringify(data);
			
		options = {
				url: url,
				contentType: 'application/json',
				dataType: 'json',
				data: data,
				type: 'POST'
		};
		FLUIGC.ajax(options, cb);
	},
	
	serviceGenerateKeys: function(data, cb) {
		var options,			
			url = '/portal/api/rest/wcm/service/oauth/applicationuser/create';		
		options = {
				url: url,
				dataType: 'text',
		        contentType: 'application/x-www-form-urlencoded',
				data: data,
				type: 'POST'
		};
		FLUIGC.ajax(options, cb);
	},
	
	serivceSearchOauthApp: function(consumerKey, cb){
		var options,			
		url = '/portal/api/rest/wcm/service/oauth/applicationuser/find?consumerKey=' + consumerKey;
		options = {
			url: url,
			contentType: 'application/json',
			dataType: 'json'
		};
		FLUIGC.ajax(options, cb);
	}
	
});
