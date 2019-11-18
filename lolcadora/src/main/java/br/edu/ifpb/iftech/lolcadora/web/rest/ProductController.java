package br.edu.ifpb.iftech.lolcadora.web.rest;

import br.edu.ifpb.iftech.lolcadora.dto.request.ProductRequest;
import br.edu.ifpb.iftech.lolcadora.dto.response.ProductResponse;
import br.edu.ifpb.iftech.lolcadora.model.Product;
import br.edu.ifpb.iftech.lolcadora.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> cadastrarProduto(@Valid @RequestBody ProductRequest request){
        Product product = this.service.cadastrarProduto(request);
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> listarProdutos(Pageable pageable){
        Page<Product> products = this.service.listarProdutos(pageable);
        return ResponseEntity.ok(ProductResponse.from(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> buscarProduto(@Valid @PathVariable(value = "id") Long id){
        Product product = this.service.buscarProduto(id);
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> atualizarProduto(@Valid @PathVariable(value = "id") Long id,
                                                            @Valid @RequestBody ProductRequest request){
        Product product = this.service.atualizarProduto(id, request);
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> removerProduto(@Valid @PathVariable(value = "id")Long id){
        this.service.removerUsuario(id);
        return ResponseEntity.ok().build();
    }

}
