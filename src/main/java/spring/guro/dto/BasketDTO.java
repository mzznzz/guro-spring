package spring.guro.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BasketDTO {
    private Long pdId;
    private String pdName;
    private Integer price;
    private Integer quantity;

    // Constructors, getters, and setters
}
