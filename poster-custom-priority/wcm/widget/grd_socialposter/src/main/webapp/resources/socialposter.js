var SocialPoster = SuperWidget.extend({

	instanceId: null,
	alias: null,
	selectedSociable: null,
	loggedSocial: null,
	social: null,
	selectedContext: null,
	typeConfigActions: null,

	bindings: {
		local: {
			'post-share-text': ['focus_shareTextFocus', 'blur_shareTextBlur', 'input_shareTextInput', 'keyup_shareTextKeyUp'],
			'post-share-form': ['submit_postFormSubmit'],
			'attach-media': ['click_attachMedia'],
			'close-post-share': ['click_closePostShare'],
			'remove-attached-media': ['click_removeAttachedMedia'],
			'poster-can-action': ['click_canAction']
		},
		global: {}
	},

	init: function() {
		if (this.alias == "") {
			socialGlobal.showHome();
		}
		this.loadSocialPoster(this.alias);
		
		/*
		 * 
		 * No nosso exemplo, a social poster pode possuit 3 valores
		 NOTHING --> ao postar não existirá nenhuma forma de socialização	
		 ALL --> socialização default do produto
		 OPTIONAL --> o usuario escolhe a forma de socializacao do post
		 
		 
		 Resumidamente no metodo loadPermissions() nos buscamos via dataset os dados cadastrados
		 no formulario para a comunidade e de acordo do que foi cadastrado retornamos
		 um dos tres valores.
		 
		 */
		
		var permissionsResult = this.loadPermissions();
		
		//console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>permissionsResult="+permissionsResult);
		this.typeConfigActions = permissionsResult;
	},
	
	loadPermissions: function() {
		
		//Se nao for comunidade retorna o tipo default
		if( this.socialType != "COMMUNITY") {
			return "ALL";
		}
		
		var defaultPoster = "ALL";
		
		//Buscando os dados do dataset do formulario de comunidades criado
		// nao se esquece de importar o vcXMLRPC.js no view.ftl para poder buscar dados de dataset.
		var c1 = DatasetFactory.createConstraint('comunityAlias', this.alias, this.alias, ConstraintType.MUST);
		var fields = new Array('comunityAlias', 'defaultOp',
	            'nothingOp', 'optionalOp');
	    var sortingFields = new Array();
		var constraints   = new Array(c1);

		try {
	        var dataset = DatasetFactory.getDataset("comunidadesPoster", fields, constraints, null);
	        
	        
	        if(dataset.values.length == 0 || dataset.values[0].defaultOp == "default") {
	        	return defaultPoster;
	        } else if(dataset.values[0].optionalOp == "optional") {
	        	return "OPTIONAL";
	        } else if(dataset.values[0].nothingOp == "nothing") {
	        	return "NOTHING";
	        } 
	        
	    } catch(erro) {
	    	alert(erro);
	    }

	    return defaultPoster;
	},
	
	canAction: function(el, ev) {
		ev.stopPropagation();
	},

	// Esta função centraliza todos os serviços necessários
	// para o carregamento da página, monta um objeto 'view'
	// que é renderizado (template via Mustache)
	loadSocialPoster: function(alias) {
		var that = this,
			tpl = that.templates.tpl_social_poster,
			poster = $('#socialposter_' + that.instanceId),
			view,
			html,
			isUserOwnPage,
			isMember,
			memberInGroup,
			aliasCommunity,
			social,
			loggedSocial,
			folderEnabled,
			documentIsEnabled,
			photoIsEnabled,
			videoIsEnabled;

		var socialCommunity = that.serviceSocialCommunity(alias, function(communityInfo) {
			social = communityInfo;
			documentIsEnabled = communityInfo.document.socialFolderEnabled;
			photoIsEnabled = communityInfo.galleryPhoto.socialFolderEnabled;
			videoIsEnabled = communityInfo.galleryVideo.socialFolderEnabled;
		});

		var socialUserLogged = that.getSocialUserLogged(function(loggedSocialData) {
			loggedSocial = loggedSocialData;
		});

		$.when(
				socialCommunity,
				socialUserLogged

		).then(function() {

			isUserOwnPage = (social.id === loggedSocial.id);
			isTenantAdmin = loggedSocial.tenantAdmin;

			if((isUserOwnPage) || (social.type === 'COMMUNITY' && social.participantStatus === 'ACCEPTED') || (social.type === 'USER' && social.participantStatus === 'ACCEPTED')) {
				isMember = true;
			} else {
				isMember = false
			}

			memberInGroup = isUserOwnPage || isMember || isTenantAdmin;

			folderEnabled = {
				document: documentIsEnabled,
				photo: photoIsEnabled,
				video: videoIsEnabled
			};

			if(!memberInGroup) {
				aliasCommunity = socialGlobal.getAliasCommunityPage();
				if(aliasCommunity !== null) {
					that.renderViewNoContent();
					return false;
				}
				that.renderViewMemberNotInGroup();
				return false;
			}

			view = {
					socialStateBlocked: (social.state === 'BLOCKED'),
					notSocialStateBlocked: (social.state != 'BLOCKED'),
					socialStateActive: (social.state === 'ACTIVE'),
					socialType: social.type,
					socialTypeCommunity: (social.type === 'COMMUNITY'),
					socialTypeUser: (social.type === 'USER'),
					notSocialTypeUser: (social.type != 'USER'),
					socialAlias: alias,
					isUserOwnPageOrMember: isUserOwnPage || isMember || (isTenantAdmin && social.type === 'COMMUNITY'),
					socialPrivateContent: social.privateContent,
					notSocialPrivateContent: !social.privateContent,
					postShareVisibility: (social.type === "COMMUNITY" && social.privateContent),
					documentIsEnabled: folderEnabled.document,
					photoIsEnabled: folderEnabled.photo,
					videoIsEnabled: folderEnabled.video,
					isContextFollowers: (that.selectedContext === 'contextFollowers'),
					isContextCommunities: (that.selectedContext === 'contextCommunity'),
					isContextUndefined: (that.selectedContext === ''),
					canConfigActions: that.typeConfigActions === 'OPTIONAL'
			};

			html = Mustache.render(tpl, view);
			poster.html(html);
			that.postCreate();
			that.placeholderIE();
			that.mentions();

		});
	},

	renderViewCanNotAccess: function() {
		var that = this,
			tpl = that.templates.tpl_can_not_access,
			poster = $('#socialposter_' + that.instanceId),
			data = {},
			html
		;

		html = Mustache.render(tpl, data);
		poster.html(html);
	},

	renderViewMemberNotInGroup: function() {
		var that = this,
			tpl = that.templates.tpl_member_not_in_group,
			poster = $('#socialposter_' + that.instanceId),
			data = {},
			html
		;

		html = Mustache.render(tpl, data);
		poster.html(html);
	},

	renderViewNoContent: function() {
		var that = this,
			tpl = that.templates.tpl_no_content,
			poster = $('#socialposter_' + that.instanceId),
			data = {},
			html
		;

		//Solução provisória para remover espaço em branco do slot
		//vazio do wcm.
		$(this.DOM).parents('.slotfull').hide();

		html = Mustache.render(tpl, data);
		poster.html(html);
	},

	// Retorna um objeto 'social' de acordo com o alias
	findSocialVOByAlias: function(alias, callback) {
	    return this.baseAjax('/api/public/social/user/' + alias, {
	        contentType:'application/json'
	    }, function(err, socialData) {

	        if(err) {
	            that.renderViewCanNotAccess();
	            return false;
	        } else {
	            callback(socialData);
	        }
	    });
	},

	// Retorna o usuário logado
	getSocialUserLogged: function(callback) {
	    return this.baseAjax('/api/public/social/user/logged', {
				contentType:'application/json'
		}, function(err, loggedSocialData) {

			if(err) {
				socialGlobal.message({
					text: '${i18n.getTranslation("post.error.user.logged")}',
					type: 'error',
					timeout: 2000
				});
			} else {
				callback(loggedSocialData);
			}
		});
	},

	// Retorna objeto com informação se pastas (doc, foto, video...) estão habilitadas
	serviceSocialCommunity: function(alias, callback) {
		return this.baseAjax('/api/public/social/community/' + alias, {
	        contentType:'application/json'
	    }, function(err, communityData) {
	        if(err) {
	            socialGlobal.message({
	                text: '${i18n.getTranslation("post.error.document.enabled")}',
	                type: 'error',
	                timeout: 2000
	            });
	        } else {
	            callback(communityData);
	        }
	    });
	},

	serviceCreatePost: function(options, cb) {
		this.baseAjax('/api/public/social/post/create/with/upload', {
			type: 'POST',
			contentType:'application/json',
			data: options
		}, cb);
		
		//resetInputsCustomData();
	},

	//facilitador de uso do ajax
	baseAjax: function(url, options, cb) {
		var options = options || {};
		options.url = url;

		return jQuery.ajax(options)
		.done(function(data) {
			cb(null, data);
		})
		.fail(function(xhr, status, text) {
			cb(xhr);
		});
	},

	mentions: function() {
		var that = this;

		socialGlobal.mentionsInput('#post-share-text', true, function(ev) {
			that.resetCountText();
		});
	},

	resetMentions: function() {
		$('#post-share-text').mentionsInput('reset', null);
	},

	shareTextFocus: function(el, ev) {

		if ( !$('.post-share-form').hasClass("form-opened") ) {
			$('.post-share-form').addClass("form-opened").removeClass('form-closed');
			$('.post-share-form .post-share-text').css('height', 'auto');
		}

		var placeholder = $(el).attr("placeholder");

		if ( el.value === placeholder ) {
			el.value = "";
		}
	},

	closePostShare: function(el, ev) {
		$('.post-share-form').css('height', 'auto').removeClass("form-opened").addClass('form-closed');
		$('#post-share-text').val('');
		this.removeAttachedMedia( $('[data-remove-attached-media]')[0] );
		this.resetCountText();
		this.resetMentions();
	},

	shareTextBlur: function(el, ev) {
		var placeholder = $(el).attr("placeholder");

		if ( el.value === "" ) {
			el.value = placeholder;
		}
	},

	shareTextInput: function() {
		var self = this;

		this.resetCountText();
	},

	shareTextKeyUp: function(el, ev) {
		var self = this;

		if ( ev.keyCode === 8 ) {
			self.resetCountText();
		}
	},

	placeholderIE: function() {
		var textarea = $(".post-share-text")
		, placeholder = textarea.attr("placeholder");

		textarea.val(placeholder);
	},

	resetCountText: function() {
		var postText = $(".post-text-limit")
		, postShareText = $(".post-share-text")
		, postSubmit = $(".post-share-submit")
		, textLength = postShareText.val().length
		, total = 600 - textLength;

		postText.html(total);

		if (total < 0 || textLength < 2) {
			if (textLength < 2) {
				postShareText.css({ "outline": "none" });
				postText.css({ "color": "#656565" });
			}
			else {
				postShareText.css({ "outline": "#DAA8A9 solid 2px", "outline-offset":"-2px" });
				postText.css({ "color": "#f00" });
			}
			postSubmit.attr("disabled", "disabled");
			postSubmit.removeClass("totvs-btn-action").addClass("totvs-btn-disabled");
		} else {
			postText.css({ "color": "#656565" });
			postSubmit.removeAttr("disabled");
			postSubmit.removeClass("totvs-btn-disabled").addClass("totvs-btn-action");
			postShareText.css({ "outline": "none" });
		}
	},

	postFormSubmit: function(el, ev) {
		ev.preventDefault();

		var that = this,
			post = $(el).find(".post-share-text"),
			visibility = "PUBLIC",
			customUsers = $(el).find(".post-share-visibility-custom").val(),
			postVal, params
		;

		var socialVOByAlias = that.findSocialVOByAlias(that.alias, function(socialData) {
			var social = socialData;
			if (social.privateContent) {
				that.visibility = "PRIVATE";
			} else if (social.hidden) {
				that.visibility = "HIDDEN";
			}
		});

		$.when(socialVOByAlias).then(function() {

			$(el).find('.post-share-submit').attr("disabled", "disabled");

			//Pegando o valor o post com as mentions.
		    $('#post-share-text').mentionsInput('val', function(text) {
		    	postVal = text;
		    });

			if(postVal === post.attr("placeholder")){
				postVal = "";
			}

			if (!that.selectedSociable && (postVal === "" || postVal.length < 2)) {
				return socialGlobal.alert('${i18n.getTranslation("min.two.chars")}');
			}

			//Do not send null
			if (!customUsers) {
				customUsers = [];
			}

			postVal = FLUIGC.utilities.preventXSS(postVal);

			params = {
				text: postVal,
				visibility: visibility,
				alias: that.alias,
				customUsers: customUsers,
				sociableObjectId: that.selectedSociable,
				customData: that.generateCustomData()
			};

			that.serviceCreatePost(JSON.stringify(params), function(err, data) {
				if(err) {
					$(el).find('.post-share-submit').removeAttr("disabled");

					var text_message = '${i18n.getTranslation("post.error")}';

					if(err.responseText ){
						text_message = JSON.parse( err.responseText ).message.message ;
					}
					socialGlobal.message({
						type: 'error',
						text: text_message ,
						timeout: 2000
					});
				} else {

					if (data) data = data.value;
					$(el).find('.post-share-submit').removeAttr("disabled");
					WCMAPI.fireEvent('newPostEvent', data);
					that.resetPostContent(post);
					that.resetInputsCustomData();
				}
			});
			
			
		});

	},
	
	resetInputsCustomData: function() {
		$('[data-priority]', this.DOM).val("NORMAL");
		$('[data-poster-can]', this.DOM).prop('checked', false);
	},
	
	generateCustomData: function() {
		var val = this.typeConfigActions,
			config = {}
		;
		
		switch(val) {
			case 'OPTIONAL':
				config.canComment = $('[data-poster-can="comment"]', this.DOM).is(':checked');
				config.canDenounce = $('[data-poster-can="denounce"]', this.DOM).is(':checked');
				config.canLike = $('[data-poster-can="like"]', this.DOM).is(':checked');
				config.canShare = $('[data-poster-can="share"]', this.DOM).is(':checked');
				config.canWatch = $('[data-poster-can="watch"]', this.DOM).is(':checked');
			break;
			
			case 'NOTHING':
				config.canComment = false;
				config.canDenounce = false;
				config.canLike = false;
				config.canShare = false;
				config.canWatch = false;
			break;
			
			case 'ALL':
			default:
				config.canComment = true;
				config.canDenounce = true;
				config.canLike = true;
				config.canShare = true;
				config.canWatch = true;
			break;
		}

		//recuperando a prioridade do post
		config.priority = $('[data-priority]', this.DOM).val();
		return config;
	},

	resetPostContent: function(el) {
		el.val("");
		this.resetCountText();
		this.removeAttachedMedia( $('[data-remove-attached-media]')[0] );
		this.resetMentions();
		this.closePostShare();
		$("#photoSelectPreview").attr("src", "").hide();
	},

	postCreate: function() {
		var self = this;

		WCMAPI.addListener(self, 'selectImageEvent', self.selectImageDone);
		WCMAPI.addListener(self, 'selectVideoEvent', self.selectVideoDone);
		WCMAPI.addListener(self, 'selectDocumentEvent', self.selectDocumentDone);

	},

	selectImageOpen: function() {
		var self = this,
		container = $("<div id='mediaSelectDiv' class='loadingGallery'></div>"),
		params = '{"galleryMode": "SELECT", "galleryType": "PHOTO", "p1": "' + self.alias + '", "fromTimeline": "true"}';

		$("body").append(container)
			.find("#mediaSelectDiv")
			.dialog({
				//remove o div criado quando a modal é fechada. senão fica duplicando a div
				close: function() {
					$(this).remove();
				},
				width: ($(window).width() - 100),
				height: ($(window).height() - 100)
			});

		WCMAPI.convertFtlAsync('socialgallery', 'view.ftl', params, function(data) {
			$("#mediaSelectDiv").removeClass("loadingGallery").html(data);
			socialGlobal.loadSociables();
		});
	},

	selectImageDone: function(event, data) {
		var self = this
		, imgContainer = $("<figure></figure>")
		, img = $("<img>")
		, removeMedia = $("<span data-remove-attached-media>x</span>");

		self.selectedSociable = data.sociableId;

		imgContainer.attr("class", "media-attach-container");

		img.attr("src", self.thumbURL(data.documentId, data.version));
		img.attr("class", "media-attach");

		removeMedia.attr("class", "remove-attached-media");
		removeMedia.attr("data-sociableid", self.selectedSociable);

		imgContainer.append(img, removeMedia);

		$(".post-share-text-container")
		.prepend(imgContainer)
		.closest(".post-share-form")
		.addClass("attached-media");

		$(".post-options-container").addClass("photo-attached");
	},

	thumbURL: function(documentId, version) {
		var THUMB_ENUM = "80_AUTO";
		return "/webdesk/streamcontrol/?WDCompanyId=" + WCMAPI.getTenantId() + "&WDNrDocto=" + documentId + "&WDNrVersao=" + version + "&thumbnail=" + THUMB_ENUM;
	},

	selectVideoOpen: function() {
		var self = this
		, container = $("<div id='mediaSelectDiv' class='loadingGallery'></div>")
		, params = '{"galleryMode": "SELECT", "galleryType": "VIDEO", "p1": "' + self.alias + '", "fromTimeline": "true"}';

		$("body").append(container)
			.find("#mediaSelectDiv")
			.dialog({
				//remove o div criado quando a modal é fechada. senão fica duplicando a div
				close: function() {
					$(this).remove();
				},
				width: ($(window).width() - 100),
				height: ($(window).height() - 100)
			});

		WCMAPI.convertFtlAsync('socialgallery', 'view.ftl', params, function(data) {
			$("#mediaSelectDiv").removeClass("loadingGallery").append(data);
			socialGlobal.loadSociables();
		});

	},

	selectVideoDone: function(event, data) {
		var self = this
		, videoContainer = $("<figure></figure>")
		, video = $("<div></div>")
		, removeMedia = $("<span data-remove-attached-media>x</span>");

		self.selectedSociable = data.sociableId;

		videoContainer.attr("class", "media-attach-container");

		video.css({
			"background-image": "url(" + self.thumbURL(data.documentId, data.version) + ")",
			"background-color": "#000"
		});

		video.attr("class", "media-attach");

		removeMedia.attr("class", "remove-attached-media");
		removeMedia.attr("data-sociableid", self.selectedSociable);

		videoContainer.append(video, removeMedia);

		$(".post-share-text-container")
		.prepend(videoContainer)
		.closest(".post-share-form")
		.addClass("attached-media");

		$(".post-options-container").addClass("video-attached");
	},

	selectDocumentOpen: function() {
		var self = this,
			modalHeight = 600,
			modalBorder = 130,
			windowHeight = $(window).height(),
			documentPanel,
			documentSharing;

		if(windowHeight < modalHeight + modalBorder) {
			modalHeight = (windowHeight - 130) * .9;
		}

		documentPanel = new WCMC.panel({
        	url: "/ecm_documentsharing/documentSharing.ftl",
        	width: 750,
        	height: modalHeight,
        	maximized: false,
        	title: "${i18n.getTranslation('select.document')}",
        	customButtons: new Array("${i18n.getTranslation('insert')}"),
        });

		documentPanel.bind("panel-load", function(){
			documentSharing = DocumentSharing.instance(undefined, "documentSharing", {"alias":self.alias});

			documentPanel.bind("panel-button-0", function(){
				documentSharing.publisher(self.alias);
	        });

			documentSharing.panel = documentPanel;
		});

	},

	selectDocumentDone: function(event, object){
		/*
		Dados objeto Document do ECM

		object.documentId
		object.version
		object.publisherName
		object.companyId
		object.documentType // 1=Pasta, 2=Documento(word, jpg, excel, png, txt, etc)
		object.iconPath
		object.additionalComments
		object.createDate
		object.documentDescription
		object.lastModifiedDate
		object.parentDocumentId
		object.privateDocument
		object.publisherId
		object.size
		*/

		var sociableId = SocialAPI.SociableService.FINDSOCIABLE({async:false}, "com.totvs.technology.social.document." + object.documentId, "" + object.version).content;

		var self = this
		, imgContainer = $("<figure></figure>")
		, img = $("<img>")
		, removeMedia = $("<span data-remove-attached-media>x</span>");

		self.selectedSociable = sociableId;

		imgContainer.attr("class", "media-attach-container");

		img.attr("src", object.iconPath);
		img.attr("class", "media-attach");

		removeMedia.attr("class", "remove-attached-media");
		removeMedia.attr("data-sociableid", sociableId);

		imgContainer.append(img, removeMedia);

		$(".post-share-text-container")
		.prepend(imgContainer)
		.closest(".post-share-form")
		.addClass("attached-media");

		$(".post-options-container").addClass("document-attached");
	},

	removeAttachedMedia: function(el, ev) {
		var self = this;
		self.selectedSociable = null;

		$(el)
			.closest(".post-share-form").removeClass("attached-media")
		.end()
		.closest(".media-attach-container").remove();

		$(".post-options-container").removeClass("photo-attached video-attached document-attached");
	},

	attachMedia: function(el, ev) {
		this.attachType(el);
		this.shareTextFocus( $('#post-share-text')[0] );
	},

	attachType: function(el) {
		var self = this
		, attached = $(el).data("attach-media");

		if (!self.selectedSociable) {
			switch(attached) {
				case "photo":
					self.selectImageOpen();
				break;

				case "video":
					self.selectVideoOpen();
				break;

				case("document"):
					self.selectDocumentOpen();
				break;
			}
		} else {
			socialGlobal.alert('${i18n.getTranslation("file.already.attached")}');
		}
	}

});
