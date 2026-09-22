# sample-component-widget

Use este módulo para publicar o widget principal da solução.

## Exemplos práticos e técnicas no módulo

- Padrão **SuperWidget** com JavaScript modular (`modules/`) para separar responsabilidades.
- Renderização híbrida com **Freemarker + Mustache** para UI dinâmica no front-end.
- Consumo de APIs internas (módulo REST da solução) e externas para composição de dados.
- Tabelas e formulários com interações ricas (ex.: edição inline e feedback visual).

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-widget` |
| `packaging` | `war` |
| Java | 11 |
| `closure-compiler-maven-plugin` | `skip=false` |

## Configuração do widget

Arquivo: `src/main/resources/application.info`.

| Chave | Valor |
| :--- | :--- |
| `application.type` | `widget` |
| `application.code` | `samplewidget` |
| `application.title` | `Sample Widget` |
| `application.renderer` | `freemarker` |
| `view.file` | `view.ftl` |
| `edit.file` | `edit.ftl` |
| `simple.deploy` | `true` |

## Interface renderizada

Arquivo: `src/main/resources/view.ftl`.

O widget renderiza:

- cabeçalho unificado com links externos;
- área principal injetada por JavaScript;
- navegação por abas com `Entity`, `REST` e `Downloads`;
- templates Mustache para tabela de usuários, lista de categorias, edição inline e alertas de licença.

## Recursos carregados

| Tipo | Recurso |
| :--- | :--- |
| CSS | `/resources/css/samplewidget.css` |
| JS | `/resources/js/modules/SampleWidgetServices.js` |
| JS | `/resources/js/modules/SampleWidgetCategories.js` |
| JS | `/resources/js/modules/SampleWidgetUsers.js` |
| JS | `/resources/js/samplewidget.js` |

## Deploy

Arquivo: `src/main/webapp/WEB-INF/jboss-web.xml`.

| Configuração | Valor |
| :--- | :--- |
| `context-root` | `/samplewidget` |

## Build local

```bash
mvn -pl sample-component-widget -am clean install
```
