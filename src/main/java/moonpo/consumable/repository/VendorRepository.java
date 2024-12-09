package moonpo.consumable.repository;

import moonpo.consumable.entity.Vendor;
import moonpo.consumable.model.mapid.VendorID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, VendorID> {

    @Query("SELECT v FROM Vendor v WHERE v.code = :code")
    Vendor findVendorByCode(@Param("code") String code);
}
