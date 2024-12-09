package moonpo.consumable.service;

import moonpo.consumable.entity.Material;
import moonpo.consumable.model.mapid.MaterialID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialService {
    List<Material> getAllMaterials();

    Page<Material> getAllMaterials(Pageable pageable);

    Material getMaterialById(MaterialID materialID);

    Material getMaterialByCode(String code);

    Material saveMaterial(Material material);

    Material updateMaterial(Material material);

    Boolean deleteMaterialById(MaterialID materialID);
}
