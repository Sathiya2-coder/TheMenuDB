# MenuDB Spring Boot example

A small Spring Boot app that forwards requests to TheMenuDB's `api.php`. Since
the API's query parameter documentation could not be verified, this example
does not assume specific operations or parameter names. Supply the parameters
supported by TheMenuDB in the request to the local endpoint.

It also provides a simple menu-item insert API backed by an in-memory H2
database. The H2 data is cleared when the app stops.

## Run

Requires Java 17 and Maven:

```bash
mvn spring-boot:run
```

## Call the proxy

Send any API parameters through `/api/menudb`. For example, replace
`documentedParameter` and `value` with a parameter and value from TheMenuDB's
API documentation:

```bash
curl "http://localhost:8080/api/menudb?documentedParameter=value"
```

The app forwards the query string to `https://www.themenudb.com/api.php` and
returns the upstream response body, content type, and HTTP status.

## Insert a menu item

Send a `POST` request to `/api/menu-items` with a name and positive price:

```bash
curl -X POST http://localhost:8080/api/menu-items \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"Masala Dosa\",\"category\":\"Breakfast\",\"price\":8.50}"
```

The response includes the inserted record and its generated ID.

To use another API URL, set the `menudb.api-url` property, for example:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--menudb.api-url=https://example.com/api.php
```

# TheMenuDB
