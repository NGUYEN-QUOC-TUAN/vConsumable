package moonpo.consumable.repository;

import moonpo.consumable.entity.Department;
import moonpo.consumable.model.mapid.DepartmentID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, DepartmentID> {

    @Query("SELECT d FROM Department d WHERE d.code = :code")
    Department findDepartmentByCode(@Param("code") String code);
}
