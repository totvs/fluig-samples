var SampleWidget = SuperWidget.extend({
	
	init: function() {
		// se desejar, coloque aqui instruções iniciais de cada widget
	},

	bindings: {
		// binding dos botões relacionados com cada função 
		local: {
			'do-something': ['click_someFunc']
		}
	},

	/**
	 * Função para exibir a data atual
	 * @returns
	 */
	someFunc: function() {	
		var d = new Date();
	    var curr_date = d.getDate();
	    var curr_month = d.getMonth() + 1;
	    var curr_year = d.getFullYear();
		alert('${i18n.getTranslation("msg.today.is")}' +' :' + curr_date + '/' + curr_month + '/' + curr_year);
	}
})