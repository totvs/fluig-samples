# Exemplo de componente para a fluig Store #
---

A partir da versão 1.6.2 do fluig, será possível instalar os componentes diretamente na Store, mais especificamente através da página "Itens da Store" dentro do próprio fluig.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o desenvolvedor deseje criar um componente e disponibiliza-lo na fluig Store.
O projeto está construído utilizando o gerenciador de projeto [Apache Maven](https://maven.apache.org/). Efetue o download e faça a instalação da ferramenta.
Será necessário configurá-lo, apontando como repositório padrão, o [Nexus do fluig](http://nexus.fluig.com). Para isso, siga as instrução no nesse [link](http://tdn.totvs.com/x/7YDbCw), na sessão "Consumindo um Serviço no Componente SDK".

Quanto ao desenvolvimento, html, js, [mustache](https://mustache.github.io/) e [freemarker](https://freemarker.apache.org/) para a widget. Esta incluído também a instrução para a criação de uma nova página já com essa nova widget adicionada.
A estrutura está definida da seguinte maneira:

    sample-component
        - sample-component-server            
        - sample-component-widget
        - sample-component-web
        - pom.xml


**sample-component-server**: projeto responsável por empacotar todo o sample-component em um arquivo do tipo .ear.

- o package do projeto deve ser do tipo **.EAR** (Enterprise Application aRchive)
Para essa configuração, no arquivo *sample-component/sample-component-server/pom.xml* a tag <packing> deve estar assim: **<packaging>ear</packaging>**



**sample-component-web**: projeto onde estão os arquivos de configuração do componente:

- para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**
Esse arquivo se encontra em *sample-component-web/src/main/resources/component.xml*
É obrigatório que o component code seja único: **<component code="Sample_Component">**

- para criar a página e já disponibilizar a widget, é necessário o arquivo **pages.xml**
Esse arquivo se encontra em *sample-component-web/src/main/resources/pages.xml*
Lemrbando que o código da página precisa ser único: **<code>pageSample_Component</code>**



**sample-component-widget**: projeto de uma widget simples. Aqui encontramos os arquivos de configuração, properties, imagens e o código-fonte da widget.

- no arquivo application.info o cógido precisa ser único: **application.code=samplewidget**



Para empacotar o projeto e gerar o arquivo **EAR**, entre com o seguinte comando na raíz do projeto:

	- mvn clean install
