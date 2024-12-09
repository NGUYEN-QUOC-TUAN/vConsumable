package moonpo.consumable.service.impl;

import lombok.AllArgsConstructor;
import moonpo.consumable.entity.Currency;
import moonpo.consumable.repository.CurrencyRepository;
import moonpo.consumable.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;


    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Page<Currency> listCurrencies(Pageable pageable) {
        return currencyRepository.findAll(pageable);
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        if (currencyRepository.existsById(code)) {
            return currencyRepository.findById(code).get();
        } else {
            throw new RuntimeException("Currency not already exists.");
        }
    }

    @Override
    public Currency getCurrenciesByCode(String code) {
        if (code != null) {
            return currencyRepository.findCurrencyByCode(code);
        }
        return null;
    }

    @Override
    public Currency saveCurrency(Currency currency) {
        if (currencyRepository.existsById(currency.getCode())) {
            throw new RuntimeException("Currency already exists.");
        } else {
            return currencyRepository.save(currency);
        }
    }

    @Override
    public Currency updateCurrency(Currency currency) {
        if (currencyRepository.existsById(currency.getCode())) {
            currencyRepository.saveAndFlush(currency);
            return currency;
        } else {
            throw new RuntimeException("Currency not already exists.");
        }
    }

    @Override
    public Boolean deleteCurrency(String code) {
        if (currencyRepository.existsById(code)) {
            currencyRepository.deleteById(code);
            return true;
        } else {
            return false;
        }
    }
}
