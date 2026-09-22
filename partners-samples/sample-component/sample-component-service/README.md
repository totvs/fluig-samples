# sample-component-service

Use este módulo para implementar regras de negócio com EJB. Você publica um artefato `ejb`
consumido pelo módulo REST via JNDI.

## Exemplos práticos e técnicas no módulo

- Camada de negócio com **EJB stateless** e contratos remotos expostos por JNDI.
- Separação por camadas (`service` + `dao`) com persistência via `@PersistenceContext`.
- Regras multi-tenant e validações de entrada antes de operações sensíveis.
- Ativação técnica do componente com `ActivationListener` e provisionamento OAuth (`Keyring`).

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-service` |
| `packaging` | `ejb` |
| Java | 11 |
| Dependência interna | `sample-component-entity` |

## Interfaces remotas

| Interface | JNDI |
| :--- | :--- |
| `SampleCategoryService` | `service/sample-category` |

## Implementações de serviço

### `SampleCategoryServiceImpl`

- Cria, busca, atualiza e remove `SampleCategory`.
- Normaliza e valida nome (trim, max 50 chars, sem caracteres de controle).
- Define `tenantId` com `securityService.getCurrentTenantId()`.
- Lista categorias por texto com paginação.

## DAOs

| Classe | Responsabilidade |
| :--- | :--- |
| `SampleCategoryDAO` | `findCategories` por tenantId + texto com paginação |

O DAO usa `@PersistenceContext(unitName = "AppDS")`.

## Ativação OAuth

Arquivo: `src/main/java/com/samplecomponent/activate/oauth/Activate.java`.

| Campo | Valor no código |
| :--- | :--- |
| EJB `mappedName` | `activator/samplecomponent` |
| Interface | `ActivationListener` |
| `APP_KEY` | `1234-5678-9876-5432` |
| `getArtifactFileName()` | `sample-component-service.jar` |
| `enable()` | `Keyring.provision(APP_KEY)` |

## Segurança EJB

Arquivo: `src/main/resources/META-INF/jboss-ejb3.xml`.

Você aplica o domínio `TOTVSTech` para todos os EJBs do módulo.

## Estrutura do módulo

```text
sample-component-service/
├── pom.xml
└── src/main/
    ├── java/com/samplecomponent/
    │   ├── activate/oauth/Activate.java
    │   ├── dao/
    │   │   └── SampleCategoryDAO.java
    │   └── service/
    │       ├── SampleCategoryService.java
    │       └── impl/
    │           └── SampleCategoryServiceImpl.java
    └── resources/META-INF/
        ├── ejb-jar.xml
        └── jboss-ejb3.xml
```

## Build local

```bash
mvn -pl sample-component-service -am clean install
```
