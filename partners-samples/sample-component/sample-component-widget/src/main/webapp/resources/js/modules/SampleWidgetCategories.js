/**
 * SampleWidgetCategories — UI module for categories CRUD
 * Rendering, datatable, inline editing.
 */
'use strict';

var SampleWidgetCategories = SuperWidget.extend({

	categoriesTable: null,
	services: null,
	mainWidget: null,

	MAX_CATEGORY_NAME_LENGTH: 50,

	/** Initializes categories module (no-op, uses configure()). */
	init: function() {},

	/**
	 * Injects dependencies needed by this module.
	 * @param {Object} servicesInstance - SampleWidgetServices instance for HTTP calls
	 * @param {Object} widgetInstance - Main widget instance (DOM, templates access)
	 */
	configure: function(servicesInstance, widgetInstance) {
		this.services = servicesInstance;
		this.mainWidget = widgetInstance;
	},

	/**
	 * Renders the category creation form into the designated area.
	 */
	renderCreateForm: function() {
		var ctx = this.mainWidget;
		var createTemplate = ctx.templates['template-create-category'];
		var formHtml = Mustache.render(createTemplate, {});
		ctx.DOM.find('#entity-create-area_' + ctx.instanceId).html(formHtml);
	},

	/**
	 * Fetches categories from API and renders them as a datatable.
	 * Shows skeleton loader while fetching.
	 * @returns {Promise<void>}
	 * @throws {Error} Shows danger toast if fetch fails
	 */
	loadCategoryList: async function() {
		var ctx = this.mainWidget;
		var categoriesContentSelector = '#entity-dynamic-content_' + ctx.instanceId;

		ctx.showTableSkeleton(categoriesContentSelector);

		try {
			var categoryList = await this.services.fetchCategories();
			this.buildCategoryTable(categoryList);
		} catch (error) {
			console.error('@<SampleComponent_TOTVS> Failed to list categories:', error);
			ctx.DOM.find(categoriesContentSelector).empty();
			FLUIGC.toast({ message: error.message || '${i18n.getTranslation("msg.error")}', type: 'danger' });
		}
	},

	/**
	 * Validates input, creates category via API, and refreshes list on success.
	 * @returns {Promise<void>}
	 * @throws {Error} Shows danger toast if creation fails
	 */
	createCategory: async function() {
		var ctx = this.mainWidget;
		var cleanCategoryName = this._getCategoryNameInput(ctx);

		if (!this._validateCategoryName(cleanCategoryName)) { return; }

		try {
			await this.services.createCategory(cleanCategoryName);
			FLUIGC.toast({ message: '${i18n.getTranslation("category.created")}', type: 'success' });
			ctx.DOM.find('[data-input-category]').val('');
			this.loadCategoryList();
		} catch (error) {
			console.error('@<SampleComponent_TOTVS> Failed to create category:', error);
			FLUIGC.toast({ message: error.message || '${i18n.getTranslation("msg.error")}', type: 'danger' });
		}
	},

	/**
	 * Reads and sanitizes category name from input field.
	 * @param {Object} ctx - Main widget instance (DOM access)
	 * @returns {string} Sanitized category name
	 * @private
	 */
	_getCategoryNameInput: function(ctx) {
		var categoryInputValue = ctx.DOM.find('[data-input-category]').val();
		return DOMPurify.sanitize(categoryInputValue);
	},

	/**
	 * Validates category name (non-empty, max length).
	 * @param {string} name - Sanitized category name to validate
	 * @returns {boolean} True if valid, false otherwise (shows toast on failure)
	 * @private
	 */
	_validateCategoryName: function(name) {
		if (!name) {
			FLUIGC.toast({ message: '${i18n.getTranslation("msg.category.name.required")}', type: 'danger' });
			return false;
		}

		var isNameTooLong = (name.length > this.MAX_CATEGORY_NAME_LENGTH);
		if (isNameTooLong) {
			FLUIGC.toast({ message: '${i18n.getTranslation("msg.category.name.max.length")}', type: 'danger' });
			return false;
		}

		return true;
	},

	/**
	 * Replaces read-only row with editable input for inline category rename.
	 * @param {HTMLElement} el - Clicked element with data-category-id and data-category-name
	 */
	activateInlineEdit: function(el) {
		var ctx = this.mainWidget;
		var categoryId = $(el).data('category-id');
		var categoryName = $(el).data('category-name');

		var editTemplate = ctx.templates['template-item-category-edit'];
		var editRowHtml = Mustache.render(editTemplate, { id: categoryId, name: categoryName });
		ctx.DOM.find('[data-category-row="' + categoryId + '"]').replaceWith(editRowHtml);
		ctx.DOM.find('[data-inline-edit-input][data-category-id="' + categoryId + '"]').focus();
	},

	/**
	 * Saves inline-edited category name via API and refreshes list.
	 * @param {HTMLElement} el - Save button element with data-category-id
	 * @returns {Promise<void>}
	 * @throws {Error} Shows danger toast if update fails
	 */
	saveInlineEdit: async function(el) {
		var ctx = this.mainWidget;
		var categoryId = $(el).data('category-id');
		var editInputValue = ctx.DOM.find('[data-inline-edit-input][data-category-id="' + categoryId + '"]').val();
		var cleanNewName = DOMPurify.sanitize(editInputValue);

		if (!this._validateCategoryName(cleanNewName)) { return; }

		try {
			await this.services.updateCategory(categoryId, cleanNewName);
			FLUIGC.toast({ message: '${i18n.getTranslation("category.updated")}', type: 'success' });
			this.loadCategoryList();
		} catch (error) {
			console.error('@<SampleComponent_TOTVS> Failed to update category ID ' + categoryId + ':', error);
			FLUIGC.toast({ message: error.message || '${i18n.getTranslation("msg.error")}', type: 'danger' });
		}
	},

	/**
	 * Cancels inline edit and restores read-only row display.
	 * @param {HTMLElement} el - Cancel button element with data-category-id and data-category-name
	 */
	cancelInlineEdit: function(el) {
		var ctx = this.mainWidget;
		var categoryId = $(el).data('category-id');
		var categoryName = $(el).data('category-name');

		var readTemplate = ctx.templates['template-item-category'];
		var readRowHtml = Mustache.render(readTemplate, { id: categoryId, name: categoryName });
		ctx.DOM.find('[data-category-row="' + categoryId + '"]').replaceWith(readRowHtml);
	},

	/**
	 * Shows confirmation dialog before deleting a category.
	 * @param {HTMLElement} el - Delete button element with data-category-id
	 */
	confirmDelete: function(el) {
		var categoriesModule = this;
		var categoryId = $(el).data('category-id');

		FLUIGC.message.confirm({
			message: '${i18n.getTranslation("msg.confirm.delete.category")}',
			title: '${i18n.getTranslation("label.delete")}',
			labelYes: '${i18n.getTranslation("label.yes")}',
			labelNo: '${i18n.getTranslation("label.no")}'
		}, function(isConfirmed) {
			if (!isConfirmed) { return; }
			categoriesModule.executeDelete(categoryId);
		});
	},

	/**
	 * Executes category deletion via API and refreshes list.
	 * @param {number|string} categoryId - ID of category to delete
	 * @returns {Promise<void>}
	 * @throws {Error} Shows danger toast if deletion fails
	 */
	executeDelete: async function(categoryId) {
		try {
			await this.services.deleteCategory(categoryId);
			FLUIGC.toast({ message: '${i18n.getTranslation("category.deleted")}', type: 'success' });
			this.loadCategoryList();
		} catch (error) {
			console.error('@<SampleComponent_TOTVS> Failed to delete category ID ' + categoryId + ':', error);
			FLUIGC.toast({ message: error.message || '${i18n.getTranslation("msg.error")}', type: 'danger' });
		}
	},

	/**
	 * Builds FLUIGC datatable for categories with ID, name, and edit columns.
	 * @param {Array<Object>} categoryList - Array of category objects to display
	 */
	buildCategoryTable: function(categoryList) {
		var ctx = this.mainWidget;
		var categoriesContentSelector = '#entity-dynamic-content_' + ctx.instanceId;

		ctx.DOM.find(categoriesContentSelector).empty();

		this.categoriesTable = FLUIGC.datatable(categoriesContentSelector, {
			emptyMessage: '<div class="text-center">${i18n.getTranslation("msg.no.data.found")}</div>',
			header: [
				{ 'title': '${i18n.getTranslation("label.id")}', 'size': 'col-md-1' },
				{ 'title': '${i18n.getTranslation("label.name")}', 'size': 'col-md-9' },
				{ 'title': '${i18n.getTranslation("label.edit")}', 'size': 'col-md-2' }
			],
			dataRequest: categoryList,
			renderContent: '.template-item-category',
			classSelected: 'active',
			actions: { enabled: false },
			search: { enabled: false },
			navButtons: { enabled: false }
		}, function(datatableError) {
			if (datatableError) {
				FLUIGC.toast({ message: '${i18n.getTranslation("msg.error")}', type: 'danger' });
			}
		});
	}

});
