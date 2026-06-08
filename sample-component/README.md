# Projeto de exemplo para desenvolvimento de apps compatíveis com a Store do TOTVS Fluig
---

A partir da atualização 1.6.2 do TOTVS Fluig Plataforma, é possível instalar componentes diretamente na Store, através da página "[Itens da Store](https://tdn.totvs.com/display/public/fluig/Plataforma+%7C+Store)" dentro da plataforma.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o desenvolvedor deseje criar um componente e disponibilizá-lo como aplicativo para venda na Store do TOTVS Fluig.
O projeto está construído utilizando o gerenciador de projeto/dependências [Apache Maven](https://maven.apache.org/). O frontend utiliza HTML, JavaScript e Mustache (template engine para as widgets). Está incluído também a instrução para a criação de uma nova página, com um novo layout e já com essa nova widget adicionada.

O layout personalizado é uma maneira de criar uma página sem o menu tradicional da plataforma. 

O projeto ainda conta com uma implementação de uma API Rest em Java.

**Atualização mínima da plataforma**: Este projeto é compatível com TOTVS Fluig Plataforma 2.0.0 ou superior (definido em `<fluig.version>` no pom pai).
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

	O package do projeto deve ser do tipo **.EAR** (Enterprise Application Archive).
	Para essa configuração, no arquivo sample-component/sample-component-pack/pom.xml a tag `<packaging>` deve estar assim: **<packaging>ear</packaging>**.
---


* **sample-component-config**: projeto onde estão os arquivos de configuração do componente:

	Para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**.
	Esse arquivo se encontra em sample-component-config/src/main/resources/component.xml.
	É obrigatório que o **component code** seja único: **component code="sample_component_app"**.
	
	Para criar uma ou mais páginas e já disponibilizar a(s) widget(s), é necessário o arquivo **pages.xml**.
	Esse arquivo se encontra em sample-component-config/src/main/resources/pages.xml.
	Lembrando que o código da página precisa ser único: ex.: **<code>sample-component-page</code>**.
	
	O ícone das páginas é definido no pages.xml utilizando a tag `<iconFont>` com ícones da família [Flat Icons](https://style.fluig.com/components.html#icons). Exemplo: `<iconFont>flaticon flaticon-widgets icon-xl</iconFont>`.
---

* **sample-component-entity**: projeto responsável em criar tabelas no banco de dados, dedicadas apenas a esse app

    A partir da atualização 1.6.5-190514 da plataforma ou superior, está disponível a criação de tabelas dedicadas do app no banco de dados. Com isso, será possível ter uma estrutura própria, com serviços dedicados e exclusivos para seu app. Isso facilita o armazenamento de alguns dados, sem a necessidade de utilizar formulários ou até mesmo um serviço externo. 
	
	Para utilizar essa funcionalidade, algumas diretrizes são obrigatórias:
	
		- DataSource name: AppDS
		- Não criar relacionamento com as tabelas da plataforma (PK, FK, view, trigger, índices, etc)		
		- Não inserir/alterar/remover registros das tabelas da plataforma. Para isso, utilize as API, WebServices e/ou SDK
		- Criar índices para as tabelas do app
		- No nome das tabelas, adicionar o prefixo com o nome da empresa/parceiro + “_”. Ex.: XYZ Company: XYZ_my_table
		- Não utilizar prefixo de tabelas da plataforma. Ex.: fdn, wcm, social, etc...
		
    Algumas recomendações:
    
        - Utilizar limit e offset nas consultas que buscam registros
        - Ao criar uma consulta/select, especificar as colunas (evite utilizar select * from)
        - Criar um VO específico para retorno invés de retornar a própria entidade
        - Utilize padrões de projetos em seu desenvolvimento: DAO, Factory, Builder, Chain of Responsibility...
        
    Esse projeto é um modelo que você pode seguir para criar suas tabelas na plataforma. Fique atento ao arquivo persistence.xml, onde é informado o data source que obrigatoriamente deve ser utilizado para os parceiros criarem suas tabelas: AppDS.
    
    Também é possível encontrar as classes definidas como entidades, que serão suas tabelas no banco de dados, bem como a criação das consultas, constraints e índices.    
--- 

* **sample-component-service**:  projeto responsável por criar os serviços de CRUD com as tabelas do app

    A classe Activate.java é reponsável por provisionar a criação de um OAuth Provider e um OAuth App, de acordo com o Token inserido na classe RestConstant.java.
	- É necessário ativar o componente para o provisionamento
	- Essa funcionalidade de provisionar só está disponível a partir da atualização 1.6.5*
		
	Acesse a documentação para maiores informações: [Como expor dados em ambientes públicos](https://tdn.totvs.com/pages/viewpage.action?pageId=445656685).
	
	Pacotes:
	
		- DAO: Classes com os métodos de acesso ao banco de dados
		- Service/Impl: Interfaces e implementações das regras de negócios e permissões do app

	Este módulo consome as entidades definidas em sample-component-entity.
---

* **sample-component-rest**: projeto responsável em disponibilizar as API Rest para os Services das tabelas do app

	Este módulo expõe via HTTP os serviços implementados em sample-component-service.

	A API Rest de exemplo(necessário estar autenticado), pode ser testada através dos endpoints:
	
		- FLUIG_URL/samplerest/api/v1/category
		- FLUIG_URL/samplerest/api/v1/app
	    
	A configuração de autenticação desses Rest's está no arquivo web.xml do projeto sample-component-rest.
	
	Para testar o Rest SEM autenticação, acesse o endpoint:
	
		- FLUIG_URL/samplerest/api/v1/activate/userInfo/{tenantId}
	
	Lembrando que, pra efetuar essa requisição, é necessário ativar o componente para que seja provisionado a criação de um OAuth Provider e OAuth App.
---	

* **sample-component-widget**: projeto de uma widget simples. Aqui encontramos os arquivos de configuração, properties, imagens e o código-fonte da widget.

	No arquivo application.info o código precisa ser único: **application.code=samplewidget**.
---	

* **sample-component-widget-menu**: projeto de uma widget de menu, para substituir o menu lateral esquerdo nativo da plataforma.

	No arquivo application.info o código precisa ser único: **application.code=samplewidgetmenu**.
---	

* **sample-component-layout**: projeto de um layout simples. Aqui encontramos os arquivos de configuração, properties, imagens, css e o código-fonte do layout.

	No arquivo application.info o código precisa ser único: **application.code=samplelayout**.
	
	**Atenção, esse layout é compatível somente com a atualização 1.8.1 ou superior (este projeto já exige 2.0.0). Para versões anteriores, acesse a tag [1.8.0_OU_Inferior](https://git.fluig.com/projects/SAMPLES/repos/projetos/browse/sample-component?at=refs%2Ftags%2F1.8.0_OU_Inferior).**
---	

* **sample-component-layout-custom**: projeto de um layout personalizado, sem o menu lateral esquerdo.
	
	No arquivo application.info o código precisa ser único: **application.code=samplecustomlayout**.

	**Atenção, esse layout é compatível somente com a atualização 1.8.1 ou superior (este projeto já exige 2.0.0). Para versões anteriores, acesse a tag [1.8.0_OU_Inferior](https://git.fluig.com/projects/SAMPLES/repos/projetos/browse/sample-component?at=refs%2Ftags%2F1.8.0_OU_Inferior).**
---	

Para empacotar o projeto e gerar o arquivo **EAR**, será necessário utilizar o Maven. Entre com o seguinte comando na raiz do projeto:

     - mvn clean install
     - Você pode também utilizar o Eclipse for Java EE Developers para executar o comando mvn install.

O artefato final será gerado em: `sample-component-pack/target/sample-component-pack.ear`

Ao gerar o pacote, o próximo passo é fazer o upload através da Central de Componentes (Painel de Controle → Central de Componentes). Após enviar o .EAR, será necessário ativar o componente. Procure pelo código, que está no component.xml, faça a ativação, atualize a página(F5) e acesse as páginas criadas. (Essa etapa é necessária apenas no desenvolvimento do app).


---

## Estrutura Maven e build

### Arquivos pom.xml

O projeto utiliza uma estrutura Maven multi-módulo com herança de configurações:

| Arquivo | Tipo | Função |
|---------|------|--------|
| `pom.xml` (raiz) | pom (pai) | Centraliza versões, plugins e configurações herdadas por todos os módulos |
| `sample-component-layout/pom.xml` | war | Empacota layout como .war |
| `sample-component-layout-custom/pom.xml` | war | Empacota layout personalizado como .war |
| `sample-component-widget/pom.xml` | war | Empacota widget como .war |
| `sample-component-widget-menu/pom.xml` | war | Empacota widget de menu como .war |
| `sample-component-config/pom.xml` | war | Empacota configuração (component.xml, pages.xml) como .war |
| `sample-component-entity/pom.xml` | jar | Empacota entidades JPA como .jar |
| `sample-component-service/pom.xml` | ejb | Empacota serviços EJB como .jar |
| `sample-component-rest/pom.xml` | war | Empacota endpoints REST como .war |
| `sample-component-pack/pom.xml` | ear | Agrupa todos os módulos no .ear final para deploy |

O pom pai define a tag `<modules>` que lista todos os sub-módulos. Ao executar `mvn clean install` na raiz, o Maven compila todos na ordem correta (Reactor Build Order).

### Dependências e scope provided

As dependências são centralizadas no pom pai via `<dependencyManagement>` (para javaee-api, fluig-api-tools, signpost-core e lombok). As demais dependências do SDK da plataforma utilizam a variável `${fluig.version}` definida no pom pai, declarada diretamente nos módulos que as necessitam.

| Dependência | Versão | Escopo | Propósito |
|-------------|--------|--------|-----------|
| `javax:javaee-api` | 8.0.1 | provided | API Java EE (Servlets, EJB, JPA, CDI). Já disponível no servidor da plataforma |
| `com.fluig:fluig-api-tools` | 1.1.3 | provided | Ferramentas e utilitários da API. Já disponível no servidor |
| `oauth.signpost:signpost-core` | 2.1.1 | compile | Assinatura OAuth para chamadas REST autenticadas |
| `org.projectlombok:lombok` | 1.18.44 | provided | Geração de getters/setters/construtores em tempo de compilação |
| `com.fluig:fluig-sdk-api` | ${fluig.version} | provided | SDK principal da plataforma (interfaces de serviços) |
| `com.fluig:fluig-sdk-common` | ${fluig.version} | provided | Classes utilitárias comuns do SDK |
| `com.fluig:fluig-sdk-keyring` | ${fluig.version} | provided | Gerenciamento de credenciais seguras |
| `com.fluig:foundation-common` | ${fluig.version} | provided | Módulo base da plataforma |
| `com.fluig:foundation-cache-module` | ${fluig.version} | provided | Módulo de cache da plataforma |

**Sobre `<scope>provided</scope>`**: Indica que a dependência é fornecida pelo servidor da plataforma em runtime. Ela é necessária para compilação, mas **não** deve ser empacotada no .ear, pois já existe no ambiente. Isso evita conflitos de classloader e reduz o tamanho do artefato.

### Plugins do build

| Plugin | Versão | Função |
|--------|--------|--------|
| `maven-war-plugin` | 3.3.1 | Empacota módulos web (.war). Configurado para não exigir web.xml (`failOnMissingWebXml=false`) |
| `maven-compiler-plugin` | 3.2 | Compila código Java. Configurado para Java 11 (source e target) |
| `closure-compiler-maven-plugin` | 2.21.0 | Minifica arquivos JavaScript na fase `generate-resources`. Usa nível SIMPLE_OPTIMIZATIONS. No pom pai `<skip>true</skip>` (não há JS na raiz); módulos com JS sobrescrevem com `<skip>false</skip>`. [Documentação](https://tdn.totvs.com/display/fluig/Closure+Compiler+Maven+Plugin) |
| `yuicompressor-maven-plugin` | 1.5.1 | Minifica arquivos CSS. Exclui .js (tratados pelo Closure Compiler) |
| `maven-ear-plugin` | 2.10 | Gera o .ear final no módulo pack. Define context-root de cada módulo web e roles de segurança |

**Sobre minificação**: O YUI Compressor é mantido apenas para CSS, pois o Closure Compiler não processa folhas de estilo. Arquivos JS são excluídos do YUI via `<exclude>**/*.js</exclude>` e processados exclusivamente pelo Closure Compiler.

### Internacionalização (i18n)

O projeto utiliza arquivos `.properties` para suporte a múltiplos idiomas. A propriedade `locale.file.base.name` no `application.info` define o prefixo dos arquivos de tradução.

Para mais informações: [Internacionalização (i18n) - TDN](https://tdn.totvs.com/display/public/fluig/Internacionaliza%C3%A7%C3%A3o+%28i18n%29)

### Principais tags Maven utilizadas

| Tag | Onde | Significado |
|-----|------|-------------|
| `<packaging>` | Todos os pom.xml | Define o tipo de artefato: pom, war, jar, ejb, ear |
| `<modules>` | pom pai | Lista os sub-módulos que compõem o projeto |
| `<parent>` | Módulos filhos | Referência ao pom pai para herdar configurações |
| `<dependencyManagement>` | pom pai | Centraliza versões de dependências sem adicioná-las diretamente |
| `<scope>provided</scope>` | Dependências | A biblioteca existe no servidor, não empacotar no artefato |
| `<scope>compile</scope>` | Dependências | A biblioteca deve ser empacotada junto (padrão se omitido) |
| `<repositories>` | pom pai | Define repositório Nexus para download de dependências |
| `<finalName>` | pom pai / pack | Nome do artefato gerado (sem versão no nome do arquivo) |
| `<properties>` | pom pai | Variáveis reutilizáveis (ex: `${fluig.version}`) |
| `<configuration>` | Plugins | Parâmetros específicos de cada plugin |

---

## Documentação e referências para parceiros

### Documentações gerais para parceiros TOTVS Fluig

- [Plataforma | Parceiros (TDN)](https://tdn.totvs.com/display/public/fluig/Plataforma+%7C+Parceiros)


