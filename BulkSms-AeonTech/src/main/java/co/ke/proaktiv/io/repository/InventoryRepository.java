package co.ke.proaktiv.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.proaktiv.io.models.Inventory;
import co.ke.proaktiv.io.pojos.helpers.Currency;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public Inventory findByCurrency(Currency currency);

}
