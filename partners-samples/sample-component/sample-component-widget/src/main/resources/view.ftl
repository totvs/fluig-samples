<div id="sampleWidget_${instanceId}" class="fluig-style-guide wcm-widget-class super-widget" data-params="SampleWidget.instance()">

    <#-- === HEADER BLOCK (navbar-inverse padrão Style Guide) === -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" id="sco-header-nav_${instanceId}">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                        data-target="#sco-header-nav-collapse_${instanceId}">
                    <span class="sr-only">${i18n.getTranslation("label.sr.toggle.nav")}</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <span class="navbar-brand">
                    <strong>${i18n.getTranslation("header.title")}</strong>
                    <small id="sco-header-subtitle_${instanceId}"></small>
                </span>
            </div>
            <div class="collapse navbar-collapse" id="sco-header-nav-collapse_${instanceId}">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="https://www.totvs.com/" target="_blank" rel="noopener noreferrer">
                            ${i18n.getTranslation("header.nav.label.manual")}
                        </a>
                    </li>
                    <li>
                        <a href="https://www.totvs.com/" target="_blank" rel="noopener noreferrer">
                            ${i18n.getTranslation("header.nav.label.politica.uso")}
                        </a>
                    </li>
                    <li>
                        <a href="https://www.totvs.com/" target="_blank" rel="noopener noreferrer">
                            ${i18n.getTranslation("header.nav.label.politica.privacidade")}
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <#-- === END HEADER BLOCK === -->

    <!-- Conteúdo principal injetado pelo JS via loadPage -->
    <div id="main-content-area_${instanceId}"></div>

    <!-- Template principal: nav-tabs -->
    <script type="text/template" class="template-users-content">

        <!-- Alerta de licença injetado aqui pelo JS -->
        <div id="license-alert_${instanceId}"></div>

        <!-- Navegação por abas: separa Entity, REST e Downloads -->
        <ul class="nav nav-tabs fs-mb-24" role="tablist">
            <li role="presentation" class="active">
                <a href="#tab-entity_${instanceId}" aria-controls="tab-entity_${instanceId}" role="tab" data-toggle="tab">
                    <span class="animaliaicon animaliaicon-database icon-sm"></span> ${i18n.getTranslation('label.tab.entity')}
                </a>
            </li>
            <li role="presentation">
                <a href="#tab-rest_${instanceId}" aria-controls="tab-rest_${instanceId}" role="tab" data-toggle="tab">
                    <span class="animaliaicon animaliaicon-globe icon-sm"></span> ${i18n.getTranslation('label.tab.rest')}
                </a>
            </li>
            <li role="presentation">
                <a href="#tab-downloads_${instanceId}" aria-controls="tab-downloads_${instanceId}" role="tab" data-toggle="tab">
                    <span class="animaliaicon animaliaicon-download icon-sm"></span> ${i18n.getTranslation('label.tab.downloads')}
                </a>
            </li>
        </ul>

        <div class="tab-content">
            <!-- Aba 1: Módulo Entity (CRUD de Categorias) -->
            <div role="tabpanel" class="tab-pane active" id="tab-entity_${instanceId}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <span class="animaliaicon animaliaicon-tag icon-sm"></span> ${i18n.getTranslation('label.button.sample.create.category')}
                        </h3>
                    </div>
                    <div class="panel-body">
                        <div id="entity-create-area_${instanceId}"></div>
                        <div class="fs-mt-16" id="entity-dynamic-content_${instanceId}"></div>
                    </div>
                </div>
            </div>

            <!-- Aba 2: Integração REST (API externa) -->
            <div role="tabpanel" class="tab-pane" id="tab-rest_${instanceId}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <span class="animaliaicon animaliaicon-globe icon-sm"></span> ${i18n.getTranslation('label.button.sample.rest.external')}
                        </h3>
                    </div>
                    <div class="panel-body">
                        <div class="fs-mb-16">
                            <button class="btn btn-primary" type="button" data-load-table>
                                <span class="animaliaicon animaliaicon-download icon-sm"></span> ${i18n.getTranslation('label.button.sample.rest.external')}
                            </button>
                            <button class="btn btn-default" type="button" data-do-something>
                                <span class="animaliaicon animaliaicon-calendar icon-sm"></span> ${i18n.getTranslation('label.button.click')}
                            </button>
                        </div>
                        <div id="external-dynamic-content_${instanceId}"></div>
                    </div>
                </div>
            </div>

            <!-- Aba 3: Downloads -->
            <div role="tabpanel" class="tab-pane" id="tab-downloads_${instanceId}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <span class="animaliaicon animaliaicon-package icon-sm"></span> ${i18n.getTranslation('label.tab.downloads')}
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p>${i18n.getTranslation('label.download.description')}</p>
                        <a class="btn btn-success" href="/samplewidget/resources/download/app.zip">
                            <span class="animaliaicon animaliaicon-download icon-sm"></span> ${i18n.getTranslation('label.button.download')}
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </script>

    <!-- Template: Loading (exibido enquanto aguarda API) -->
    <script type="text/template" class="template-skeleton-table">
        <table class="table">
            <tbody>
                <tr>
                    <td><div class="fs-skeleton-loader size-20"></div></td>
                    <td><div class="fs-skeleton-loader"></div></td>
                    <td><div class="fs-skeleton-loader size-40"></div></td>
                    <td><div class="fs-skeleton-loader size-20"></div></td>
                </tr>
                <tr>
                    <td><div class="fs-skeleton-loader size-20"></div></td>
                    <td><div class="fs-skeleton-loader"></div></td>
                    <td><div class="fs-skeleton-loader size-40"></div></td>
                    <td><div class="fs-skeleton-loader size-20"></div></td>
                </tr>
                <tr>
                    <td><div class="fs-skeleton-loader size-20"></div></td>
                    <td><div class="fs-skeleton-loader"></div></td>
                    <td><div class="fs-skeleton-loader size-40"></div></td>
                    <td><div class="fs-skeleton-loader size-20"></div></td>
                </tr>
            </tbody>
        </table>
    </script>

    <!-- Template: Linha de usuário no datatable REST -->
    <script type="text/template" class="template-list-users">
        <tr>
            <td title="{{name}}">{{name}}</td>
            <td title="{{username}}">{{username}}</td>
            <td title="{{address.street}}">{{address.street}}</td>
            <td title="{{email}}">{{email}}</td>
            <td title="{{phone}}">{{phone}}</td>
            <td>
                <button class="btn btn-danger btn-sm" type="button" data-remove-user data-user-id="{{id}}" title="${i18n.getTranslation('label.delete')}">
                    <span class="animaliaicon animaliaicon-trash icon-sm"></span>
                </button>
            </td>
        </tr>
    </script>

    <!-- Template: Linha de categoria (modo leitura) -->
    <script type="text/template" class="template-item-category">
        <tr data-category-row="{{id}}">
            <td title="{{id}}">{{id}}</td>
            <td title="{{name}}">{{name}}</td>
            <td>
                <div class="btn-group">
                    <button class="btn btn-default btn-sm" type="button" data-edit-category data-category-id="{{id}}" data-category-name="{{name}}" title="${i18n.getTranslation('label.edit')}">
                        <span class="animaliaicon animaliaicon-pencil icon-sm"></span>
                    </button>
                    <button class="btn btn-danger btn-sm" type="button" data-remove-category data-category-id="{{id}}" title="${i18n.getTranslation('label.delete')}">
                        <span class="animaliaicon animaliaicon-trash icon-sm"></span>
                    </button>
                </div>
            </td>
        </tr>
    </script>

    <!-- Template: Linha de categoria (modo edição inline) -->
    <script type="text/template" class="template-item-category-edit">
        <tr data-category-row="{{id}}">
            <td>{{id}}</td>
            <td>
                <input type="text" class="form-control input-sm" data-inline-edit-input data-category-id="{{id}}" value="{{name}}" maxlength="50">
            </td>
            <td>
                <div class="btn-group">
                    <button class="btn btn-success btn-sm" type="button" data-save-inline-category data-category-id="{{id}}" title="${i18n.getTranslation('label.save')}">
                        <span class="animaliaicon animaliaicon-floppy-disk icon-sm"></span>
                    </button>
                    <button class="btn btn-default btn-sm" type="button" data-cancel-inline-category data-category-id="{{id}}" data-category-name="{{name}}" title="${i18n.getTranslation('label.cancel')}">
                        <span class="animaliaicon animaliaicon-x icon-sm"></span>
                    </button>
                </div>
            </td>
        </tr>
    </script>

    <!-- Template: Formulário de criação de categoria -->
    <script type="text/template" class="template-create-category">
        <div class="row">
            <div class="col-md-6">
                <div class="input-group">
                    <input type="text" class="form-control" data-input-category placeholder="${i18n.getTranslation('ph.category.name')}" maxlength="50">
                    <span class="input-group-btn">
                        <button class="btn btn-primary" data-create-category type="button">
                            <span class="animaliaicon animaliaicon-plus icon-sm"></span> ${i18n.getTranslation('btn-category.create')}
                        </button>
                    </span>
                </div>
            </div>
        </div>
    </script>

    <!-- Template: Licença OK -->
    <script type="text/template" class="template-license-ok">
        <div class="alert alert-success fs-mb-16" role="alert">
            ${i18n.getTranslation('label.license.ok')}
        </div>
    </script>

    <!-- Template: Licença NON OK -->
    <script type="text/template" class="template-license-non-ok">
        <div class="alert alert-warning fs-mb-16" role="alert">
            ${i18n.getTranslation('label.license.non.ok')}
        </div>
    </script>

</div>
