package br.edu.ifpb.iftech.lolcadora.dto.response;

import br.edu.ifpb.iftech.lolcadora.model.Product;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String nome;
    private BigDecimal preco;

    public static ProductResponse from(Product product){
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setNome(product.getNome());
        productResponse.setPreco(product.getPreco());

        return productResponse;
    }

    public static Page<ProductResponse> from(Page<Product> products){
        Page<ProductResponse> responses = products.map(product -> {

            ProductResponse productResponse = new ProductResponse();

            productResponse.setId(product.getId());
            productResponse.setNome(product.getNome());
            productResponse.setPreco(product.getPreco());

            return productResponse;

        });
        return responses;
    }

}
