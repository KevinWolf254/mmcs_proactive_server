package co.ke.aeontech.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.ke.aeontech.models.Organisation;
import co.ke.aeontech.models.Units;
import co.ke.aeontech.models.UnitsRequest;
import co.ke.aeontech.pojos.MpesaConfirmation;
import co.ke.aeontech.pojos.Country;
import co.ke.aeontech.pojos.EmailMessage;
import co.ke.aeontech.pojos.Reply;
import co.ke.aeontech.pojos.UnitsDetailsResponse;
import co.ke.aeontech.pojos.UnitsResponse;
import co.ke.aeontech.repository.UnitsRepository;
import co.ke.aeontech.services.AeonUnitsService;
import co.ke.aeontech.services.EmailService;
import co.ke.aeontech.services.OrganisationService;
import co.ke.aeontech.services.UnitsRequestService;
import co.ke.aeontech.services.UnitsService;

@Service
public class UnitsServiceImpl implements UnitsService{

	@Autowired
	private UnitsRepository repository;
	
	@Autowired
	private OrganisationService organisationService;
	@Autowired
	private UnitsRequestService requestService;
	@Autowired
	private AeonUnitsService aeonService;
	@Autowired
	private EmailService emailService;


	@Value("${spring.mail.username}")
	private String proactiveEmail;
	
	@Async
	@Override
	public void save(Units units){
		final Units savedUnits = repository.save(units);
		log.info("###### Saved: "+savedUnits);
	} 

	@Override
	public UnitsDetailsResponse getUnitsInfo(String email) {
		
		final Organisation organisation = organisationService
				.findByAdministratorsEmail(email);
		final Long id = organisation.getId();
		
		final Units units = findByOrganisationId(id);
		
		final List<UnitsRequest> pending_requests = requestService
				.findPendingByOrganisationId(id);
		
		final BigDecimal pendingAmount = requestService
				.calculatePendingAmounts(organisation, pending_requests);
		
		return new UnitsDetailsResponse(organisation.getCountry(), 
				units.getUnitsAvailable(), pending_requests.size(), 
				pendingAmount.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
	}
	
	@Override
	public UnitsDetailsResponse getUnitsInfoById(Long id) {
		
		final Organisation organisation = organisationService.findById(id);
		
		final Units units = findByOrganisationId(id);
		
		final List<UnitsRequest> pending_requests = requestService
				.findPendingByOrganisationId(id);
		
		final BigDecimal pendingAmount = requestService
				.calculatePendingAmounts(organisation, pending_requests);
		
		return new UnitsDetailsResponse(organisation.getCountry(), 
				units.getUnitsAvailable(), pending_requests.size(), 
				pendingAmount.setScale(2, RoundingMode.HALF_EVEN).doubleValue());
	}
	
	@Override
	public Units findByOrganisationId(Long clientId){
		return repository.findByOrganisationId(clientId);
	}

	@Override
	public UnitsResponse confirmPayment(Organisation organisation, MpesaConfirmation request) {
		//save the pending request for the organization
		requestService.save(new UnitsRequest(request.getRequestedUnits(), request.getMpesaTransNo(), 
				organisation));
		
		//check if units are available and can fulfill all the requests for the organization
		final List<UnitsRequest> pending_requests = requestService
				.findPendingByOrganisationId(organisation.getId());

		//get organization currency
		final Country org_currency = organisation.getCountry();

		//calculate and get, in the organization's currency, the total pending amount
		//they may have made multiple payments in different currencies
		final BigDecimal total_pending = requestService
				.calculatePendingAmounts(organisation, pending_requests);

		// NB: aeonUnits == KES
		//change aeonUnits to organization currency
		final BigDecimal converted_aeonUnits = aeonService.convertAeonUnitsTo(org_currency);
		
		log.info("aeon units"+converted_aeonUnits);
		log.info("is aeon units < total pending? "+(converted_aeonUnits.compareTo(total_pending) == -1));
		//check if supplier units are less than pending top up requests
		if(converted_aeonUnits.compareTo(total_pending) == -1) {
			final StringBuilder builder = new StringBuilder();
			
			builder.append("Request ")
					.append(request.getMpesaTransNo())
					.append(" for ")
					.append(request.getRequestedUnits())
					.append(" has been recieved. Kindly wait until the requested units "
							+ "are added to your account. This may"
							+ " take 5 to 10 minutes. Thank you for your patience.");
			
			//send email to client
			emailService.sendEmail(new EmailMessage(request.getRequestedBy(), 
					"Payment Confirmation Successful", builder.toString()));
			//send email to notify proactive_io administrator
			emailService.sendEmail(new EmailMessage(proactiveEmail, "Top up units.", builder
					.append("organisation: ").append(organisation.getName())
					//to include amount in kes_
					.append("Transaction Details: ").append(request.toString()).toString()));
			
			return new UnitsResponse(Reply.SUCCESS, "payment confirmation successful."
					+ "Top up will occur in 5 to 10 minutes.");
		}
		
		//update aeon_units
		//change back aeonUnits to original currency
		aeonService.updateAeonUnits(aeonService.convertAeonUnitsFrom(org_currency, 
				converted_aeonUnits.subtract(total_pending)).doubleValue());
		
		//update Organization units
		final Units org_units_info = findByOrganisationId(organisation.getId());
		log.info("###### Updating from: "+org_units_info);
		org_units_info.setUnitsAvailable((BigDecimal.valueOf(org_units_info.getUnitsAvailable()).add(total_pending)).doubleValue());
		save(org_units_info);
		
		//update client requests to status fulfilled
		requestService.updatePendingUnits(pending_requests);
		
		emailService.sendEmail(new EmailMessage(request.getRequestedBy(), "Payment Confirmation Successful. Top Up Complete.", 
				"All pending top up requests have been fulfilled. No pending requests remaining"));
		
		return new UnitsResponse(Reply.SUCCESS, "top up complete");
	}	
	
	@Override
	public void delete(Units units) {
		repository.delete(units);
	}
	private static final Logger log = LoggerFactory.getLogger(UnitsServiceImpl.class);
}
