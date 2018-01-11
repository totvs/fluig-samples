# Exemplo de implementação do scheme mailto
---

Assim como o telefone, com o scheme "mailto://" é possível abrir o aplicativo de *e-mail* que deseja e iniciar uma nova mensagem com os dados cadastrados nos campos de um formulário. Ao tocar no link de *e-mail* do usuário, será executado um aplicativo de *e-mail* instalado no dispositivo móvel para realização do envio de *e-mail*. Consulte nossa documentação completa sobre o [protocolo fluig://](http://tdn.totvs.com/x/ztVSDg).

Usando "mailto" via HTML passando o *e-mail* do contato a ser enviado:

	<a href="mailto:exemplo@examplo.com">Enviar Email</a>

Tambem é possivel passar dados para o cabeçalho (ex: assunto, cc, etc.) e uma mensagem para o corpo do *e-mail*.

	<a href="mailto:exemplo@examplo.com?subject=This%20is%20the%20subject&cc=outroexemplo@examplo.com&body=Esse%20e%20o%20corpo">Enviar Email</a>

Pode ser informado dois *e-mail*

	<a href="mailto:exemplo@examplo.com, outroexemplo@examplo.com">Enviar Email</a>

O Endereço também pode ser omitido:

	<a href="mailto:?to=&subject=mailto%20with%20examples&body=http://en.wikipedia.org/wiki/Mailto">Compartilhe esse conhecimento...</a>
