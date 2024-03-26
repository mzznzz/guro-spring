package spring.guro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.guro.entity.BranchInventory;

import java.util.Optional;

public interface BranchInventoryRepository extends JpaRepository<BranchInventory, Long> {

    @Query("SELECT bi FROM BranchInventory bi WHERE bi.branch.branchId = :branchId AND bi.ingredient.igId = :igId")
    Optional<BranchInventory> findByBranchAndIngredientIds(@Param("branchId") Long branchId, @Param("igId") Long igId);
}
