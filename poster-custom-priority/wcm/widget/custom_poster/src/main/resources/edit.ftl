<#attempt>
<#assign params = '{instanceId: ${instanceId?c}, context: "${context!}", communityAlias: "${communityAlias!}"}'?html>
<div class="fluig-style-guide wcm-widget-class super-widget" data-params="CustomPosterEdit.instance(${params})" id="customposteredit_${instanceId}">
	<div class="panel panel-default">
    	<div class="panel-body">
			<form name="editPosterForm" data-form-edit>
				<fieldset>
					<legend>${i18n.getTranslation('context.publish')}</legend>
					<div class="form-group">
				        <label class="radio-inline">
						    <input type="radio" name="inlineRadioOptions" data-context value="contextFollowers">
						    	${i18n.getTranslation('context.followers')}
						</label>
						<label class="radio-inline">
						    <input type="radio" name="inlineRadioOptions" data-context value="contextCommunity">
						    	${i18n.getTranslation('context.community')}
					    </label>


						<div data-community-details>
							<label>${i18n.getTranslation('selected.community')}<small></small></label>
							<input type="text" class="form-control ui-autocomplete-input" placeholder="${i18n.getTranslation('enter.community')}" data-search-community>
						</div>

				    </div>
				</fieldset>
				<div class="pull-right">
					<button type="button" class="btn btn-primary" data-loading-text="Loading..." data-save-poster-context>${i18n.getTranslation('btn.save')}</button>
				</div>
			</form>
		</div>
	</div>
</div>
<#recover> <#include "/social_error.ftl"> </#attempt>
