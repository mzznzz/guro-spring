package spring.guro.entity;

import javax.persistence.*;
import lombok.*;
import spring.guro.enumtype.TestBranchStatus;
import spring.guro.enumtype.Authority;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long branchId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String branchName;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private Authority authority; // 권한 : ROLE_USER, ROLE_ADMIN

    @Column
    @Enumerated(EnumType.STRING)
    private TestBranchStatus testBranchStatus;

}
