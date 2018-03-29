<div id="sampleWidget_${instanceId}" class="fluig-style-guide wcm-widget-class super-widget" data-params="SampleWidget.instance()">
    <div class="container" data-users-content>
    </div>

    <script type="text/template" class="template-users-content">
        <h1>${i18n.getTranslation('label.widget.hello')}</h1>
        <p>
            <a class="btn btn-primary btn-lg" href="#" data-do-something role="button">${i18n.getTranslation('label.button.click')}</a>
            <a class="btn btn-primary btn-lg" href="#" data-load-table role="button">${i18n.getTranslation('label.button.load.table')}</a>
            <a class="btn btn-primary btn-lg" href="/samplewidget/resources/download/app.zip" role="button">Download</a>
        </p>
        <div class="row">
            <div class="jumbotron">
                <h1>Sample component!</h1>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div data-users-table></div>
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
</div>