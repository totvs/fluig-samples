<script type="text/javascript" src="/webdesk/vcXMLRPC.js"></script>

<div id="Widataset_${instanceId}" class="wcm-widget-class super-widget" data-params="Widataset.instance()">
    <div class="fluig-style-guide">    
    	<h4>${i18n.getTranslation('help.text1')}</h4>
    	<hr>
		<p class="text-info">${i18n.getTranslation('help.text2')}</p>
		<p class="text-info">${i18n.getTranslation('help.link')}: <a href="/portal/p/02/wcmdatasetpage">LINK</a></p>		
		<p class="text-info">${i18n.getTranslation('help.text3')}</p>
		<br>
		<div id="dt-table"></div>		
		<blockquote>${i18n.getTranslation('help.obs')}</blockquote>		
		<div id="dt-btn-area"></div>
	</div>
	
	<!-- templates html -->
	<script type="text/template" class="template-single-dataset">
	    <tr>
	        <td>{{name}}</td>
	        <td>{{fields}}</td>
			<td>
				{{#sync}}
					${i18n.getTranslation('label.yes')}
				{{/sync}}
				{{^sync}}
					${i18n.getTranslation('label.no')}
				{{/sync}}
			</td>
			<td class="dt-status">
				{{#installed}}
					<span class="fluigicon fluigicon-check-circle-on fluigicon-md">
				{{/installed}}
				{{^installed}}
					<span class="fluigicon fluigicon-remove-sign fluigicon-md">
				{{/installed}}
			</td>	        
	    </tr>
	</script>

	<script type="text/template" class="template-btn-install">
		<button class="btn btn-info btn-lg" data-title="Instalar" data-install>
			<span class="fluigicon fluigicon-download-circle"></span> ${i18n.getTranslation('label.btn.install')}
		</button>
	</script>

	<script type="text/template" class="template-btn-installed">
		<button class="btn btn-success btn-lg" disabled="disabled">
			<span class="fluigicon fluigicon-download-circle"></span> ${i18n.getTranslation('label.dt.installed')}
		</button>
	</script>

	<script type="text/template" class="template-dt-installed">
		<span class="fluigicon fluigicon-check-circle-on fluigicon-md">
	</script>
			
</div>