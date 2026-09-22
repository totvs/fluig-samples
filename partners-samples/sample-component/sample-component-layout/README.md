# sample-component-layout

Use este módulo para publicar o layout principal da solução.

## Exemplos práticos e técnicas no módulo

- Criação de **layout fluig com Freemarker** usando slot editável (`SlotA`).
- Uso de flags de experiência (`application.responsiveLayout` e `application.newBuilder`).
- Composição de página com **header/menu/rodapé** de forma condicional por tema.
- Organização de assets (`css/js`) para customização visual desacoplada do conteúdo.

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-layout` |
| `packaging` | `war` |
| Java | 11 |
| `closure-compiler-maven-plugin` | `skip=false` |

## Configuração do componente

Arquivo: `src/main/resources/application.info`.

| Chave | Valor |
| :--- | :--- |
| `application.type` | `layout` |
| `application.code` | `samplelayout` |
| `application.title` | `Sample Layout` |
| `application.renderer` | `freemarker` |
| `layout.defaultSlot` | `SlotA` |
| `layout.file` | `layout.ftl` |
| `application.responsiveLayout` | `true` |
| `application.newBuilder` | `true` |

## Template FTL

Arquivo: `src/main/resources/layout.ftl`.

O template:

- renderiza cabeçalho e menu do portal fora do modo de edição;
- expõe um slot editável `SlotA` em largura total;
- renderiza o rodapé quando o tema não é `responsive_theme`.

## Recursos carregados

| Tipo | Recurso |
| :--- | :--- |
| CSS | `/portal/resources/css/wcm_responsive_layout.css` |
| CSS | `/resources/css/samplelayout.css` |
| JS | `/resources/js/samplelayout.js` |

## Deploy

Arquivo: `src/main/webapp/WEB-INF/jboss-web.xml`.

| Configuração | Valor |
| :--- | :--- |
| `context-root` | `/samplelayout` |

## Build local

```bash
mvn -pl sample-component-layout -am clean install
```
