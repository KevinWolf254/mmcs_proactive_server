package co.ke.aeontech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.ke.aeontech.models.Inventory;
import co.ke.aeontech.pojos.helpers.Currency;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public Inventory findByCurrency(Currency currency);

}
