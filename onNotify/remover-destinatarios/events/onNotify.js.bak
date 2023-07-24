function onNotify(subject, receivers, template, params) {
	log.info("### entrou no evento Global onNotify ###");
	
	//lista de emails que não devem receber notificação
	var naoRecebe = new Array("funcionario@totvs.com.br", "gestao@totvs.com.br");
	
	log.info("### vai remover todos os seguintes usuarios da lista de remetentes de e-mail: " + naoRecebe);
	receivers.removeAll(java.util.Arrays.asList(naoRecebe)); //remove qualquer email na lista criada acima
}