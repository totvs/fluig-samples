/**
 * SampleWidgetServices — Isolated HTTP services layer
 *
 * Flow: Widget -> Solution REST API -> Service/DAO -> Database (AppDS).
 * Never access platform tables directly.
 */
'use strict';

var SampleWidgetServices = SuperWidget.extend({

	SLOT_ID_LICENCA: 4012,

	/** Initializes service module (no-op, configuration done externally). */
	init: function() {},

	/**
	 * Normalizes API error responses into user-friendly messages.
	 * @param {string} responseText - Raw response text from server
	 * @param {string} fallbackMessage - Fallback message to show when no specific error is detected
	 * @returns {string} Friendly error message
	 */
	_normalizeApiErrorMessage: function(responseText, fallbackMessage) {
		var normalizedText = (responseText || '').trim();

		if (!normalizedText) {
			return fallbackMessage;
		}

		try {
			var parsedResponse = JSON.parse(normalizedText);
			if (parsedResponse && typeof parsedResponse === 'object' && parsedResponse.error) {
				normalizedText = String(parsedResponse.error).trim();
			}
		} catch (ignore) {}

		if (/Duplicate entry|duplicate key|sc_category\.scp_category_pk/i.test(normalizedText)) {
			return '${i18n.getTranslation("msg.error.category.duplicate")}';
		}

		return fallbackMessage;
	},

	/* ========================================================================
	 * EXTERNAL API (didactic example - jsonplaceholder)
	 * ======================================================================== */

	/**
	 * Fetches user list from external JSONPlaceholder API (didactic example).
	 * @returns {Promise<Array<Object>>} Array of user objects from external API
	 * @throws {Error} If HTTP response is not ok
	 */
	fetchExternalUsers: async function() {
		var fetchResponse = await fetch('https://jsonplaceholder.typicode.com/users', {
			method: 'GET',
			headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
		});
		if (!fetchResponse.ok) {
			throw new Error('@<SampleComponent_TOTVS> Failed to fetch external users — HTTP ' + fetchResponse.status);
		}
		return await fetchResponse.json();
	},

	/* ========================================================================
	 * SOLUTION REST API (CRUD own tables: SCO_CATEGORY)
	 * Base endpoint: /samplerest/api/v1
	 * ======================================================================== */

	/**
	 * Fetches all categories from the solution REST API.
	 * @returns {Promise<Array<Object>>} Array of category objects
	 * @throws {Error} If HTTP response is not ok
	 */
	fetchCategories: async function() {
		var fetchResponse = await fetch(WCMAPI.getServerURL() + '/samplerest/api/v1/category', {
			method: 'GET',
			headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
		});
		if (!fetchResponse.ok) {
			throw new Error('${i18n.getTranslation("msg.error.list.categories")}');
		}
		return await fetchResponse.json();
	},

	/**
	 * Creates a new category via solution REST API.
	 * @param {string} categoryName - Name for the new category
	 * @returns {Promise<string>} Server response text (created ID or confirmation)
	 * @throws {Error} If HTTP response is not ok (includes server error message)
	 */
	createCategory: async function(categoryName) {
		var createResponse = await fetch(WCMAPI.getServerURL() + '/samplerest/api/v1/category', {
			method: 'POST',
			headers: { 'Accept': 'text/plain', 'Content-Type': 'application/json' },
			body: JSON.stringify({ 'name': categoryName })
		});
		if (!createResponse.ok) {
			var serverErrorText = await createResponse.text();
			throw new Error(this._normalizeApiErrorMessage(serverErrorText, '${i18n.getTranslation("msg.error.create.category")}'));
		}
		return await createResponse.text();
	},

	/**
	 * Updates an existing category name via solution REST API.
	 * @param {number|string} categoryId - ID of category to update
	 * @param {string} newCategoryName - New name for the category
	 * @returns {Promise<boolean>} True if update succeeded
	 * @throws {Error} If HTTP response is not ok (includes server error message)
	 */
	updateCategory: async function(categoryId, newCategoryName) {
		var updateResponse = await fetch(WCMAPI.getServerURL() + '/samplerest/api/v1/category', {
			method: 'PUT',
			headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
			body: JSON.stringify({ 'id': categoryId, 'name': newCategoryName })
		});
		if (!updateResponse.ok) {
			var serverErrorText = await updateResponse.text();
			throw new Error(this._normalizeApiErrorMessage(serverErrorText, '${i18n.getTranslation("msg.error.update.category")}'));
		}
		return true;
	},

	/**
	 * Deletes a category by ID via solution REST API.
	 * @param {number|string} categoryId - ID of category to delete
	 * @returns {Promise<boolean>} True if deletion succeeded
	 * @throws {Error} If HTTP response is not ok (includes server error message)
	 */
	deleteCategory: async function(categoryId) {
		var deleteResponse = await fetch(WCMAPI.getServerURL() + '/samplerest/api/v1/category/' + categoryId, {
			method: 'DELETE',
			headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
		});
		if (!deleteResponse.ok) {
			var serverErrorText = await deleteResponse.text();
			throw new Error(this._normalizeApiErrorMessage(serverErrorText, '${i18n.getTranslation("msg.error.delete.category")}'));
		}
		return true;
	},

	/* ========================================================================
	 * TOTVS FLUIG PUBLIC APIs (Licensing)
	 * Docs: https://tdn.totvs.com/x/bS6QGg
	 * ======================================================================== */

	/**
	 * Checks slot license validity via Fluig Licensing API.
	 * @returns {Promise<Object>} License data object with `valid` boolean property
	 * @throws {Error} If HTTP response is not ok
	 */
	checkSlotLicense: async function() {
		var checkResponse = await fetch(WCMAPI.getServerURL() + '/license/api/v1/slots/' + this.SLOT_ID_LICENCA, {
			method: 'GET',
			headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' }
		});
		if (!checkResponse.ok) {
			throw new Error('@<SampleComponent_TOTVS> License slot check failed — HTTP ' + checkResponse.status);
		}
		return await checkResponse.json();
	}

});
