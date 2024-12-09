package moonpo.consumable.service;

import moonpo.consumable.entity.Category;
import moonpo.consumable.model.mapid.CategoryID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Page<Category> getAllCategories(Pageable pageable);

    Category getCategoryById(CategoryID categoryID);

    Category getCategoryByCode(String code);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    Boolean deleteCategoryById(CategoryID categoryID);
}
