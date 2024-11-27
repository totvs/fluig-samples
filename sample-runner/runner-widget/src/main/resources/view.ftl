<div id="runnerWidget_${instanceId}" class="fluig-style-guide wcm-widget-class super-widget" data-params="RunnerWidget.instance()">
    <div class="container" data-content-area>
    </div>

    <script type="text/template" class="template-users-content">
    	<div class="row" id="mainDiv">
            <div class="jumbotron">
                <h1>Runner component!</h1>
            </div>
        </div>
        <h2>${i18n.getTranslation('label.widget.hello')}</h2>
        <p>
            <a class="btn btn-primary btn-lg" href="#" data-do-something role="button">${i18n.getTranslation('label.button.click')}</a>
            <a class="btn btn-primary btn-lg" href="#" data-load-table role="button">${i18n.getTranslation('label.button.sample.rest.external')}</a>
            <a class="btn btn-primary btn-lg" href="#" data-load-create-group role="button">${i18n.getTranslation('label.button.sample.create.group')}</a>
            <a class="btn btn-primary btn-lg" href="#" data-list-groups role="button">${i18n.getTranslation('label.button.sample.list.groups')}</a>
            <a class="btn btn-primary btn-lg" href="/runnerwidget/resources/download/app.zip" role="button">Download</a>
        </p>
        
        <br>
        
        <div class="row">
            <div class="col-md-12">
                <div data-sample-table></div>
            </div>
        </div>
        
    </script>
    
    <script type="text/template" class="template-list-users">
        <tr>
            <td title="{{name}}">{{name}}</td>
            <td title="{{username}}">{{username}}</td>
            <td title="{{address.street}}">{{address.street}}</td>
            <td title="{{email}}">{{email}}</td>
            <td title="{{phone}}">{{phone}}</td>
            <td>
                <p data-placement="top" data-toggle="tooltip" title="Delete">
                    <button class="btn btn-danger btn-xs" data-title="Delete" data-remove-user data-user-id="{{id}}" data-toggle="modal" data-target="#delete">
                        <span class="fluigicon fluigicon-trash"></span>
                    </button>
                </p>
            </td>
        </tr>
    </script>
    
    <script type="text/template" class="template-item-group">
    	<tr>
            <td title="{{id}}">{{id}}</td>
            <td title="{{code}}">{{code}}</td>
            <td title="{{description}}">{{description}}</td>
    	</tr>
    </script>
    
    <script type="text/template" class="template-create-group">
		<div class="row">
		    <div class="col-lg-6">
		        <div class="input-group">
		            <input type="text" class="form-control" data-input-group-code placeholder="${i18n.getTranslation('ph.group.code')}" maxlength="50">
                    <input type="text" class="form-control" data-input-group-description placeholder="${i18n.getTranslation('ph.group.description')}" maxlength="50">
		            <span class="input-group-btn">
		            <button class="btn btn-default" data-create-group type="button">${i18n.getTranslation('btn-group.create')}</button>
		            </span>
		        </div>
		    </div>
		</div>		    	
    </script>
    
    <script type="text/template" class="template-license-ok">    	
    	<div class="alert alert-success" role="alert">${i18n.getTranslation('label.license.ok')}</div>    	
    </script>
    
    <script type="text/template" class="template-license-non-ok">
    	<div class="alert alert-warning" role="alert">${i18n.getTranslation('label.license.non.ok')}</div>
    </script>
    
</div>