package moonpo.consumable.controller.rest;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Material;
import moonpo.consumable.model.mapid.MaterialID;
import moonpo.consumable.service.MaterialService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/material")
@AllArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    // GET FULL MATERIALS:
    @GetMapping("/get-full")
    public ResponseEntity<List<Material>> getFullMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    // GET ALL MATERIALS:
    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<Material>>> getAllMaterials(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            PagedResourcesAssembler resourcesAssembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("num").ascending());
        Page<Material> materials = materialService.getAllMaterials(pageable);
        PagedModel<EntityModel<Material>> model = resourcesAssembler.toModel(materials);
        return ResponseEntity.ok(model);

    }

    // GET ONE MATERIAL:
    @GetMapping("/get-one/{item_num}/{item_vendor}/{item_group}")
    public ResponseEntity<Material> getOneMaterial(@PathVariable(name = "item_num") String item_num, @PathVariable(name = "item_vendor") String item_vendor, @PathVariable(name = "item_group") String item_group) {
        MaterialID materialID = new MaterialID(item_num, item_vendor, item_group);
        Material material = materialService.getMaterialById(materialID);
        if (material == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(material);
        }
    }

    // GET MATERIAL BY NUM:
    @GetMapping("/get-by-num/{item_num}")
    public ResponseEntity<Material> getMaterial(@PathVariable(name = "item_num") String item_num) {
        Material material = materialService.getMaterialByCode(item_num);
        if (material == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(material);
        }
    }

    // SAVE MATERIAL:
    @PostMapping("/save")
    public ResponseEntity<Material> saveMaterial(@RequestBody Material material) {
        Material savedMaterial = materialService.saveMaterial(material);
        return ResponseEntity.ok(savedMaterial);
    }

    // UPDATE MATERIAL:
    @PutMapping("/update")
    public ResponseEntity<Material> updateMaterial(@RequestBody Material material) {
        Material savedMaterial = materialService.updateMaterial(material);
        return ResponseEntity.ok(savedMaterial);
    }
    // DELETE MATERIAL:

    @DeleteMapping("/delete/{item_num}/{item_vendor}/{item_group}")
    public ResponseEntity<Boolean> deleteMaterial(@PathVariable(name = "item_num") String item_num, @PathVariable(name = "item_vendor") String item_vendor, @PathVariable(name = "item_group") String item_group) {
        MaterialID materialID = new MaterialID(item_num, item_vendor, item_group);
        boolean isDeleted = materialService.deleteMaterialById(materialID);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
}
