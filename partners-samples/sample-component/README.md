# Sample Component

Projeto de exemplo para desenvolvimento de componente compatível com a Store do TOTVS Fluig.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o
desenvolvedor deseje criar um componente e disponibilizá-lo na Store do TOTVS Fluig. O projeto está construído utilizando o gerenciador de projeto/dependências Apache
Maven. O frontend utiliza HTML, JavaScript e Mustache (template engine para as widgets). Está
incluída também a instrução para a criação de uma nova página, com um novo layout e já com essa
nova widget adicionada.

## Índice

- [Pré-requisitos](#pré-requisitos)
- [Início Rápido](#início-rápido)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Descrição dos Módulos](#descrição-dos-módulos)
- [Detalhes do Build Maven](#detalhes-do-build-maven)
- [Páginas e Navegação](#páginas-e-navegação)
- [Documentação e Referências](#documentação-e-referências)

## Pré-requisitos

| Requisito | Versão mínima |
| :--- | :--- |
| TOTVS Fluig | 2.0.0 |
| Java | 11 |
| Maven | 3.6.3 |
| Repositório Nexus | `https://nexus.fluig.com/content/groups/public` |

Consulte a [documentação de credenciais do Nexus Fluig](https://tdn.totvs.com/pages/releaseview.action?pageId=870389961)
para obter usuário e senha e configurar o arquivo `~/.m2/settings.xml`.

## Início Rápido

1. Consulte a documentação oficial do Nexus e configure o seu `~/.m2/settings.xml`.
2. Clone o repositório e acesse a pasta `sample-component/`.
3. Execute `mvn clean install` na raiz para gerar `sample-component-pack/target/sample-component.ear`.
4. Faça o *deploy* do `.ear` no servidor Fluig.

## Estrutura do Projeto

```text
sample-component/
├── pom.xml                          # POM raiz — multi-módulo, groupId: com.fluig, versão 1.2.0
├── sample-component-config/         # Configuração: component.xml, pages.xml, dataset
├── sample-component-entity/         # Entidade JPA: SCO_CATEGORY
├── sample-component-service/        # EJB: SampleCategoryService, Activate (OAuth)
├── sample-component-rest/           # API REST JAX-RS no context-root /samplerest
├── sample-component-layout/         # Layout padrão: samplelayout (slot único)
├── sample-component-widget/         # Widget principal: samplewidget
└── sample-component-pack/           # EAR final: empacota todos os módulos
```

## Descrição dos Módulos

| Módulo | Descrição | README |
| :--- | :--- | :--- |
| `sample-component-config` | Configuração central da solução com `component.xml`, `pages.xml`, permissões e *dataset* `sco_ds_fornecedores`. | [README](sample-component-config/README.md) |
| `sample-component-entity` | Entidade JPA `SampleCategory` (`SCO_CATEGORY`) com `persistence unit` `AppDS`. | [README](sample-component-entity/README.md) |
| `sample-component-service` | Serviço EJB (`SampleCategoryServiceImpl`) e ativação OAuth com `Activate` e `Keyring`. | [README](sample-component-service/README.md) |
| `sample-component-rest` | API REST JAX-RS no `context-root` `/samplerest` com CRUD de `SampleCategory` e integração OAuth em `/activate`. | [README](sample-component-rest/README.md) |
| `sample-component-layout` | *Layout* padrão `samplelayout` com slot único `SlotA`, `header`, menu e rodapé do portal. | [README](sample-component-layout/README.md) |
| `sample-component-widget` | *Widget* principal `samplewidget` com abas de Entity, REST e Downloads. | [README](sample-component-widget/README.md) |
| `sample-component-pack` | Empacotamento final em `sample-component.ear` com agregação dos demais módulos. | [README](sample-component-pack/README.md) |

## Detalhes do Build Maven

### POM files

| Módulo | Packaging | Java source/target |
| :--- | :--- | :--- |
| `sample-component` (raiz) | `pom` | — |
| `sample-component-config` | `war` | 11 / 11 |
| `sample-component-entity` | `jar` | 11 / 11 |
| `sample-component-service` | `ejb` | 11 / 11 |
| `sample-component-rest` | `war` | 11 / 11 |
| `sample-component-layout` | `war` | 11 / 11 |
| `sample-component-widget` | `war` | 11 / 11 |
| `sample-component-pack` | `ear` | 11 / 11 |

### Dependências gerenciadas (raiz)

| Dependência | Versão | Escopo |
| :--- | :--- | :--- |
| `javax:javaee-api` | 8.0.1 | provided |
| `com.fluig:fluig-api-tools` | 1.1.3 | provided |
| `oauth.signpost:signpost-core` | 2.1.1 | compile |

### Plugins de build

| Plugin | Versão | Finalidade |
| :--- | :--- | :--- |
| `maven-war-plugin` | 3.3.1 | Empacotamento WAR |
| `yuicompressor-maven-plugin` | 1.5.1 | Minificação CSS |
| `closure-compiler-maven-plugin` | 2.21.0 | Minificação JS |
| `maven-compiler-plugin` | 3.2 | Compilação Java 11 |
| `maven-ear-plugin` | 2.10 | Empacotamento EAR |

### Tags Maven principais

| Tag | Valor |
| :--- | :--- |
| `<groupId>` | `com.fluig` |
| `<artifactId>` | `sample-component` |
| `<version>` | `1.2.0` |
| `<fluig.version>` | `2.0.0` |
| `<finalName>` (pack) | `sample-component` |

## Páginas e Navegação

| Código | Título | *Layout* | *Widget*(s) | Permissão mínima |
| :--- | :--- | :--- | :--- | :--- |
| `sample-component-page` | Sample Component | `layoutsimple` | — | user |
| `sample_page1` | Sample Layout | `samplelayout` | `samplewidget` (SlotA) | user |
| `sample_page_html_editor` | HTML Editor | `layoutsimple` | `html_editor` (SlotA) | user |

O wizard de instalação está disponível como projeto separado (`wizard-install`).

## Documentação e Referências

- [Fluig TDN — Layouts](https://tdn.totvs.com/display/public/fluig/Layouts)
- [Fluig TDN — application.info](https://tdn.totvs.com/display/public/fluig/Arquivo+application.info)
- [Documentação de credenciais do Nexus Fluig](https://tdn.totvs.com/pages/releaseview.action?pageId=870389961)
- [Maven Introduction to the POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html)
- [Plataforma | Parceiros](https://tdn.totvs.com/display/public/fluig/Plataforma+%7C+Parceiros)
