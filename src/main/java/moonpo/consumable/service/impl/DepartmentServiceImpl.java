package moonpo.consumable.service.impl;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Department;
import moonpo.consumable.model.mapid.DepartmentID;
import moonpo.consumable.repository.DepartmentRepository;
import moonpo.consumable.service.DeparmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DeparmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<Department> getDepartmentsDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Page<Department> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Override
    public Department getDepartmentById(DepartmentID departmentID) {
        if (departmentRepository.existsById(departmentID)) {
            return departmentRepository.findById(departmentID).get();
        } else {
            throw new RuntimeException("Department not already exists.");
        }
    }

    @Override
    public Department getDepartmentByCode(String code) {
        if(code != null){
            return departmentRepository.findDepartmentByCode(code);
        }
        return null;
    }

    @Override
    public Department saveDepartment(Department department) {
        DepartmentID departmentID = new DepartmentID(department.getCode(), department.getGroup());
        if (departmentRepository.existsById(departmentID)) {
            throw new RuntimeException("Department already exists.");
        } else {
            departmentRepository.save(department);
            return department;
        }
    }

    @Override
    public Department updateDepartment(Department department) {
        DepartmentID departmentID = new DepartmentID(department.getCode(), department.getGroup());
        if (departmentRepository.existsById(departmentID)) {
            departmentRepository.saveAndFlush(department);
            return department;
        } else {
            throw new RuntimeException("Department not already exists.");
        }
    }

    @Override
    public Boolean deleteDepartment(DepartmentID departmentID) {
        if (departmentRepository.existsById(departmentID)) {
            departmentRepository.deleteById(departmentID);
            return true;
        } else {
            throw new RuntimeException("Department not already exists.");
        }
    }
}
