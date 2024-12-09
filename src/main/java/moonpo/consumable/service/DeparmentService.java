package moonpo.consumable.service;

import moonpo.consumable.entity.Department;
import moonpo.consumable.model.mapid.DepartmentID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeparmentService {
    List<Department> getDepartmentsDepartments();

    Page<Department> getAllDepartments(Pageable pageable);

    Department getDepartmentById(DepartmentID departmentID);

    Department getDepartmentByCode(String code);

    Department saveDepartment(Department department);

    Department updateDepartment(Department department);

    Boolean deleteDepartment(DepartmentID departmentID);
}
