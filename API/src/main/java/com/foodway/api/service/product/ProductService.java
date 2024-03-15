package com.foodway.api.service.product;

import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.handler.exceptions.ProductNotFoundException;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Product;
import com.foodway.api.record.RequestProduct;
import com.foodway.api.record.UpdateProductData;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.ProductRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import com.foodway.api.utils.Pilha;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    EstablishmentService establishmentService;

    @Autowired
    EstablishmentRepository establishmentRepository;

    public ResponseEntity deleteProduct(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return ResponseEntity.status(200).build();
        }
        throw new ProductNotFoundException("Product not found");
    }

    public ResponseEntity<Product> putProduct(UUID id, UpdateProductData data) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        product.get().update(Optional.ofNullable(data));
        Product savedProduct = product.get();
        productRepository.save(savedProduct);
        return ResponseEntity.status(200).body(savedProduct);
    }

    public ResponseEntity<Product> postProduct(RequestProduct product) {
        Optional<Establishment> establishment = establishmentRepository.findById(product.idEstablishment());

        if(establishment.isEmpty()) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }

        Product createdProduct = new Product(product);
        establishment.get().addProduct(createdProduct);
        createdProduct.setEstablishment(establishment.get());

        return ResponseEntity.status(201).body(productRepository.save(createdProduct));
    }

    public ResponseEntity<List<Product>> getProducts(@Nullable UUID establishmentId, @Nullable String orderBy) {
        List<Product> menu;
        if ("price".equals(orderBy)) {
            menu = productRepository.findByEstablishment_IdUserOrderByPriceAsc(establishmentId);
        } else if ("name".equals(orderBy)) {
            menu = productRepository.findByEstablishment_IdUserOrderByNameAsc(establishmentId);
        }else if ("nameDesc".equals(orderBy)) {
            menu = productRepository.findByEstablishment_IdUserOrderByNameDesc(establishmentId);
        } else if ("maxPrice".equals(orderBy)) {
            menu = productRepository.findByEstablishment_IdUserOrderByPriceDesc(establishmentId);
        } else if ("minPrice".equals(orderBy)) {
            menu = productRepository.findByEstablishment_IdUserOrderByPriceAsc(establishmentId);
        } else {
            menu = productRepository.findByIdProductOrderByNameAsc(establishmentId);
        }

        if (menu.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(menu);
        }

        return ResponseEntity.ok(menu);
    }

    public ResponseEntity<Product> getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        return ResponseEntity.status(200).body(product.get());
    }

    public ResponseEntity<Product> deleteLastProduct(UUID idEstablishment) {
        if (!establishmentRepository.existsById(idEstablishment)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment does not found");
        }
        List<Product> products = productRepository.findLastProductAdded(idEstablishment);
        if(products.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "There is no products yet");
        }

        Pilha<Product> productPilha = new Pilha<>(1);

        productPilha.push(products.get(0));
        Product removed = productPilha.peek();
        productRepository.delete(productPilha.pop());

        return ResponseEntity.status(200).body(removed);
    }

    public ResponseEntity<List<Product>> getAllProduct(UUID idEstablishment) {

        List<Product> products = productRepository.findByEstablishment_IdUser(idEstablishment);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "There is no products yet");
        }
        return ResponseEntity.status(200).body(products);
    }
}





