package br.edu.ifpb.iftech.lolcadora.service;

import br.edu.ifpb.iftech.lolcadora.dto.request.ProductRequest;
import br.edu.ifpb.iftech.lolcadora.exceptions.IsNegativedException;
import br.edu.ifpb.iftech.lolcadora.exceptions.ObjectNotFoundException;
import br.edu.ifpb.iftech.lolcadora.model.Product;
import br.edu.ifpb.iftech.lolcadora.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product cadastrarProduto(ProductRequest request){
        Product product = new Product();

        product.setNome(request.getNome());
        if(request.getPreco().compareTo(new BigDecimal("0")) <= 0){
            throw new IsNegativedException("Valor abaixo do permitido");
        }else{
            product.setPreco(request.getPreco());
        }
        return this.repository.save(product);
    }

    public Product atualizarProduto(Long id, ProductRequest request){
        Product product = this.repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Produto não encontrado"));

        product.setNome(request.getNome());
        if(request.getPreco().compareTo(new BigDecimal("0")) <= 0){
            throw new IsNegativedException("Valor abaixo do permitido");
        }else{
            product.setPreco(request.getPreco());
        }
        return this.repository.save(product);
    }

    public Page<Product> listarProdutos(Pageable pageable){
        return this.repository.findAll(pageable);
    }

    public Product buscarProduto(Long id){
        Product product = this.repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Produto não encontrado"));
        return product;
    }

    public void removerUsuario(Long id){
        Product product = this.repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Produto não encontrado"));
        this.repository.delete(product);
    }
}
