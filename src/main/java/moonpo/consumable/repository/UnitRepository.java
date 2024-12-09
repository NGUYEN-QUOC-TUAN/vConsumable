package moonpo.consumable.repository;

import moonpo.consumable.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {

    @Query("SELECT u FROM Unit u WHERE u.code = :code")
    Unit findUnitByCode(@Param("code") String code);
}
