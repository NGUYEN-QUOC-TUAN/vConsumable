package moonpo.consumable.service.impl;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Unit;
import moonpo.consumable.repository.UnitRepository;
import moonpo.consumable.service.UnitService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public List<Unit> getUnitList() {
        return unitRepository.findAll();
    }

    @Override
    public Page<Unit> getAllUnits(Pageable pageable) {
        return unitRepository.findAll(pageable);
    }

    @Override
    public Unit getUnitById(String unitId) {
        if (unitRepository.existsById(unitId)) {
            return unitRepository.findById(unitId).get();
        } else {
            throw new RuntimeException("Unit not found.");
        }
    }

    @Override
    public Unit getUnitByCode(String code) {
        if (code != null) {
            return unitRepository.findUnitByCode(code);
        }
        return null;
    }

    @Override
    public Unit saveUnit(Unit unit) {
        if (unitRepository.existsById(unit.getCode())) {
            throw new RuntimeException("Unit already exists.");
        } else {
            return unitRepository.save(unit);
        }
    }

    @Override
    public Unit updateUnit(Unit unit) {
        if (unitRepository.existsById(unit.getCode())) {
            return unitRepository.saveAndFlush(unit);
        } else {
            throw new RuntimeException("Unit not already exists.");
        }
    }

    @Override
    public Boolean deleteById(String unitId) {
        if (unitRepository.existsById(unitId)) {
            unitRepository.deleteById(unitId);
            return true;
        } else {
            throw new RuntimeException("Unit not found.");
        }
    }
}
