package spring.guro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Branch;

public interface TestBranchRepository extends JpaRepository<Branch, Long> {
    Branch findByBranchName(String branchName);
}
