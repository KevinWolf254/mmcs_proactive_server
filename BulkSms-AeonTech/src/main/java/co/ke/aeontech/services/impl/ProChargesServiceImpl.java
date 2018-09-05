package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.ProCharges;
import co.ke.aeontech.pojos.PhoneNos;
import co.ke.aeontech.pojos.helpers.Country;
import co.ke.aeontech.pojos.helpers.ServiceProvider;
import co.ke.aeontech.repository.ProChargesRepository;
import co.ke.aeontech.services.ProChargesService;

@Service
public class ProChargesServiceImpl implements ProChargesService{

	@Autowired
	private ProChargesRepository repository;
	
	@Override
	public ProCharges findByCountry(Country country) {		
		return repository.findByCountry(country);
	}

	public double getProCharges(final List<PhoneNos> phoneNos, final Country country) {
		final BigDecimal cost = BigDecimal.ZERO;
		final ProCharges charges = findByCountry(country);
		
		phoneNos.stream().forEach(phoneNo ->{
			if(phoneNo.getTelecom().equals(ServiceProvider.RW_OTHERS))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getRwf()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.RW_AIRTEL))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getRwfAir()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.KE_OTHERS))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getKes()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.KE_AIRTEL))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getKesAir()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.TZ_OTHERS))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getTzs()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.TZ_AIRTEL))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getTzsAir()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.UG_OTHERS))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getUgx()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.UG_AIRTEL))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getUgxAir()));
			else if(phoneNo.getTelecom().equals(ServiceProvider.OTHER))
				cost.add(BigDecimal.valueOf(phoneNo.getTotals() * charges.getOther()));
		});
		cost.setScale(2, RoundingMode.HALF_EVEN);
		return cost.doubleValue();
	}
}
