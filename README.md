# produtos-soap

Web Service SOAP em Spring Boot que recebe o código de um produto e devolve nome, descrição, marca e quantidade em estoque.

- WSDL: http://localhost:8080/ws/produtos.wsdl
- Operação: consultarProduto
- Códigos de teste: 1, 2 e 3
- Código inexistente: retorna um SOAP Fault (`Client`) com a mensagem "Produto não encontrado para o código: X"
- Segurança: WS-Security com UsernameToken. Usuário `admin` / senha `senha123` (configurável em `application.properties`). Sem credenciais válidas o serviço devolve um SOAP Fault de autenticação. O WSDL continua público.

Exemplo de requisição:

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:prod="http://cps.sp.gov.br/produtos"
                  xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
  <soapenv:Header>
    <wsse:Security>
      <wsse:UsernameToken>
        <wsse:Username>admin</wsse:Username>
        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">senha123</wsse:Password>
      </wsse:UsernameToken>
    </wsse:Security>
  </soapenv:Header>
  <soapenv:Body>
    <prod:consultarProdutoRequest><prod:codigo>1</prod:codigo></prod:consultarProdutoRequest>
  </soapenv:Body>
</soapenv:Envelope>
```

Tecnologias: Java 17, Spring Boot 4.1.1, Spring Web Services, Maven, XSD/JAXB2.
