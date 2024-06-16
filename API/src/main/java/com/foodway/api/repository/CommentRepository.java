    package com.foodway.api.repository;
    import com.foodway.api.model.Comment;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;
    import java.util.UUID;
    import com.foodway.api.record.DTOs.*;

    public interface CommentRepository extends JpaRepository<Comment, UUID> {
        List<Comment> findByIdEstablishmentAndIdCustomer(UUID idEstablishment, UUID idCustomer);

        Optional<List<Comment>> findTop4ByIdCustomer(UUID idCustomer);

        long countByIdCustomer(UUID idCustomer);

        @Query("""
                SELECT c FROM com.foodway.api.model.Comment c
                WHERE idEstablishment = ?1
                """)
        List<Comment> findByidEstablishment(UUID idEstablishment, PageRequest pageable);

        @Query("""
                SELECT count(*) FROM com.foodway.api.model.Comment c
                WHERE idEstablishment = ?1 and c.sentiment = 0
                """)
        int countByReviewPositive(UUID idEstablishment);  
        @Query("""
                SELECT count(*) FROM com.foodway.api.model.Comment c
                WHERE idEstablishment = ?1 and c.sentiment = 1
                """)
        int countByReviewNegative (UUID idEstablishment); 

        @Query("""
                SELECT count(*) FROM com.foodway.api.model.Comment c
                WHERE idEstablishment = ?1 and c.sentiment = 2
                """)
        int countByReviewNeutral(UUID idEstablishment);

        @Query("""
                SELECT count(*) FROM com.foodway.api.model.Comment c
                WHERE idEstablishment = ?1 and c.sentiment = 3 
                """)    
        int countByReviewError(UUID idEstablishment);

        @Query("""  
                SELECT count(*) FROM com.foodway.api.model.Comment c
                WHERE idEstablishment = ?1 and c.sentiment <> null and c.sentiment <> 3
                """)
        int countAllReviewByIdEstablishment(UUID idEstablishment);

        @Query("""
            SELECT new map(
                SUM(CASE WHEN c.sentiment = 0 THEN 1 ELSE 0 END) AS positive,
                SUM(CASE WHEN c.sentiment = 1 THEN 1 ELSE 0 END) AS negative,
                SUM(CASE WHEN c.sentiment = 2 THEN 1 ELSE 0 END) AS neutral,
                SUM(CASE WHEN c.sentiment = 3 THEN 1 ELSE 0 END) AS error,
                SUM(CASE WHEN c.sentiment <> 3 THEN 1 ELSE 0 END) AS total
            )
            FROM com.foodway.api.model.Comment c
            WHERE c.idEstablishment = ?1 AND c.sentiment IS NOT NULL
            """)
        Map<String, Long> countReviewsBySentiment(UUID idEstablishment);

}
