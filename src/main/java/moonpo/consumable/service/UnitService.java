package moonpo.consumable.service;

import moonpo.consumable.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UnitService {
    List<Unit> getUnitList();

    Page<Unit> getAllUnits(Pageable pageable);

    Unit getUnitById(String unitId);

    Unit getUnitByCode(String code);

    Unit saveUnit(Unit unit);

    Unit updateUnit(Unit unit);

    Boolean deleteById(String unitId);

}
