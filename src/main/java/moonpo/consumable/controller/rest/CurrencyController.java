package moonpo.consumable.controller.rest;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Currency;
import moonpo.consumable.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/currency")
@AllArgsConstructor
public class CurrencyController {

    private CurrencyService service;

    // GET FULL CURRENCIES:
    @GetMapping("/get-full")
    public ResponseEntity<Iterable<Currency>> getFullCurrencies() {
        return ResponseEntity.ok(service.getAllCurrencies());
    }

    // GET ALL CURRENCY:
    @GetMapping("/get-all")
    public ResponseEntity<PagedModel<EntityModel<Currency>>> getAllCurrencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            PagedResourcesAssembler resourcesAssembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("code").ascending());
        Page<Currency> currencies = service.listCurrencies(pageable);
        PagedModel<EntityModel<Currency>> model = resourcesAssembler.toModel(currencies);
        return ResponseEntity.ok(model);
    }

    // GET BY CODE:
    @GetMapping("/get-one/{code}")
    public ResponseEntity<Currency> getOneCurency(@PathVariable(name = "code") String code) {
        Currency currency = service.getCurrencyByCode(code);
        if (currency != null) {
            return ResponseEntity.ok(currency);
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }

    // CREATE:
    @PostMapping("/save")
    public ResponseEntity<Currency> saveCurrency(@RequestBody Currency currency) {
        Currency savedCurrency = service.saveCurrency(currency);
        return ResponseEntity.ok(savedCurrency); // RETURN SAVES CURRENTCY.
    }

    // UPDATE:
    @PutMapping("/update")
    public ResponseEntity<Currency> updateCurrency(@RequestBody Currency currency) {
        Currency updatedCurrency = service.updateCurrency(currency);
        return ResponseEntity.ok(updatedCurrency); // RETURN UPDATED CURRENTCY.
    }

    // DELETE:
    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable(name = "code") String code) {
        boolean isDeleted = service.deleteCurrency(code);
        if (isDeleted) {
            return ResponseEntity.ok().build(); // RETURN 200 OK IF DELETED.
        } else {
            return ResponseEntity.notFound().build(); // RETURN 404 IF NOT FOUND.
        }
    }

}
