package spring.guro.dto;

import lombok.*;
import spring.guro.entity.Orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdersDTO {
    private LocalDate date;
    private String productName;
    private Integer mount;
    private Integer price;
    private String branchName;
    private String email;
}
