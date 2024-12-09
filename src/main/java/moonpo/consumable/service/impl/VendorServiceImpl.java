package moonpo.consumable.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import moonpo.consumable.entity.Vendor;
import moonpo.consumable.model.mapid.VendorID;
import moonpo.consumable.repository.VendorRepository;
import moonpo.consumable.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Page<Vendor> getAllVendors(Pageable pageable) {
        return vendorRepository.findAll(pageable);
    }

    @Override
    public Vendor getOneVendor(VendorID vendorID) {
        if (vendorRepository.existsById(vendorID)) {
            return vendorRepository.findById(vendorID).get();
        } else {
            throw new RuntimeException("Vendor not found");
        }
    }

    @Override
    public Vendor getVendorByCode(String code) {
        if (code != null) {
            return vendorRepository.findVendorByCode(code);
        }
        return null;
    }

    @Override
    public Vendor saveVendor(Vendor vendor) {
        VendorID vendorID = new VendorID(vendor.getCode(), vendor.getGroup());
        if (vendorRepository.existsById(vendorID)) {
            throw new RuntimeException("Vendor already exist.");
        } else {
            vendorRepository.save(vendor);
            return vendor;
        }
    }

    @Override
    public Vendor updateVendor(Vendor vendor) {
        VendorID vendorID = new VendorID(vendor.getCode(), vendor.getGroup());
        if (vendorRepository.existsById(vendorID)) {
            vendorRepository.saveAndFlush(vendor);
            return vendor;
        } else {
            throw new RuntimeException("Vendor not already exists.");
        }
    }

    @Override
    public Boolean deleteVendor(VendorID vendorID) {
        if (vendorRepository.existsById(vendorID)) {
            vendorRepository.deleteById(vendorID);
            return true;
        } else {
            throw new RuntimeException("Vendor not already exists.");
        }
    }
}
