package moonpo.consumable.service;

import moonpo.consumable.entity.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurrencyService {
    List<Currency> getAllCurrencies();

    Page<Currency> listCurrencies(Pageable pageable);

    Currency getCurrencyByCode(String code);

    Currency getCurrenciesByCode(String code);

    Currency saveCurrency(Currency currency);

    Currency updateCurrency(Currency currency);

    Boolean deleteCurrency(String code);
}
