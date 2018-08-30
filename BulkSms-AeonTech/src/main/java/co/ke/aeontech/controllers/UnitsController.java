package co.ke.aeontech.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.ExchangeRate;
import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Units;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.MpesaConfirmation;
import co.ke.aeontech.pojos.Reply;
import co.ke.aeontech.pojos.UnitsDetailsResponse;
import co.ke.aeontech.pojos.UnitsResponse;
import co.ke.aeontech.services.AeonUnitsService;
import co.ke.aeontech.services.ExchangeRatesService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.PaymentService;
import co.ke.aeontech.services.UnitsService;

@RestController
public class UnitsController {
	
	@Autowired
	private UnitsService unitsService;
	@Autowired
	private OrganisationService orgService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private AeonUnitsService aeonUnitsService;
	@Autowired
	private ExchangeRatesService ratesService;
	
	@GetMapping(value = "/units/info/{email}")
	public ResponseEntity<UnitsDetailsResponse> getUnitsInfoByEmail(
			@PathVariable("email") final String email) {
		UnitsDetailsResponse response = unitsService.getUnitsInfo(email);
		return new ResponseEntity<UnitsDetailsResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/units/info/id/{id}")
	public ResponseEntity<UnitsDetailsResponse> getUnitsInfoById(
			@PathVariable("id") final Long id) {
		final UnitsDetailsResponse response = unitsService.getUnitsInfoById(id);
		return new ResponseEntity<UnitsDetailsResponse>(response, HttpStatus.OK);
	}
	
	/**
	 * used by the organization user to confirm top up payments
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/units")
	public ResponseEntity<Object> confirmUnits(
			@RequestBody final MpesaConfirmation request) {			
		final Organisation organisation = 
				orgService.findByAdministratorsEmail(request.getRequestedBy());		
		//check if payment has been made
		final UnitsResponse _response = 
				paymentService.confirmPayment(organisation, request.getMpesaTransNo()
						, request.getRequestedUnits());
		log.info("Confirming: "+request.getRequestedUnits());
		if(_response.getStatus() == Reply.FAILED) {
			return new ResponseEntity<Object>(_response, HttpStatus.OK);
		}	
		final UnitsResponse response = unitsService.confirmPayment(organisation, request);		 
		return new ResponseEntity<Object>(response, HttpStatus.OK);		
	}
	/**
	 * used by proactive_io user to fulfill top up requests
	 * which are pending
	 * after toping up aeon_ units
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/fulfill/pending/units")
	public ResponseEntity<Object> fullFillTopUpRequest(
			@RequestBody final MpesaConfirmation request) {			
		final Organisation organisation = 
				orgService.findByAdministratorsEmail(request.getRequestedBy());
		
		//find pending request by provider_ref id/mpesa_no
		//add amount to organization units after converting to org_currency
		//update request status to fulfilled
		
		return new ResponseEntity<Object>(HttpStatus.OK);		
	}
	
	@PostMapping(value = "/units/subtract/{expense}/{organisation}")
	public void subtractUnits(
			@PathVariable("organisation") final Long org_number,
			@PathVariable("expense") final double amount) {
		final Units units = unitsService.findByOrganisationId(org_number);
		
		final BigDecimal available = BigDecimal.valueOf(units.getUnitsAvailable());
		final BigDecimal expense = BigDecimal.valueOf(amount);

		units.setUnitsAvailable(available.subtract(expense).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
		unitsService.save(units);
		
		//convert amount from organization currency to aeon_currency
		ExchangeRate rates = ratesService
				.findByCountry(orgService.findById(org_number).getCountry());
		final BigDecimal converted = ratesService
				.convertToCurrency(Country.KENYA, expense, rates);
		//subtract/update aeon_units
		aeonUnitsService.subtractAeonUnits(converted);
	}
	private static final Logger log = LoggerFactory.getLogger(UnitsController.class);

}
