package moonpo.consumable.service.impl;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Category;
import moonpo.consumable.model.mapid.CategoryID;
import moonpo.consumable.repository.CategoryRepository;
import moonpo.consumable.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryById(CategoryID categoryID) {
        if (categoryRepository.existsById(categoryID)) {
            return categoryRepository.findById(categoryID).get();
        } else {
            throw new RuntimeException("Category not already exists.");
        }
    }

    @Override
    public Category getCategoryByCode(String code) {
        if (code != null) {
            return categoryRepository.findCategoriesByCode(code);
        }
        return null;
    }

    @Override
    public Category saveCategory(Category category) {
        CategoryID categoryID = new CategoryID(category.getCode(), category.getGroup());
        if (categoryRepository.existsById(categoryID)) {
            throw new RuntimeException("Category already exists.");
        } else {
            categoryRepository.save(category);
            return category;
        }
    }

    @Override
    public Category updateCategory(Category category) {
        CategoryID categoryID = new CategoryID(category.getCode(), category.getGroup());
        if (categoryRepository.existsById(categoryID)) {
            categoryRepository.saveAndFlush(category);
            return category;
        } else {
            throw new RuntimeException("Category not already exists.");
        }
    }

    @Override
    public Boolean deleteCategoryById(CategoryID categoryID) {
        if (categoryRepository.existsById(categoryID)) {
            categoryRepository.deleteById(categoryID);
            return true;
        } else {
            throw new RuntimeException("Category not already exists.");
        }
    }

}
