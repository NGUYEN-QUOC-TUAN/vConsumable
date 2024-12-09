package moonpo.consumable.controller.rest;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Category;
import moonpo.consumable.model.mapid.CategoryID;
import moonpo.consumable.service.CategoryService;
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
@AllArgsConstructor
@RequestMapping(path = "api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    // GET FULL CATEGORIES:
    @GetMapping("/get-full")
    public ResponseEntity<List<Category>> getFullCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    // GET ALL CATEGORY BY PAGE:
    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<Category>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            PagedResourcesAssembler<Category> resourcesAssembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("code").ascending());
        Page<Category> categorys = categoryService.getAllCategories(pageable);
        PagedModel<EntityModel<Category>> model = resourcesAssembler.toModel(categorys);
        return ResponseEntity.ok(model);
    }

    // GET CATEGORY BY ID - (CODE, GROUP):
    @GetMapping("/get-one/{code}/{group}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(name = "code") String code, @PathVariable(name = "group") String group) {
        CategoryID categoryID = new CategoryID(code, group);
        Category category = categoryService.getCategoryById(categoryID);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOTFOUND.
        }
    }

    // GET CATEGORY BY CODE:
    @GetMapping("/get-by-code/{code}")
    public ResponseEntity<Category> getCategoryByCode(@PathVariable(name = "code") String code) {
        Category category = categoryService.getCategoryByCode(code);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOTFOUND.
        }
    }

    // SAVE CATEGORY:
    @PostMapping("/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory); // RETURN SAVED CATEGORY.
    }

    // UPDATE CATEGORY:
    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory); // RETURN UPDATED CATEGORY.
    }

    // DELETE CATEGORY BY ID - (CODE, GROUP):
    @DeleteMapping("/delete/{code}/{group}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable(name = "code") String code, @PathVariable(name = "group") String group) {
        CategoryID categoryID = new CategoryID(code, group);
        boolean isDeleted = categoryService.deleteCategoryById(categoryID);
        if (isDeleted) {
            return ResponseEntity.ok().build(); // RETURN 200 OK IF DELETED.
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }

}
