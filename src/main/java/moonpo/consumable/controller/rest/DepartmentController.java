package moonpo.consumable.controller.rest;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Department;
import moonpo.consumable.model.mapid.DepartmentID;
import moonpo.consumable.service.DeparmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/department")
@AllArgsConstructor
public class DepartmentController {

    private final DeparmentService departmentService;

    // GET FULL DEPARTMENTS:
    @GetMapping("/get-full")
    public ResponseEntity<Iterable<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getDepartmentsDepartments());
    }

    // GET ALL DEPARTMENT:
    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<Department>>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            PagedResourcesAssembler<Department> resourcesAssembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("code").ascending());
        Page<Department> departments = departmentService.getAllDepartments(pageable);
        PagedModel<EntityModel<Department>> model = resourcesAssembler.toModel(departments);
        return ResponseEntity.ok(model);
    }

    // GET ONE DEPARTMENT BY ID:
    @GetMapping("/get-one/{code}/{group}")
    public ResponseEntity<Department> getOneDepartment(@PathVariable(name = "code") String code, @PathVariable(name = "group") String group) {
        DepartmentID departmentID = new DepartmentID(code, group);
        Department department = departmentService.getDepartmentById(departmentID);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
    // GET ONE DEPARTMENT BY CODE:
    @GetMapping("/get-by-code/{code}")
    public ResponseEntity<Department> getDepartmentByCode(@PathVariable(name = "code") String code) {
        Department department = departmentService.getDepartmentByCode(code);
        if (department != null) {
            return ResponseEntity.ok(department);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }

    // SAVE DEPARTMENT:
    @PostMapping("/save")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(savedDepartment); // RETURN SAVED DEPARTMENT.
    }

    // UPDATE DEPARTMENT:
    @PutMapping("/update")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
        Department updatedDepartment = departmentService.updateDepartment(department);
        return ResponseEntity.ok(updatedDepartment); // RETURN UPDATED DEPARTMENT.
    }

    // DELETE DEPARTMENT BY ID - (CODE, GROUP):
    @DeleteMapping("/delete/{code}/{group}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable(name = "code") String code, @PathVariable(name = "group") String group) {
        DepartmentID departmentID = new DepartmentID(code, group);
        boolean isDeleted = departmentService.deleteDepartment(departmentID);
        if (isDeleted) {
            return ResponseEntity.ok().build(); // RETURN 200 OK IF DELETED.
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
}
