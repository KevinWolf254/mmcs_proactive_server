package co.ke.aeontech.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.aeontech.pojos.Sms;
import co.ke.aeontech.pojos.reports.SmsDeliveryReport;
import co.ke.aeontech.pojos.reports.SmsValidationReport;
import co.ke.aeontech.services.SmsService;

@RestController
public class SmsController {
	@Autowired
	private SmsService smsService;

	@PostMapping(value = "/sms")
	public ResponseEntity<Object> sendSms(@RequestBody Sms sms){
		final SmsDeliveryReport report = smsService.sendSms(sms);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
	
	@PutMapping(value = "/sms")
	public ResponseEntity<Object> validateSms(@RequestBody Sms sms){
		final SmsValidationReport report = smsService.validateSms(sms);
		return new ResponseEntity<Object>(report, HttpStatus.OK);
	}
}
