package spring.guro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
