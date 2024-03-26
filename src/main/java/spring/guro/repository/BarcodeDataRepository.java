package spring.guro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.BarcodeData;

import java.util.List;

public interface BarcodeDataRepository extends JpaRepository<BarcodeData, Integer> {
    // 바코드 값을 기준으로 제품을 조회하는 메소드
    List<BarcodeData> findByBarcode(String barcode);
}
