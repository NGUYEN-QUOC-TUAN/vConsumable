package moonpo.consumable.service;

import moonpo.consumable.entity.Vendor;
import moonpo.consumable.model.mapid.VendorID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VendorService {
    List<Vendor> getAllVendors();

    Page<Vendor> getAllVendors(Pageable pageable);

    Vendor getOneVendor(VendorID vendorID);

    Vendor getVendorByCode(String code);

    Vendor saveVendor(Vendor vendor);

    Vendor updateVendor(Vendor vendor);

    Boolean deleteVendor(VendorID vendorID);
}
