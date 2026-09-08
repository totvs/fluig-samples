# sample-component-entity

Use este módulo para modelar dados persistidos da solução. Você publica um JAR com entidades JPA
e metadados de persistência usados pelos serviços EJB.

## Exemplos práticos e técnicas no módulo

- Modelagem **JPA** com mapeamento de tabelas, colunas e chaves (`@Entity`, `@Table`, `@Id`).
- Uso de **restrições únicas** e `NamedQuery` para consultas padronizadas.
- Geração de IDs via `@TableGenerator` (`SCO_ID_GEN`) para compatibilidade multi-banco.
- Configuração de persistência JTA + Hibernate com cache e datasource corporativo.

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-entity` |
| `packaging` | `jar` |
| Java | 11 |

## Unidade de persistência

Arquivo: `src/main/resources/META-INF/persistence.xml`.

| Propriedade | Valor |
| :--- | :--- |
| `persistence-unit` | `AppDS` |
| `transaction-type` | `JTA` |
| `provider` | `org.hibernate.ejb.HibernatePersistence` |
| `jta-data-source` | `jdbc/AppDS` |
| `hibernate.hbm2ddl.auto` | `update` |
| `hibernate.cache.use_second_level_cache` | `true` |
| `hibernate.cache.use_query_cache` | `true` |

> **Nota Oracle:** O Hibernate detecta o dialect automaticamente via driver. Nenhum dialect precisa ser declarado explicitamente.

## Entidades implementadas

### `SampleCategory`

Arquivo: `src/main/java/com/samplecomponent/entity/SampleCategory.java`.

| Aspecto | Valor no código |
| :--- | :--- |
| Tabela | `SCO_CATEGORY` |
| Chave primária | `CAT_ID` (`@TableGenerator` com `SCO_CATEGORY_SEQ` na tabela `SCO_ID_GEN`) |
| Campos obrigatórios | `CAT_NAME` (max 50), `CAT_TENANT_ID` |
| Restrição única | `CAT_TENANT_ID`, `CAT_NAME` |
| NamedQuery | `SampleCategory.findByName` |

> **Prefixo de colunas:** todas as colunas usam prefixo `CAT_` para evitar conflito com palavras reservadas em qualquer banco (Oracle, MySQL, SQL Server).

## Tabelas criadas automaticamente

| Tabela | Finalidade |
| :--- | :--- |
| `SCO_CATEGORY` | Cadastro de categorias |
| `SCO_ID_GEN` | Gerador de IDs (`TABLE` strategy) para compatibilidade Oracle 19c, MySQL 8 e SQL Server 2022 |

## Dependências do módulo

`pom.xml` define as dependências abaixo com escopo `provided`:

| Artefato | Escopo |
| :--- | :--- |
| `com.fluig:fluig-sdk-api` | provided |
| `com.fluig:fluig-sdk-common` | provided |
| `com.fluig:fluig-sdk-keyring` | provided |
| `javax:javaee-api` | provided |

## Estrutura do módulo

```text
sample-component-entity/
├── pom.xml
└── src/main/
    ├── java/com/samplecomponent/entity/
    │   └── SampleCategory.java
    └── resources/META-INF/persistence.xml
```

## Build local

```bash
mvn -pl sample-component-entity -am clean install
```
