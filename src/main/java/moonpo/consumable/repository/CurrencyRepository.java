package moonpo.consumable.repository;

import moonpo.consumable.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    @Query("SELECT c FROM Currency c WHERE c.code = :code")
    Currency findCurrencyByCode(@Param("code") String code);

}
