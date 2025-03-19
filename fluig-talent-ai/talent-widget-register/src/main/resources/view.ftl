<div
    id="talentRegisterWidget_${instanceId}"
    class="wcm-widget-class super-widget fluig-style-guide"
    data-params="TalentRegisterWidget.instance()"
>
    <h1 class="fs-mt-0">Talent AI | Cadastro de vagas</h1>
    <p>Cadastre abaixo as vagas disponíveis em sua empresa.</p>
    <div id="table-jobs-list"></div>

    <script type="text/template" class="template_datatable">
        <tr>
            <td>{{name}}</td>
            <td>{{description}}</td>
            <td>{{state}}</td>
            <td>
                {{ #isAnalysed }}
                    <button class="btn btn-default btn-sm" data-show-result>Ver resultado</button>
                {{ /isAnalysed }}
                {{ ^isAnalysed }}
                    <button class="btn btn-primary btn-sm" data-analyze>Analisar candidato</button>
                {{ /isAnalysed }}
            </td>
            <td>
                <div class="dropdown">
                    <button class="btn btn-default btn-sm dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
                        Ações <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="#">Editar vaga</a>
                        </li>
                        <li role="presentation">
                            <a role="menuitem" tabindex="-1" href="#">Remover vaga</a>
                        </li>
                    </ul>
                </div>
            </td>
        </tr>
    </script>

    <script type="text/template" class="template_datatable_actions">
        <button class="btn btn-primary" data-register-job>Cadastrar vaga</button>
    </script>

    <script type="text/template" class="template_modal_content">
        <div class="container-fluid">
            <form>
                <div class="form-group">
                    <label for="titulo">Título da vaga:</label>
                    <input type="text" class="form-control" id="titulo" placeholder="Ex: Desenvolvedor Java" required>
                </div>
    
                <div class="form-group">
                    <label for="nivel">Nível de carreira:</label>
                    <select class="form-control" id="nivel" required>
                        <option value="">Selecione</option>
                        <option>Júnior</option>
                        <option>Pleno</option>
                        <option>Sênior</option>
                        <option>Especialista</option>
                        <option>Coordenador</option>
                    </select>
                </div>
    
                <div class="form-group">
                    <label for="competencias">Competências:</label>
                    <input type="text" class="form-control" id="competencias" placeholder="Ex: Java, Spring Boot, SQL" required>
                </div>
    
                <div class="form-group">
                    <label for="modelo">Modelo de contratação:</label>
                    <select class="form-control" id="modelo" required>
                        <option value="">Selecione</option>
                        <option>Home Office</option>
                        <option>Híbrido</option>
                        <option>Presencial</option>
                    </select>
                </div>
    
                <div class="form-group">
                    <label for="responsabilidades">Responsabilidades e atribuições:</label>
                    <textarea class="form-control" id="responsabilidades" rows="3" placeholder="Descreva as responsabilidades" required></textarea>
                </div>
    
                <div class="form-group">
                    <label for="requisitos">Requisitos e qualificações:</label>
                    <textarea class="form-control" id="requisitos" rows="3" placeholder="Ex: Formação em Ciência da Computação" required></textarea>
                </div>
    
                <div class="form-group">
                    <label for="desejaveis">Requisitos desejáveis:</label>
                    <textarea class="form-control" id="desejaveis" rows="3" placeholder="Ex: Certificação AWS"></textarea>
                </div>
    
                <div class="form-group">
                    <label for="idForm">Selecionar formulário:</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="idForm">
                        <span class="input-group-btn">
                            <button class="btn btn-primary" type="button">
                                <span class="animaliaicon animaliaicon-magnifying-glass-plus"></span>
                            </button>
                        </span>
                    </div>
                </div>
            </form>
        </div>
    </script>

    <script type="text/template" class="template_modal_result_content">
        <h3 class="fs-mt-0">Resultado da análise</h3>
        {{ #individualAnalyses }}
            <p>{{ candidateIndex }}: {{ analysis }}</p>
        {{ /individualAnalyses }}
        <h3>Resultado final</h3>
        <p class="fs-mb-0">{{{ finalRanking }}}</p>
    </script>
</div>

