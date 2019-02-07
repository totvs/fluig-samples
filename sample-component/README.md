# Exemplo de componente para a fluig Store
---

A partir da versão 1.6.2 do fluig, será possível instalar os componentes diretamente na Store, mais especificamente através da página "[Itens da Store](http://tdn.totvs.com/x/yUwXEQ)" dentro do próprio fluig.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o desenvolvedor deseje criar um componente e disponibilizá-lo na fluig Store.
O projeto está construído utilizando o gerenciador de projeto [Apache Maven](https://maven.apache.org/). Quanto ao desenvolvimento HTML, JS e freemarker, Mustache para a widget. Está incluído também a instrução para a criação de uma nova página, com um novo layout e já com essa nova widget adicionada.

O layout customizado é uma maneira de criar uma página sem o menu tradicional do fluig. 

O projeto ainda conta com uma implementação de uma API Rest em Java. 
A estrutura está definida da seguinte maneira:

###sample-component
###### sample-component-pack
###### sample-component-config
###### sample-component-api
###### sample-component-widget
###### sample-component-layout
###### sample-component-custom-layout
###### sample-component-menu-widget

###### pom.xml
  

* **sample-component-pack**: projeto responsável por empacotar todo o sample-component em um arquivo do tipo .ear.

	O package do projeto deve ser do tipo **.EAR** (Enterprise Application Archive)
	Para essa configuração, no arquivo sample-component/sample-component-pack/pom.xml a tag <packing> deve estar assim: **<packaging>ear</packaging>**


* **sample-component-config**: projeto onde estão os arquivos de configuração do componente:

	Para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**
	Esse arquivo se encontra em sample-component-config/src/main/resources/component.xml
	É obrigatório que o **component code** seja único: **<component code="Sample_Component">**
	
	Para criar a página e já disponibilizar a widget, é necessário o arquivo **pages.xml**
	Esse arquivo se encontra em sample-component-config/src/main/resources/pages.xml
	Lembrando que o código da página precisa ser único: ex.: **<code>sample-component-page</code>**
	
	O ícone da página criada, pode ser encontrado em sample-component-config/src/main/webapp/resources/images/sample-component.png
	
	A classe Activate.java é reponsável por provisionar a criação de um OAuth Provider e um OAuth App, de acordo com o Token inserido na classe RestConstant.java.
	*É necessário ativar o componente para o provisionamento*
	*Essa funcionalidade de provisionar só está disponível a partir da release 1.6.5*	
	Acesse a documentação para maiores informações: [Como expor dados em ambientes públicos](https://bit.ly/2SEW4bz)	 


* **sample-component-api**: projeto responsável em disponibilizar as API Rest desenvolvida em Java

	A API Rest de exemplo(necessário estar autenticado), pode ser testada pela URL FLUIG_URL/samplecomponent/v1/myrest
	
	Para testar o Rest SEM autenticação, FLUIG_URL/samplecomponent/v1/activate/userInfo/{tenantId}


* **sample-component-widget**: projeto de uma widget simples. Aqui encontramos os arquivos de configuração, properties, imagens e o código-fonte da widget.

	No arquivo application.info o código precisa ser único: **application.code=samplewidget**


* **sample-component-layout**: projeto de um layout simples. Aqui encontramos os arquivos de configuração, properties, imagens, css e o código-fonte do layout.

	No arquivo application.info o código precisa ser único: **application.code=samplelayout**


* **sample-component-custom-layout**: projeto de um layout customizado, sem o menu lateral esquerdo.
	
	No arquivo application.info o código precisa ser único: **application.code=samplecustomlayout**


* **sample-component-menu-widget**: projeto de uma widget de menu, para substituir o menu lateral esquerdo nativo do fluig.

	No arquivo application.info o código precisa ser único: **application.code=samplewidgetmenu**



-> Para empacotar o projeto e gerar o arquivo **EAR**, será necessário utilizar o Maven. Entre com o seguinte comando na raiz do projeto:

	- mvn clean install
	- Você pode também utilzar o Eclipse for Java EE Developers para executar o comando mvn


Ao gerar o pacote, o próximo passo é fazer o upload através da Central de Componentes. Após enviar o .EAR, será necessário ativar o componente. Procure pelo código, que está no component.xml, faça a ativação, atualize a página(F5) e acesse as páginas criadas.
	