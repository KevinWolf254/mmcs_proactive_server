package co.ke.aeontech.repository.custom;

import java.util.Date;
import java.util.List;

import co.ke.aeontech.models.Payment;

public interface PaymentRepositoryCustom{

	public List<Payment> SearchBtw(Date from, Date to, Long id);
}
