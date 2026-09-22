# sample-component-rest

Use este módulo para publicar a API REST da solução. Você gera um WAR JAX-RS com recursos de CRUD
e recursos de integração OAuth.

## Exemplos práticos e técnicas no módulo

- API **JAX-RS** versionada (`/api/v1`) com recursos CRUD para entidades de domínio.
- Integração outbound com OAuth 1.0 assinando requisições HTTP para APIs públicas do Fluig.
- Controle de acesso por endpoint via `web.xml` e perfis de segurança.
- Tratamento de cenários de conflito/validação retornando respostas adequadas ao cliente.

## Artefato Maven

| Propriedade | Valor |
| :--- | :--- |
| `artifactId` | `sample-component-rest` |
| `packaging` | `war` |
| Java | 11 |
| Dependência interna | `sample-component-service` |

## Contexto da API

| Configuração | Valor |
| :--- | :--- |
| `context-root` | `/samplerest` |
| `@ApplicationPath` | `/api/v1` |
| Base URL | `/samplerest/api/v1` |

Arquivos:

- `src/main/webapp/WEB-INF/jboss-web.xml`
- `src/main/java/com/samplecomponent/ApplicationConfig.java`

## Endpoints implementados

### Recurso `category`

Classe: `src/main/java/com/samplecomponent/rest/SampleCategoryRest.java`.

| Método | Rota | Ação |
| :--- | :--- | :--- |
| GET | `/category?text=&limit=&offset=` | Lista categorias |
| GET | `/category/{id}` | Busca categoria por ID |
| POST | `/category` | Cria categoria |
| PUT | `/category` | Atualiza categoria |
| DELETE | `/category/{id}` | Remove categoria |

O código trata conflito de duplicidade da constraint `scp_category_pk`.

### Recurso `activate`

Classe: `src/main/java/com/samplecomponent/rest/SampleActivateRest.java`.

| Método | Rota | Ação |
| :--- | :--- | :--- |
| GET | `/activate/search/{tenantId}` | Chama `/api/public/search/advanced` |
| GET | `/activate/userInfo/{tenantId}` | Chama `/api/public/2.0/users/getCurrent` |

## Segurança web

Arquivo: `src/main/webapp/WEB-INF/web.xml`.

- Proteja `/api/v1/category/*` com perfil `user`.
- Não aplique `security-constraint` em `/api/v1/activate/*`.

Perfis declarados: `user`, `totvstech`, `sysadmin`.

## Integração OAuth no recurso `activate`

1. Recupere chaves com `Keyring.getKeys(tenantId, RestConstant.APP_KEY)`.
2. Monte `OAuthConsumer` com consumer e token.
3. Assine `HttpURLConnection` antes do envio.
4. Envie requisições GET ou POST para API pública do Fluig.

## Estrutura do módulo

```text
sample-component-rest/
├── pom.xml
└── src/main/
    ├── java/com/samplecomponent/
    │   ├── ApplicationConfig.java
    │   ├── rest/
    │   │   ├── SampleActivateRest.java
    │   │   └── SampleCategoryRest.java
    │   └── util/
    │       ├── ErrorStatus.java
    │       ├── RestConstant.java
    │       └── RestHelper.java
    └── webapp/WEB-INF/
        ├── beans.xml
        ├── jboss-web.xml
        └── web.xml
```

## Build local

```bash
mvn -pl sample-component-rest -am clean install
```
