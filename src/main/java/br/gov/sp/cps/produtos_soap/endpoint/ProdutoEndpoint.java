package br.gov.sp.cps.produtos_soap.endpoint;

import java.util.Map;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoRequest;
import br.gov.sp.cps.produtos_soap.model.ConsultarProdutoResponse;

@Endpoint
public class ProdutoEndpoint {

    private static final String NAMESPACE = "http://cps.sp.gov.br/produtos";

    // Simulação de banco de dados, como na aula
    private record Produto(String nome, String descricao, String marca, int estoque) {}

    private static final Map<Integer, Produto> PRODUTOS = Map.of(
        1, new Produto("Notebook", "Notebook 15.6 polegadas, 16GB RAM", "Dell", 12),
        2, new Produto("Mouse sem fio", "Mouse óptico 1600 DPI", "Logitech", 40),
        3, new Produto("Teclado mecânico", "Teclado ABNT2 com switch azul", "Redragon", 25)
    );

    @PayloadRoot(namespace = NAMESPACE, localPart = "consultarProdutoRequest")
    @ResponsePayload
    public ConsultarProdutoResponse consultarProduto(@RequestPayload ConsultarProdutoRequest request) {

        ConsultarProdutoResponse response = new ConsultarProdutoResponse();
        Produto produto = PRODUTOS.get(request.getCodigo());

        if (produto != null) {
            response.setNome(produto.nome());
            response.setDescricao(produto.descricao());
            response.setMarca(produto.marca());
            response.setQuantidadeEstoque(produto.estoque());
        } else {
            response.setNome("Produto não encontrado");
            response.setDescricao("-");
            response.setMarca("-");
            response.setQuantidadeEstoque(0);
        }

        return response;
    }
}