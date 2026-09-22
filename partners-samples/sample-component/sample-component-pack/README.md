# sample-component-pack

Use este módulo para gerar o EAR da solução.

## Exemplos práticos e técnicas no módulo

- Empacotamento corporativo **EAR** agregando WAR, EJB e JAR do projeto.
- Definição explícita de `context-root` por web module para roteamento previsível.
- Organização de bibliotecas compartilhadas no `defaultLibBundleDir` (`lib`).
- Declaração de perfis/roles no descritor de aplicação para governança de acesso.

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-pack` |
| `packaging` | `ear` |
| `finalName` | `sample-component` |
| Arquivo gerado | `sample-component.ear` |

## Configuração de empacotamento

Arquivo: `pom.xml`.

| Chave | Valor |
| :--- | :--- |
| `maven-compiler-plugin.source/target` | `11 / 11` |
| `applicationName` | `Sample_Component` |
| `initializeInOrder` | `true` |
| `fileNameMapping` | `no-version` |
| `defaultLibBundleDir` | `lib` |
| `manifestEntries.Dependencies` | `com.fluig.common` |

## Módulos no EAR

Conforme `maven-ear-plugin` e `target/application.xml`.

| Módulo | Tipo | Context-root |
| :--- | :--- | :--- |
| `sample-component-layout` | WAR | `/samplelayout` |
| `sample-component-widget` | WAR | `/samplewidget` |
| `sample-component-rest` | WAR | `/samplerest` |
| `sample-component-config` | WAR | `/samplecomponentconfig` |
| `sample-component-service` | EJB | — |
| `sample-component-entity` | JAR | — |

## Perfis declarados

O EAR declara os perfis abaixo no `application.xml`.

- `sysadmin`
- `admin`
- `user`

## Dependências `provided`

- `foundation-common`
- `foundation-cache-module`
- `javaee-api`
- `fluig-api-tools`

## Build local

```bash
mvn -pl sample-component-pack -am clean package
```
