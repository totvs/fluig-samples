# Exemplo de implementação do scheme mailto

Usando "mailto" via html passando o email do contato a ser enviado:
	
	<a href="mailto:exemplo@examplo.com">Enviar Email</a>
	
Tambem e possivel passar dados para o cabeçalho (ex: assunto, cc, etc.) e uma mensagem para o corpo do email.
	
	<a href="mailto:exemplo@examplo.com?subject=This%20is%20the%20subject&cc=outroexemplo@examplo.com&body=Esse%20e%20o%20corpo">Enviar Email</a>
	
Pode ser informado dois email
	
	<a href="mailto:exemplo@examplo.com, outroexemplo@examplo.com">Enviar Email</a>
	
O Endereço tambem pode ser omitido:
	
	<a href="mailto:?to=&subject=mailto%20with%20examples&body=http://en.wikipedia.org/wiki/Mailto">Compartilhe esse conhecimento...</a>
