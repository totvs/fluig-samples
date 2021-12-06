function addCopy(element) {
	const loading = FLUIGC.loading(window, {
		textMessage: "Adicionando uma cópia...", 
		onBlock: function() {
			// Pega o índice da linha que está copiando
			const indice = $(element)[0].getAttribute("name").split("___")[1];
			
			// Pega os todos os campos com nome terminados em "___1" na tabela "tabelaExemplo"
			const campos = $("#tabelaExemplo :input[name$='___" + indice + "']");
			
			const novoIndice = wdkAddChild("tabelaExemplo");
			
			for (let campoOrigem of campos) {
				const nomeCampoOrigem = campoOrigem.name;
				const campoDestino = nomeCampoOrigem.split("___")[0]+"___"+novoIndice;
				
				copiarDadosCampo(campoOrigem, campoDestino)
				
			}
		    loading.hide();
	    }
	});
	
	loading.show();
};

function copiarDadosCampo(campoOrigem, campoDestino) {
	const tipo = campoOrigem.type;
	const nomeCampoOrigem = campoOrigem.name;
	
	if (tipo == "select-multiple") { //Zoom
		
		const valor = window[nomeCampoOrigem].getSelectedItems();
		
		if (valor.length) {
			window[campoDestino].setValue(valor);
		}
		
	} else if (tipo == "radio") {
		
		const opcoes = $("input[name='"+nomeCampoOrigem+"']");
		
		for (let i = 0; i < opcoes.length; i++) {
			if (opcoes[i].checked) {
				$("input[name='"+campoDestino+"']")[i].checked = true;
				break;
			}
		}
		
	} else if (tipo == "checkbox") {
		
		$("#"+campoDestino)[0].checked = $("#"+nomeCampoOrigem)[0].checked;
		
	} else if (tipo == "text" || tipo == "textarea" || tipo == "hidden") {
		
		const valor = $("#"+nomeCampoOrigem).val();
		$("#"+campoDestino).val(valor);
		
	} else if (tipo == "select-one") { //select
		const opcoes = $("#"+nomeCampoOrigem).children();
		for (let i = 0; i < opcoes.length; i++) {
			if (opcoes[i].selected) {
				$("#"+campoDestino).children()[i].selected = true;
				break;
			}
		}
	}
};

