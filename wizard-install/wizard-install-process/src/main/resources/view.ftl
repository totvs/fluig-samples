<div id="wiprocess_${instanceId}" class="wcm-widget-class super-widget" data-params="wiprocess.instance()">    
    <div class="fluig-style-guide">
    	<h4>${i18n.getTranslation('help.text1')}</h4>
    	<hr>
    	<p class="text-info">${i18n.getTranslation('help.text2')}</p>
    	<p class="text-info">${i18n.getTranslation('help.text3')}</p>    	
    	<table class="table table-hover" id="up-area-${instanceId}">
			<thead>
				<tr>
					<th>#</th>
					<th>${i18n.getTranslation('label.info.item')}</th>
					<th>${i18n.getTranslation('label.info.installed')}</th>
				</tr>
			<thead>
		</table>		
		<blockquote>${i18n.getTranslation('help.obs')}</blockquote>		
		<div id="up-btn-area-${instanceId}"></div>				
	</div>
	
	<!-- templates html -->
	<script type="text/template" class="template-container-install-process">
		<tr>
			<td><span class="fluigicon fluigicon-group fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.group')}</td>
			<td><span class="fluigicon fluigicon-remove-sign fluigicon-md" id="up-group-icon"></td>
		</tr>
		<tr>
			<td><span class="fluigicon fluigicon-folder-close fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.folder')}</td>
			<td><span class="fluigicon fluigicon-remove-sign fluigicon-md" id="up-folder-icon"></td>
		</tr>
		<tr>
			<td><span class="fluigicon fluigicon-form fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.form')}</td>
			<td><span class="fluigicon fluigicon-remove-sign fluigicon-md" id="up-form-icon"></td>
		</tr>
		<tr>
			<td><span class="fluigicon fluigicon-process fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.process')}</td>
			<td><span class="fluigicon fluigicon-remove-sign fluigicon-md" id="up-process-icon"></td>
		</tr>
	</script>

	<script type="text/template" class="template-container-process-installed">
		<tr>
			<td><span class="fluigicon fluigicon-group fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.group')}</td>
			<td><span class="fluigicon fluigicon-check-circle-on fluigicon-md"></td>
		</tr>
		<tr>
			<td><span class="fluigicon fluigicon-folder-close fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.folder')}</td>
			<td><span class="fluigicon fluigicon-check-circle-on fluigicon-md"></td>
		</tr>
		<tr>
			<td><span class="fluigicon fluigicon-form fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.form')}</td>
			<td><span class="fluigicon fluigicon-check-circle-on fluigicon-md"></td>
		</tr>
		<tr>
			<td><span class="fluigicon fluigicon-process fluigicon-md"></span></td>
			<td>${i18n.getTranslation('label.info.process')}</td>
			<td><span class="fluigicon fluigicon-check-circle-on fluigicon-md"></td>
		</tr>			
	</script>

	<script type="text/template" class="template-btn-install">
		<button type="button" class="btn btn-info btn-lg" data-install id='button_install_${instanceId}'>${i18n.getTranslation('label.install')}</button></td>
	</script>

	<script type="text/template" class="template-btn-installed">
		<button type="button" class="btn btn-success btn-lg" disabled="disabled">${i18n.getTranslation('label.info.installed')}</button>
	</script>
		
</div>