/**
 * SampleWidget — Main orchestrator
 *
 * Auxiliary modules (loaded via application.info):
 * - modules/SampleWidgetServices.js    → HTTP layer
 * - modules/SampleWidgetCategories.js  → Categories CRUD (UI)
 * - modules/SampleWidgetUsers.js       → External users listing (UI)
 */
'use strict';

var SampleWidget = SuperWidget.extend({

	services: null,
	categoriesModule: null,
	usersModule: null,

	bindings: {
		local: {
			'do-something': ['click_showCurrentDate'],
			'load-table': ['click_loadUsersTable'],
			'remove-user': ['click_confirmUserDelete'],
			'list-categories': ['click_loadCategoryList'],
			'load-create-category': ['click_showCreateCategoryForm'],
			'create-category': ['click_createCategory'],
			'edit-category': ['click_editCategory'],
			'remove-category': ['click_deleteCategory'],
			'save-inline-category': ['click_saveInlineCategoryEdit'],
			'cancel-inline-category': ['click_cancelInlineCategoryEdit']
		}
	},

	/**
	 * Initializes widget: modules, rendering, and async data loading.
	 */
	init: function() {
		this._initModules();
		this._initRendering();
		this._initAsyncData();
	},

	/**
	 * Instantiates and configures all auxiliary modules (services, categories, users).
	 * @private
	 */
	_initModules: function() {
		var ctx = this;
		ctx.services = SampleWidgetServices.instance(ctx.instanceId, ctx.DOM[0].id, null);

		ctx.categoriesModule = SampleWidgetCategories.instance(ctx.instanceId, ctx.DOM[0].id, null);
		ctx.categoriesModule.configure(ctx.services, ctx);

		ctx.usersModule = SampleWidgetUsers.instance(ctx.instanceId, ctx.DOM[0].id, null);
		ctx.usersModule.configure(ctx.services, ctx);
	},

	/**
	 * Renders main layout and contextual greeting on page load.
	 * @private
	 */
	_initRendering: function() {
		this.renderMainLayout();
		this.renderContextualGreeting();
	},

	/**
	 * Triggers async operations (license check, category form/list) after DOM paint.
	 * @private
	 */
	_initAsyncData: function() {
		var ctx = this;
		setTimeout(function() {
			ctx.checkLicense();
			ctx.categoriesModule.renderCreateForm();
			ctx.categoriesModule.loadCategoryList();
		}, 0);
	},

	/* ========================================================================
	 * GENERIC UI (layout, greeting, skeleton)
	 * ======================================================================== */

	/**
	 * Renders main content area using Mustache template.
	 */
	renderMainLayout: function() {
		var ctx = this;
		var contentTemplate = ctx.templates['template-users-content'];
		var contentHtml = Mustache.render(contentTemplate, {});
		ctx.DOM.find('#main-content-area_' + ctx.instanceId).html(contentHtml);
	},

	/**
	 * Renders personalized greeting in the header subtitle area.
	 */
	renderContextualGreeting: function() {
		var subtitle = document.getElementById('sco-header-subtitle_' + this.instanceId);
		if (subtitle) {
			var userName = WCMAPI.getUser();
			var greetingTemplate = '${i18n.getTranslationP1("label.greeting", "__USER__")}';
			subtitle.textContent = greetingTemplate.replace('__USER__', userName);
		}
	},

	/**
	 * Displays a loading skeleton inside the target container while data loads.
	 * @param {string} targetSelector - jQuery selector for container to fill with skeleton
	 */
	showTableSkeleton: function(targetSelector) {
		var ctx = this;
		var skeletonTemplate = ctx.templates['template-skeleton-table'];
		var skeletonHtml = Mustache.render(skeletonTemplate, {});
		ctx.DOM.find(targetSelector).html(skeletonHtml);
	},

	/* ========================================================================
	 * LICENSE
	 * ======================================================================== */

	/**
	 * Validates license slot and renders status indicator.
	 * @returns {Promise<void>}
	 * @throws {Error} Shows danger toast if license check fails
	 */
	checkLicense: async function() {
		var ctx = this;
		try {
			var licenseData = await ctx.services.checkSlotLicense();
			ctx._renderLicenseStatus(licenseData);
		} catch (error) {
			console.error('@<SampleComponent_TOTVS> License check failed:', error);
			FLUIGC.toast({ message: '${i18n.getTranslation("msg.error")}', type: 'danger' });
		}
	},

	/**
	 * Renders license valid/invalid alert based on API response.
	 * @param {Object} licenseData - License response with `valid` boolean
	 * @private
	 */
	_renderLicenseStatus: function(licenseData) {
		var ctx = this;
		var isLicenseValid = licenseData.valid;
		var licenseTemplate = isLicenseValid
			? ctx.templates['template-license-ok']
			: ctx.templates['template-license-non-ok'];

		var licenseHtml = Mustache.render(licenseTemplate, {});
		ctx.DOM.find('#license-alert_' + ctx.instanceId).html(licenseHtml);
	},

	/* ========================================================================
	 * SIMPLE ACTION (current date)
	 * ======================================================================== */

	/**
	 * Displays current date in a success toast (simple action demo).
	 */
	showCurrentDate: function() {
		var today = new Date();
		var formattedDate = today.getDate() + '/' + (today.getMonth() + 1) + '/' + today.getFullYear();
		FLUIGC.toast({
			message: '${i18n.getTranslation("msg.today.is")}: ' + formattedDate,
			type: 'success'
		});
	},

	/* ========================================================================
	 * MODULE DELEGATION
	 * ======================================================================== */

	/** Delegates to usersModule.loadExternalUsers(). */
	loadUsersTable: function() { this.usersModule.loadExternalUsers(); },
	/** Delegates to usersModule.confirmDelete(). */
	confirmUserDelete: function() { this.usersModule.confirmDelete(); },

	/** Delegates to categoriesModule.loadCategoryList(). */
	loadCategoryList: function() { this.categoriesModule.loadCategoryList(); },
	/** Delegates to categoriesModule.renderCreateForm(). */
	showCreateCategoryForm: function() { this.categoriesModule.renderCreateForm(); },
	/** Delegates to categoriesModule.createCategory(). */
	createCategory: function() { this.categoriesModule.createCategory(); },
	/**
	 * Delegates to categoriesModule.activateInlineEdit().
	 * @param {HTMLElement} el - Clicked element with data-category-id attribute
	 */
	editCategory: function(el) { this.categoriesModule.activateInlineEdit(el); },
	/**
	 * Delegates to categoriesModule.confirmDelete().
	 * @param {HTMLElement} el - Clicked element with data-category-id attribute
	 */
	deleteCategory: function(el) { this.categoriesModule.confirmDelete(el); },
	/**
	 * Delegates to categoriesModule.saveInlineEdit().
	 * @param {HTMLElement} el - Clicked element with data-category-id attribute
	 */
	saveInlineCategoryEdit: function(el) { this.categoriesModule.saveInlineEdit(el); },
	/**
	 * Delegates to categoriesModule.cancelInlineEdit().
	 * @param {HTMLElement} el - Clicked element with data-category-id attribute
	 */
	cancelInlineCategoryEdit: function(el) { this.categoriesModule.cancelInlineEdit(el); }

});
