<div id="Wioauth_${instanceId}" class="wcm-widget-class super-widget" data-params="Wioauth.instance()">
    <div class="fluig-style-guide">    
    	<h4>${i18n.getTranslation('help.text1')}</h4>
    	<hr>
		<p class="text-info">${i18n.getTranslation('help.text2')}</p>		
		<p class="text-info">${i18n.getTranslation('help.text3')}</p>
		<br>
		<div class="row">
			<div class="col-xs-4">
				<label for="exampleInputEmail1">${i18n.getTranslation('label.consumer.key')}</label>
				<input type="text" class="form-control" data-consumer-key disabled="disabled">
			</div>
			<div class="col-xs-4">				
				<label for="exampleInputEmail1">${i18n.getTranslation('label.consumer.secret')}</label>
				<input type="text" class="form-control" data-consumer-secret disabled="disabled">
			</div>
		</div>
		<br>
		<table class="table table-hover" id="oa-table">
			<thead>
				<tr>
					<th>#</th>
					<th>${i18n.getTranslation('label.info.item')}</th>
					<th>${i18n.getTranslation('label.info.installed')}</th>
				</tr>
			<thead>
			<tr>
				<td><span class="fluigicon fluigicon-key fluigicon-md"></span></td>
				<td>${i18n.getTranslation('label.info.oauth.provider')}</td>
				<td class="up-form-icon"><span class="fluigicon fluigicon-remove-sign fluigicon-md"></td>
			</tr>
			<tr>
				<td><span class="fluigicon fluigicon-user-config fluigicon-md"></span></td>
				<td>${i18n.getTranslation('label.info.oauth.app')}</td>
				<td class="up-form-icon"><span class="fluigicon fluigicon-remove-sign fluigicon-md"></td>
			</tr>
		</table>
		<div id="oa-btn-area"></div>
		<br>
		<div class="panel panel-default">
			<div class="panel-heading">${i18n.getTranslation('label.info.oauth.tokens')}</div>
			<div class="panel-body">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="access.token" class="col-sm-2 control-label">${i18n.getTranslation('label.access.token')}</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" data-access-token disabled="disabled">
						</div>
					</div>
					<div class="form-group">
						<label for="token.secret" class="col-sm-2 control-label">${i18n.getTranslation('label.token.secret')}</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" data-token-secret disabled="disabled">
						</div>
					</div>					
				</form>
			</div>
		</div>		
		<blockquote>${i18n.getTranslation('help.obs')}</blockquote>
	</div>
	
	<!-- templates html -->
	<script type="text/template" class="template-btn-install">
		<button class="btn btn-info btn-lg" data-title="Instalar" data-install>
			<span class="fluigicon fluigicon-download-circle"></span> ${i18n.getTranslation('label.btn.install')}
		</button>
	</script>

	<script type="text/template" class="template-btn-installed">
		<button class="btn btn-success btn-lg" disabled="disabled">
			<span class="fluigicon fluigicon-download-circle"></span> ${i18n.getTranslation('label.oa.installed')}
		</button>
	</script>

	<script type="text/template" class="template-oa-installed">
		<span class="fluigicon fluigicon-check-circle-on fluigicon-md">
	</script>
			
</div>