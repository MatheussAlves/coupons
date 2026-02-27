### Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- Docker

### Rodando com Docker

Build da imagem:
docker build -t coupons-app .
Executando o container:
docker run -p 8080:8080 coupons-app

Ou docker-compose:
docker-compose up --build

Endpoints disponiveis em:
http://localhost:8080/swagger-ui/index.html

