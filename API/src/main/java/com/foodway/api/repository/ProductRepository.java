package com.foodway.api.repository;

import com.foodway.api.model.Product;
import com.foodway.api.utils.Pilha;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByEstablishment_IdUserOrderByNameAsc(UUID idUser);
    List<Product> findByIdProductOrderByNameAsc(UUID idProduct);
    List<Product> findByEstablishment_IdUserOrderByPriceAsc(UUID idUser);
    List<Product> findByEstablishment_IdUserOrderByPriceDesc(UUID idUser);
    Product findByIdProduct(UUID idProduct);
    List<Product> findByNameContaining(String query);

    List<Product> findByEstablishment_IdUser(UUID idUser);

    @Query("""
            SELECT p FROM com.foodway.api.model.Product p WHERE p.establishment.idUser = ?1
            ORDER BY p.createdAt DESC
            """)
    List<Product> findLastProductAdded(UUID idEstablishment);

    List<Product> findByEstablishment_IdUserOrderByNameDesc(UUID establishmentId);


//    @Transactional
//    @Modifying
//    @Query("""
//            DELETE FROM tb_establishment_menu WHERE id_product = ?1
//            """)
//    List<Product> deleteForeignKeyEstablishmentMenu(UUID idProduct);
}
