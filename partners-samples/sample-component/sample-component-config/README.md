# sample-component-config

Use este módulo para registrar a solução, publicar páginas e declarar dependências de instalação.
Você gera um artefato WAR com `component.xml`, `pages.xml` e arquivos de `WEB-INF`.

## Exemplos práticos e técnicas no módulo

- Registro declarativo de app via `component.xml` (código, versão, metadados e ativação).
- Modelagem de navegação via `pages.xml` com layout, widgets e permissões por perfil.
- Publicação de dependências técnicas (ex.: datasets) para instalação automatizada.
- Configuração de segurança web (`jboss-web.xml`) com domínio e perfis mapeados.

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-config` |
| `packaging` | `war` |
| Java | 11 |

## Context-root e segurança

Arquivo: `src/main/webapp/WEB-INF/jboss-web.xml`.

| Campo | Valor |
| :--- | :--- |
| `context-root` | `/samplecomponentconfig` |
| `security-domain` | `TOTVSTech` |
| Perfis mapeados | `user`, `totvstech`, `sysadmin`, `admin` |

## Registro da solução

Arquivo: `src/main/resources/component.xml`.

| Campo | Valor no código |
| :--- | :--- |
| `code` | `sample_component_app` |
| `title` | `Sample Component` |
| `version` | `1.2.0` |
| `category` | `Store` |
| `activationClass` | `activator/samplecomponent` |
| `developerCode` | `TOTVS` |
| `developerName` | `TOTVS S.A.` |
| `developerURL` | `https://www.totvs.com` |

## Dependências declaradas

Você publica o seguinte recurso em `component.xml`:

| Tipo | Código | Arquivo |
| :--- | :--- | :--- |
| dataset | `sco_ds_fornecedores` | `dependencyFiles/datasets/sco_ds_fornecedores.js` |

## Páginas e navegação

Arquivo: `src/main/resources/pages.xml`.

| Código da página | Título | Pai | *layout* | *widget*(s) | Perfil de visualização |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `sample-component-page` | Sample Component | — | `layoutsimple` | — | `user` |
| `sample_page1` | Sample Layout | `sample-component-page` | `samplelayout` | `samplewidget` | `user` |
| `sample_page_html_editor` | HTML Editor | `sample-component-page` | `layoutsimple` | `html_editor` | `user` |

O wizard de instalação está disponível como projeto separado (`wizard-install`).

## Estrutura do módulo

```text
sample-component-config/
├── pom.xml
└── src/main/
    ├── resources/
    │   ├── component.xml
    │   ├── pages.xml
    │   └── dependencyFiles/datasets/sco_ds_fornecedores.js
    └── webapp/WEB-INF/
        ├── beans.xml
        ├── jboss-web.xml
        └── web.xml
```

## Build local

```bash
mvn -pl sample-component-config -am clean install
```
