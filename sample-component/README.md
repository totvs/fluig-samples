# Exemplo de componente para a fluig Store
---

A partir da versão 1.6.2 do fluig, será possível instalar os componentes diretamente na Store, mais especificamente através da página "[Itens da Store](http://tdn.totvs.com/x/yUwXEQ)" dentro do próprio fluig.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o desenvolvedor deseje criar um componente e disponibilizá-lo na fluig Store.
O projeto está construído utilizando o gerenciador de projeto/dependências [Apache Maven](https://maven.apache.org/). Quanto ao desenvolvimento HTML, JS e freemarker, Mustache para a widget. Está incluído também a instrução para a criação de uma nova página, com um novo layout e já com essa nova widget adicionada.

O layout customizado é uma maneira de criar uma página sem o menu tradicional do fluig. 

O projeto ainda conta com uma implementação de uma API Rest em Java.
A estrutura está definida da seguinte maneira:

###sample-component
###### sample-component-pack
###### sample-component-config
###### sample-component-entity
###### sample-component-service
###### sample-component-rest
###### sample-component-widget
###### sample-component-widget-menu
###### sample-component-layout
###### sample-component-layout-custom
###### pom.xml  
---

* **sample-component-pack**: projeto responsável por empacotar todo o sample-component em um arquivo do tipo .ear.

	O package do projeto deve ser do tipo **.EAR** (Enterprise Application Archive)
	Para essa configuração, no arquivo sample-component/sample-component-pack/pom.xml a tag <packing> deve estar assim: **<packaging>ear</packaging>**
---


* **sample-component-config**: projeto onde estão os arquivos de configuração do componente:

	Para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**
	Esse arquivo se encontra em sample-component-config/src/main/resources/component.xml
	É obrigatório que o **component code** seja único: **<component code="sample_component_app">**
	
	Para criar uma ou mais páginas e já disponibilizar a(s) widget(s), é necessário o arquivo **pages.xml**
	Esse arquivo se encontra em sample-component-config/src/main/resources/pages.xml
	Lembrando que o código da página precisa ser único: ex.: **<code>sample-component-page</code>**
	
	O ícone da página criada, pode ser encontrado em sample-component-config/src/main/webapp/resources/images/sample-component.png

---
* **sample-component-entity**: projeto responsável em criar tabelas no banco de dados, dedicadas apenas a esse app

    A partir da release do fluig 1.6.5-190514 ou superior, está disponível a criação de tabelas próprias do app no banco de dados do fluig. Com isso, será possível ter uma estrutura própria, com serviços dedicados e exclusivos para seu app. Isso facilita o armazenamento de alguns dados, sem a necessidade de utilizar formulários ou até mesmo um serviço externo. 
	
	Para utilizar essa funcionalidade, algumas diretrizes são obrigatórias:
	
		- DataSource name: AppDS
		- Não criar relacionamento com as tabelas da plataforma (PK, FK, view, trigger, índices, etc)		
		- Não inserir/alterar/remover registros das tabelas do fluig. Para isso, utilize as API, WebServices e/ou SDK
		- Criar índices para as tabelas do app
		- No nome das tabelas, adicionar o prefixo com o nome da empresa/parceiro + “_”. Ex.: XYZ Company: XYZ_my_table
		- Não utilizar prefixo de tabelas do fluig. Ex.: fdn, wcm, social, etc...
		
    Algumas recomendações:
    
        - Utilizar limit e offset nas consultas que buscam registros
        - Ao criar uma consulta/select, especificar as colunas (evite utilizar select * from)
        - Criar um VO específico para retorno invés de retornar a própria entidade
        - Utilize padrões de projetos em seu desenvolvimento: DAO, Factory, Builder, Chain of Responsibility...
        
    Esse projeto é um modelo que você pode seguir para criar suas tabelas no fluig. Fique atento ao arquivo persistence.xml, onde é informado o data source que obrigatoriamente deve ser utilizado para os parceiros criarem suas tabelas: AppDS
    Também é possível encontrar as classes definidas como entidades, que serão suas tabelas no banco de dados, bem como a criação das consultas, constraints e índices.
    
--- 
* **sample-component-service**:  projeto responsável por criar os serviços de CRUD com as tabelas do app

    A classe Activate.java é reponsável por provisionar a criação de um OAuth Provider e um OAuth App, de acordo com o Token inserido na classe RestConstant.java.
	- É necessário ativar o componente para o provisionamento
	- Essa funcionalidade de provisionar só está disponível a partir da release 1.6.5*
		
	Acesse a documentação para maiores informações: [Como expor dados em ambientes públicos](https://bit.ly/2SEW4bz)
	
	Pacotes:
	
		- DAO: Classes com os métodos de acesso ao banco de dados. 
		- Service/Impl: Interfaces e implementações das regras de negócios e permissões do app
	
---    
* **sample-component-rest**: projeto responsável em disponibilizar as API Rest para os Services das tabelas do app

	A API Rest de exemplo(necessário estar autenticado), pode ser testada através dos endpoints:
	
		- FLUIG_URL/samplecomponent/api/v1/category
		- FLUIG_URL/samplecomponent/api/v1/app
	    
	A configuração de autenticação desses Rest's está no arquivo web.xml do projeto sample-component-rest
	
	Para testar o Rest SEM autenticação, acesse o endpoint:
	
		- FLUIG_URL/samplecomponent/api/v1/activate/userInfo/{tenantId}
	
	Lembrando que, pra efetuar essa requisição, é necessário ativar o componente para que seja provisionado a criação de um OAuth Provide e OAuth App
	
	
---	
* **sample-component-widget**: projeto de uma widget simples. Aqui encontramos os arquivos de configuração, properties, imagens e o código-fonte da widget.

	No arquivo application.info o código precisa ser único: **application.code=samplewidget**
	
---	
* **sample-component-widget-menu**: projeto de uma widget de menu, para substituir o menu lateral esquerdo nativo do fluig.

	No arquivo application.info o código precisa ser único: **application.code=samplewidgetmenu**
	
---	
* **sample-component-layout**: projeto de um layout simples. Aqui encontramos os arquivos de configuração, properties, imagens, css e o código-fonte do layout.

	No arquivo application.info o código precisa ser único: **application.code=samplelayout**
	
---	
* **sample-component-layout-custom**: projeto de um layout customizado, sem o menu lateral esquerdo.
	
	No arquivo application.info o código precisa ser único: **application.code=samplecustomlayout**
	
---	
-> Para empacotar o projeto e gerar o arquivo **EAR**, será necessário utilizar o Maven. Entre com o seguinte comando na raiz do projeto:

     - mvn clean install
     - Você pode também utilzar o Eclipse for Java EE Developers para executar o comando mvn install

   Ao gerar o pacote, o próximo passo é fazer o upload através da Central de Componentes. Após enviar o .EAR, será necessário ativar o componente. Procure pelo código, que está no component.xml, faça a ativação, atualize a página(F5) e acesse as páginas criadas. (Essa etapa é necessária apenas no desenvolvimento do app)