package moonpo.consumable.repository;

import moonpo.consumable.entity.Category;
import moonpo.consumable.model.mapid.CategoryID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, CategoryID> {

    @Query("SELECT c FROM Category c WHERE c.code = :code")
    Category findCategoriesByCode(@Param("code") String code);
}
