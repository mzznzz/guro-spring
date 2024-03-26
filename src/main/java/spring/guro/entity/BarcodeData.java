package spring.guro.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "barcode_data")
public class BarcodeData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    private Double price;

    private String image; // 제품 이미지 경로

    @Column(name = "`desc`")
    private String desc; // 제품 설명

    @Column(name = "iced_only")
    private Boolean icedOnly; // 아이스 전용 여부

    @Column(name = "extra_price")
    private Double extraPrice; // 추가 가격

    @Column(name = "barcode")
    private String barcode; // 바코드

    @Column(name = "no_option")
    private Boolean noOption; // 옵션 없음 여부

    // Getters and Setters
}
