package moonpo.consumable.controller.rest;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Unit;
import moonpo.consumable.service.UnitService;
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
@RequestMapping("/api/v1/unit")
@AllArgsConstructor
public class UnitMeasureController {

    private final UnitService unitService;

    // GET FULL UNITS:
    @GetMapping("/get-full")
    public ResponseEntity<Iterable<Unit>> getFullUnits() {
        return ResponseEntity.ok(unitService.getUnitList());
    }

    // GET ALL UNITS:
    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<Unit>>> getAllUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            PagedResourcesAssembler resourcesAssembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("code").ascending());
        Page<Unit> units = unitService.getAllUnits(pageable);
        PagedModel<EntityModel<Unit>> model = resourcesAssembler.toModel(units);
        return ResponseEntity.ok(model);
    }

    // GET ONE UNIT:
    @GetMapping("/get-one/{code}")
    public ResponseEntity<Unit> getOneUnit(@PathVariable(name = "code") String unitId) {
        Unit unit = unitService.getUnitById(unitId);
        if (unit != null) {
            return ResponseEntity.ok(unit);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }

    // GET UNIT BY CODE:
    @GetMapping("/get-by-code/{code}")
    public ResponseEntity<Unit> getUnitByCode(@PathVariable(name = "code") String code) {
        Unit unit = unitService.getUnitByCode(code);
        if (unit != null) {
            return ResponseEntity.ok(unit);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }

    // SAVE UNIT:
    @PostMapping("/save")
    public ResponseEntity<Unit> saveUnit(@RequestBody Unit unit) {
        Unit savedUnit = unitService.saveUnit(unit);
        return ResponseEntity.ok(savedUnit); // RETURN SAVED UNIT.
    }

    // UPDATE UNIT:
    @PutMapping("/update")
    public ResponseEntity<Unit> updateUnit(@RequestBody Unit unit) {
        Unit updateUnit = unitService.updateUnit(unit);
        return ResponseEntity.ok(updateUnit); // RETURN UPDATED UNIT.
    }

    // DELETE UNIT:
    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Boolean> deleteUnit(@PathVariable(name = "code") String code) {
        Boolean isDelete = unitService.deleteById(code);
        if (isDelete) {
            return ResponseEntity.ok().build(); // RETURN 200 OK IF DELETED.
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
}
