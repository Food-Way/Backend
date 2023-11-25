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
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

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

    public ResponseEntity<Product> postProduct(RequestProduct product, UUID idEstablishment) {
        ResponseEntity<Establishment> establishment = establishmentService.getEstablishment(idEstablishment);

        if(establishment.getStatusCode().value() == 404) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }

        Product createdProduct = new Product(product);
        establishment.getBody().addProduct(createdProduct);
        createdProduct.setIdEstablishment(idEstablishment);

        return ResponseEntity.status(201).body(productRepository.save(createdProduct));
    }

    public ResponseEntity<List<Product>> getProducts(@Nullable UUID idEstablishment) {
        List<Product> products;
        if (idEstablishment == null) {
            products = productRepository.findAll();
        } else {
            establishmentService.getEstablishment(idEstablishment);
            products = productRepository.findByIdEstablishment(idEstablishment);
        }
        return ResponseEntity.status(200).body(products);
    }

    public ResponseEntity<Product> getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        return ResponseEntity.status(200).body(product.get());
    }
}





