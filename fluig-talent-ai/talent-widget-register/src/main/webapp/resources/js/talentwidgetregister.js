var TalentRegisterWidget = SuperWidget.extend({

    instanceId: null,
    myTable: null,
    myModal: null,

    bindings: {
        local: {
            'register-job': ['click_registerJob'],
            'show-result': ['click_showResult'],
            'analyze': ['click_analyze'],
        },
    },

    init() {
        this.initDatatable();
    },

    initDatatable() {
        const mydata = [
            {name: 'Job 1', description: 'Descrição do Job 1', state: 'Active', isAnalysed: function() { return this.state === 'Analyzed'; } },
            {name: 'Job 2', description: 'Descrição do Job 2', state: 'Inactive', isAnalysed: function() { return this.state === 'Analyzed'; } },
            {name: 'Job 3', description: 'Descrição do Job 3', state: 'Active', isAnalysed: function() { return this.state === 'Analyzed'; } },
            {name: 'Job 4', description: 'Descrição do Job 4', state: 'Analyzed', isAnalysed: function() { return this.state === 'Analyzed'; } },
        ];
        this.myTable = FLUIGC.datatable('#table-jobs-list', {
            dataRequest: mydata,
            renderContent: '.template_datatable',
            header: [
                {'title': 'Nome', 'size': 'col-md-3'},
                {'title': 'Descrição', 'size': 'col-md-3'},
                {'title': 'Status', 'size': 'col-md-2'},
                {'title': 'Análise', 'size': 'col-md-2'},
                {'title': 'Ações', 'size': 'col-md-2'},
            ],
            actions: {
                enabled: true,
                template: '.template_datatable_actions',
                actionAreaStyle: 'col-md-6'
            }
        }, (err, data) => {
            // DO SOMETHING (error or success)
        });
    },

    registerJob(el, ev) {
        const content = Mustache.render(this.templates['template_modal_content']);
        this.myModal = FLUIGC.modal({
            content,
            title: 'Cadastrar vaga',
            id: 'fluig-modal-register-job',
            size: 'large',
            actions: [{
                'label': 'Cancelar',
                'autoClose': true
            }, {
                'label': 'Cadastrar',
                'bind': 'data-save-job-service',
            }]
        });
    },

    showResult(el, ev) {
        const content = Mustache.render(this.templates['template_modal_result_content'], {
            "individualAnalyses": [
                {
                    "candidateIndex": 1,
                    "analysis": "João tem 5 anos de experiência em Java e Spring Boot, o que o torna um forte candidato."
                },
                {
                    "candidateIndex": 2,
                    "analysis": "Maria tem experiência com Java, mas seu foco maior é frontend, o que pode ser uma desvantagem."
                },
                {
                    "candidateIndex": 3,
                    "analysis": "Carlos tem experiência relevante, mas não tem conhecimento de Spring Boot, o que pode dificultar a adaptação."
                }
            ],
            "finalRanking": "Ranking dos candidatos:<br>1. João - 90%<br>2. Carlos - 75%<br>3. Maria - 65%"
        });
        this.myModal = FLUIGC.modal({
            content,
            title: 'Resultado da vaga',
            id: 'fluig-modal-result-job',
            size: 'large',
            actions: [{
                'label': 'Fechar',
                'autoClose': true
            }]
        });
    },

    analyze(el, ev) {
        const loading  = FLUIGC.loading(window, {
            textMessage: 'Analisando candidatos...',
        });

        loading.show();

        setTimeout(() => {
            loading.hide();
            this.showResult();
        }, 8000);
    },
});

