package co.ke.aeontech.controllers;

import java.util.List;

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

import co.ke.aeontech.models.Payment;
import co.ke.aeontech.pojos.Dates;
import co.ke.aeontech.pojos.PaymentNotification;
import co.ke.aeontech.pojos.SuccessResponse;
import co.ke.aeontech.services.PaymentService;

@RestController
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	@PostMapping(value = "/payment")
	public ResponseEntity<SuccessResponse> addPaymentNotification(
			@RequestBody PaymentNotification notification) {
		final Payment newPayment = paymentService.processNotification(notification);
		
		log.info("###### Notification: "+newPayment);
		return new ResponseEntity<SuccessResponse>(paymentReceived, HttpStatus.OK);
	}
	
	@GetMapping(value = "/payment/{id}")
	public ResponseEntity<Object> getByOrganisationId(@PathVariable("id") Long id){
		final List<Payment> payments = paymentService.findByOrganisationId(id);
		return new ResponseEntity<Object>(payments, HttpStatus.OK);
	}
	
	@PostMapping(value = "/payment/btwn/{id}")
	public ResponseEntity<Object> searchBtwm(@PathVariable("id") Long id,
			@RequestBody final Dates request){
		final List<Payment> payments = paymentService
				.SearchBtw(request.getFrom(), request.getTo(), id);
		return new ResponseEntity<Object>(payments, HttpStatus.OK);
	}
	
	private final static SuccessResponse paymentReceived = new SuccessResponse
			("success", "notification received.");
}
