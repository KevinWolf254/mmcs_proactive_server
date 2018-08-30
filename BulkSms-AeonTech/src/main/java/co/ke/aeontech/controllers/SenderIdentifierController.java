package co.ke.aeontech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.models.SenderIdentifier;
import co.ke.aeontech.pojos.OnSendDeliveryReport;
import co.ke.aeontech.pojos.SmsInfo;
import co.ke.aeontech.services.SenderIdentifierService;

@RestController
public class SenderIdentifierController {
	@Autowired
	private SenderIdentifierService senderIdService;

	@GetMapping(value = "/senderId/{orgNo}")
	public ResponseEntity<SenderIdentifier> register(@PathVariable("orgNo") Long org_no){
		final SenderIdentifier senderId = senderIdService.findByOrganisationId(org_no);
		return new ResponseEntity<SenderIdentifier>(senderId, HttpStatus.OK);
	}
	
	@PostMapping(value = "/senderId")
	public ResponseEntity<Boolean> register(@RequestParam("senderId") String senderId){
		Boolean result = senderIdService.foundSenderIdByName(senderId);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping(value = "/senderId/contacts")
	public ResponseEntity<Object> sendSms(@RequestBody SmsInfo smsInfo){
		final OnSendDeliveryReport report = senderIdService.sendSms(smsInfo);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
}
