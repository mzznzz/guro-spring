package spring.guro.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pdId;

    private String pdCategory;

    private String pdName;

    private Double pdPrice;

    private String image; // 제품 이미지 경로

    @Column(name = "`desc`")
    private String desc; // 제품 설명

    private Boolean icedOnly; // 아이스 전용 여부

    private Double extraPrice; // 추가 가격
}
