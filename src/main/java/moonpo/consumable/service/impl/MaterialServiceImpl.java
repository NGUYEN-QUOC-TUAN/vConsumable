package moonpo.consumable.service.impl;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Material;
import moonpo.consumable.model.mapid.MaterialID;
import moonpo.consumable.repository.MaterialRepository;
import moonpo.consumable.service.MaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Page<Material> getAllMaterials(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    @Override
    public Material getMaterialById(MaterialID materialID) {
        if (materialRepository.existsById(materialID)) {
            return materialRepository.findById(materialID).get();
        } else {
            throw new RuntimeException("Material not already exists.");
        }
    }

    @Override
    public Material getMaterialByCode(String code) {
        if(code != null){
            return materialRepository.findMaterialByNum(code);
        }
        return null;
    }

    @Override
    public Material saveMaterial(Material material) {
        MaterialID materialID = new MaterialID(material.getNum(), material.getVendor(), material.getGroup());
        if (materialRepository.existsById(materialID)) {
            throw new RuntimeException("Material already exists.");
        } else {
            materialRepository.save(material);
            return material;
        }
    }

    @Override
    public Material updateMaterial(Material material) {
        MaterialID materialID = new MaterialID(material.getNum(), material.getVendor(), material.getGroup());
        if (materialRepository.existsById(materialID)) {
            materialRepository.saveAndFlush(material);
            return material;
        } else {
            throw new RuntimeException("Material not already exists.");
        }
    }

    @Override
    public Boolean deleteMaterialById(MaterialID materialID) {
        if (materialRepository.existsById(materialID)) {
            materialRepository.deleteById(materialID);
            return true;
        } else {
            throw new RuntimeException("Material not found.");
        }
    }
}
