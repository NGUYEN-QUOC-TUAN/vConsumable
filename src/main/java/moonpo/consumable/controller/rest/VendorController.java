package moonpo.consumable.controller.rest;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Vendor;
import moonpo.consumable.model.mapid.VendorID;
import moonpo.consumable.service.VendorService;
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
@RequestMapping("api/v1/vendor")
public class VendorController {

    private final VendorService vendorService;

    //GET FULL VENDORS:
    @GetMapping("/get-full")
    public ResponseEntity<List<Vendor>> getAllVendorsFull() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }
    // GET ALL VENDORS BY PAGE:
    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<Vendor>>> getAllVendors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            PagedResourcesAssembler<Vendor> resourcesAssembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("code").ascending());
        Page<Vendor> vendors = vendorService.getAllVendors(pageable);
        PagedModel<EntityModel<Vendor>> model = resourcesAssembler.toModel(vendors);
        return ResponseEntity.ok(model);
    }
    // GET VENDOR BY ID:
    @GetMapping("/get-one/{code}/{group}")
    public ResponseEntity<Vendor> getOneVendor(@PathVariable(name = "code") String code, @PathVariable(name = "group") String group) {
        VendorID vendorID = new VendorID(code, group);
        Vendor vendor = vendorService.getOneVendor(vendorID);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
    // GET VENDOR BY CODE:
    @GetMapping("/get-by-code/{code}")
    public ResponseEntity<Vendor> getVendorByCode(@PathVariable(name = "code") String code) {
        Vendor vendor = vendorService.getVendorByCode(code);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
    // CREATE:
    @PostMapping("/save")
    public ResponseEntity<Vendor> saveVendor(@RequestBody Vendor vendor) {
        Vendor vendorSave = vendorService.saveVendor(vendor);
        return ResponseEntity.ok(vendorSave); // RETURN SAVED VENDOR.
    }
    // UPDATE:
    @PutMapping("/update")
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor) {
        Vendor vendorUpdate = vendorService.updateVendor(vendor);
        return ResponseEntity.ok(vendorUpdate); // RETURN UPDATED VENDOR.
    }
    // DELETE:
    @DeleteMapping("/delete/{code}/{group}")
    public ResponseEntity<Void> deleteVendor(@PathVariable(name = "code") String code, @PathVariable(name = "group") String group) {
        VendorID vendorID = new VendorID(code, group);
        boolean isDeleted = vendorService.deleteVendor(vendorID);
        if (isDeleted) {
            return ResponseEntity.ok().build(); // RETURN 200 OK IF DELETED.
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }
}
