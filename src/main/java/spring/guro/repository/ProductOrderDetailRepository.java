package spring.guro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.ProductOrderDetail;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetail, Long> {

}
