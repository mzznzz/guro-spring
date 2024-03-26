package spring.guro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.guro.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT re FROM Recipe re WHERE re.product.pdId=:pdId")
    List<Recipe> findByPdId(int pdId);
}
