/**
 * SampleWidgetUsers — UI module for external users listing
 * Fetches, displays and removes users from external API.
 */
'use strict';

var SampleWidgetUsers = SuperWidget.extend({

	usersTable: null,
	services: null,
	mainWidget: null,

	/** Initializes users module (no-op, uses configure()). */
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

	/* ========================================================================
	 * LOAD LIST
	 * ======================================================================== */

	/**
	 * Fetches external users from API and renders datatable.
	 * Shows skeleton loader while fetching.
	 * @returns {Promise<void>}
	 */
	loadExternalUsers: async function() {
		var ctx = this.mainWidget;
		var contentSelector = '#external-dynamic-content_' + ctx.instanceId;
		ctx.showTableSkeleton(contentSelector);

		try {
			var userList = await this.services.fetchExternalUsers();
			this.buildTable(userList);
		} catch (error) {
			this._handleLoadError(error);
		}
	},

	/**
	 * Handles fetch error: logs, clears container, shows danger toast.
	 * @param {Error} error - Error thrown during user fetch
	 * @private
	 */
	_handleLoadError: function(error) {
		var ctx = this.mainWidget;
		var contentSelector = '#external-dynamic-content_' + ctx.instanceId;
		console.error('@<SampleComponent_TOTVS> Failed to load external users:', error);
		ctx.DOM.find(contentSelector).empty();
		FLUIGC.toast({ message: '${i18n.getTranslation("msg.error")}', type: 'danger' });
	},

	/* ========================================================================
	 * DELETE
	 * ======================================================================== */

	/**
	 * Shows confirmation dialog before removing selected user row.
	 * No-op if no row selected.
	 */
	confirmDelete: function() {
		var module = this;
		var rowIndex = module._getSelectedRowIndex();

		if (rowIndex === null) { return; }

		FLUIGC.message.confirm({
			message: '${i18n.getTranslation("msg.remove.selected.user")}',
			title: '${i18n.getTranslation("label.remove.user")}',
			labelYes: '${i18n.getTranslation("label.yes")}',
			labelNo: '${i18n.getTranslation("label.no")}'
		}, function(isConfirmed) {
			if (!isConfirmed) { return; }
			module._executeDelete(rowIndex);
		});
	},

	/**
	 * Returns index of selected datatable row, or null if none selected.
	 * @returns {number|null} Row index or null
	 * @private
	 */
	_getSelectedRowIndex: function() {
		var rowIndex = this.usersTable.selectedRows()[0];
		return (rowIndex >= 0) ? rowIndex : null;
	},

	/**
	 * Removes row from datatable by index and reloads table.
	 * @param {number} index - Row index to remove
	 * @private
	 */
	_executeDelete: function(index) {
		this.usersTable.removeRow(index);
		this.usersTable.reload();
		FLUIGC.toast({ message: '${i18n.getTranslation("msg.alert.ok")}', type: 'success' });
	},

	/* ========================================================================
	 * DATATABLE
	 * ======================================================================== */

	/**
	 * Builds FLUIGC datatable for external users with name, login, address, email, phone, and actions columns.
	 * @param {Array<Object>} userList - Array of user objects to display
	 */
	buildTable: function(userList) {
		var ctx = this.mainWidget;
		var contentSelector = '#external-dynamic-content_' + ctx.instanceId;
		ctx.DOM.find(contentSelector).empty();

		this.usersTable = FLUIGC.datatable(contentSelector, {
			emptyMessage: '<div class="text-center">${i18n.getTranslation("msg.no.data.found")}</div>',
			header: [
				{ 'title': '${i18n.getTranslation("label.name")}' },
				{ 'title': '${i18n.getTranslation("label.login")}' },
				{ 'title': '${i18n.getTranslation("label.address")}' },
				{ 'title': '${i18n.getTranslation("label.email")}' },
				{ 'title': '${i18n.getTranslation("label.phone")}' },
				{ 'title': '${i18n.getTranslation("label.actions")}' }
			],
			dataRequest: userList,
			renderContent: '.template-list-users',
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
