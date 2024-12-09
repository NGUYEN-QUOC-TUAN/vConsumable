package moonpo.consumable.repository;

import moonpo.consumable.entity.Material;
import moonpo.consumable.model.mapid.MaterialID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, MaterialID> {

    @Query("SELECT m FROM Material m WHERE m.num = :num")
    Material findMaterialByNum(@Param("num") String num);
}
