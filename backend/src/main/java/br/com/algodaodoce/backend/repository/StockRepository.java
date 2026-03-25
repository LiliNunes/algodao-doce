package br.com.algodaodoce.backend.repository;

import br.com.algodaodoce.backend.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductId(String productId);
}
