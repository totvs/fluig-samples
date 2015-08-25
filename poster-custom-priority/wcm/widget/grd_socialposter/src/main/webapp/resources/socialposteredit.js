var SocialPosterEdit = SuperWidget.extend({

	instanceId: null,
	context: null,
	communityName: null,
	communityAlias: null,
	communityPageAlias: null,
	DOM: null,

	bindings: {
		local: {
			'context': ['change_changeContext'],
			'save-poster-context': ['click_updatePosterPreferences'],
			'form-edit':  ['submit_saveForm']

		},
		global: {

		}
	},

	init: function() {
		if (!this.isCommunityPage()) {
			this.initContextComplete();
			this.loadPosterConfig();
		} else {
			this.setContextWithoutChange();
		}
	},

	loadPosterConfig: function() {
		var that = this;
		if(that.communityAlias) {
			this.returnSocialCommunity(that.communityAlias);
		}
		that.setContext(that.context, true);
	},

	// radio behavior
	changeContext: function(el, ev) {
		var $el = $(el),
			value = $el.val()
		;
		this.setContext(value, false);
	},

	saveForm: function(el, ev) {
		ev.preventDefault();
	},

	// valid page by function
	isCommunityPage: function() {
		this.communityPageAlias = socialGlobal.getAliasCommunityPage();
		if(this.communityPageAlias !== null) {
			return true;
		}
		return false;
	},

	setContextWithoutChange: function() {
		this.returnSocialCommunity(this.communityPageAlias);
		$('[data-community-details]', this.DOM).removeClass('c-hidden');
		$('[data-context]', this.DOM).prop('disabled',true);
		$('[data-search-community]', this.DOM).prop('disabled',true);
		$('[data-save-poster-context]', this.DOM).prop('disabled',true);
		$('[data-context][value="contextCommunity"]', this.DOM).prop('checked', true);
	},

	setContext: function(value, getServerData) {
		if(value === 'contextCommunity') {
			$('[data-community-details]', this.DOM).removeClass('c-hidden');
			this.context = 'contextCommunity';
		} else {
			$('[data-community-details]', this.DOM).addClass('c-hidden');
			this.context = 'contextFollowers';
			$('[data-context][value="contextFollowers"]', this.DOM).prop('checked', true);
		}

		if(getServerData) {
			// find communityName
			$('[data-context][value="'+value+'"]', this.DOM).prop('checked', true);
			if(this.communityName){
				$('[data-community-details]', this.DOM).find('label small').text(this.communityName);
			}
		}
	},

	// save
	updatePosterPreferences: function(el) {
		var that = this,
		args = {},
		$communitySelected = $('[data-community-details]', this.DOM).find('label small'),
		hasError = false;

		var btn = $(el);
		btn.prop('disabled',true);

		args['context'] = this.context;
		if (this.context == "contextCommunity") {
	    	//  try save metadata without community
			if ($communitySelected.text() == '') {
				FLUIGC.toast({
					title: '${i18n.getTranslation("attention")}',
	    	        message: '${i18n.getTranslation("select.community")}',
	    	        type: "warning"
				});
	    		hasError = true;
	    		btn.prop('disabled',false);
	    	}
			args['communityAlias'] = this.communityAlias;
			args['communityName'] = this.communityName;
		} else {
			$communitySelected.text('');
			$('[data-search-community]').val('');
			args['communityAlias'] = '';

		}

		if(!hasError) {
			this.rest(WCMSpaceAPI.PageService.UPDATEPREFERENCES,
				[that.instanceId, args],
				function(res) {
					if(res) {
						FLUIGC.toast({
			    	        message: "${i18n.getTranslation('successs.update')}",
			    	        type: "success"
						});
					}
					btn.prop('disabled',false);
				},
				function(xhr, text, errData) {
					socialGlobal.alert(errData.message);
					btn.prop('disabled',false);
				}
			);
		}
	},

	// Find community by alias
	findCommunity: function(term, cb) {
		var url = '/api/public/social/community/listCommunities',
		data = {
				pattern: term,
				limit: 10,
				offset: 0,
			},
			options = {
				type: 'GET',
				url: url,
				contentType: 'application/json',
				data: data,
				dataType: 'json'
			}
		;

		$.ajax(options)
		.done(function(data) {
			var result = [],
				obj = {},
				data = data.content
			;
			for(var i = 0; i < data.length; i++) {
				obj = {};
				obj['label'] = data[i].name;
				obj['value'] = data[i].name;
				obj['alias'] = data[i].alias;
				result.push(obj);
			}
			cb(result);
		})
		.fail(function(xhr, status, text) {
			cb(xhr);
		});
	},

	//find community
	returnSocialCommunity: function(alias){
		var that = this,
			url =  '/api/public/social/community/' + alias ,
			options = {
				type: 'GET',
				url: url,
				contentType: 'application/json',
				dataType: 'json'
			};

		$.ajax(options)
		.done(function(data) {
			var community = data;
			that.communityName = community.name;

			$('[data-community-details]', that.DOM)
			.find('label small')
			.text(that.communityName);
		});
	},

	// auto complete function
	initContextComplete: function() {
		var that = this;
		$('[data-search-community]', this.DOM).autocomplete({
			source: function(req, cb) {
				that.findCommunity(req.term, cb);
			},
			select: function(ev, ui) {
				var value = ui.item.value;

				// set global attributes
				that.communityAlias = ui.item.alias;
				that.communityName =  ui.item.value;

				$('[data-community-details]', that.DOM)
					.find('label small')
					.text(value);
			}
		});
	}

});