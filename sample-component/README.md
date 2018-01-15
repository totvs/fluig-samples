# Exemplo de componente para a fluig Store #
---

A partir da versão 1.6.2 do fluig, será possível instalar os componentes diretamente na Store, mais especificamente através da página "[Itens da Store](http://tdn.totvs.com/x/yUwXEQ)" dentro do próprio fluig.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o desenvolvedor deseje criar um componente e disponibiliza-lo na fluig Store.
O projeto está construído utilizando o gerenciador de projeto [Apache Maven](https://maven.apache.org/). Quanto ao desenvolvimento HTML, JS e freemarker para a widget. Está incluído também a instrução para a criação de uma nova página já com essa nova widget adicionada.
A estrutura está definida da seguinte maneira:

    sample-component
        - sample-component-server            
        - sample-component-widget
        - sample-component-web
        - pom.xml


**sample-component-server**: projeto responsável por empacotar todo o sample-component em um arquivo do tipo .ear.

- O package do projeto deve ser do tipo **.EAR** (Enterprise Application Archive)
Para essa configuração, no arquivo sample-component/sample-component-server/pom.xml a tag <packing> deve estar assim: **<packaging>ear</packaging>**


**sample-component-web**: projeto onde estão os arquivos de configuração do componente:

- Para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**
Esse arquivo se encontra em sample-component-web/src/main/resources/component.xml
É obrigatório que o **component code** seja único: **<component code="Sample_Component">**

- Para criar a página e já disponibilizar a widget, é necessário o arquivo **pages.xml**
Esse arquivo se encontra em sample-component-web/src/main/resources/pages.xml
Lemrbando que o código da página precisa ser único: **<code>pageSample_Component</code>**


**sample-component-widget**: projeto de uma widget simples. Aqui encontramos os arquivos de configuração, properties, imagens e o código-fonte da widget.

- No arquivo application.info o código precisa ser único: **application.code=samplewidget**



Para empacotar o projeto e gerar o arquivo **EAR**, entre com o seguinte comando na raiz do projeto:

	- mvn clean install
