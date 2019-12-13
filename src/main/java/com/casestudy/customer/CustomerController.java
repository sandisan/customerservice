package com.casestudy.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	
	@GetMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Customer> getAllItems() {
		return customerRepository.findAll();
	}

	@PostMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//@HystrixCommand(fallbackMethod="fallBackSaveCustomer")
	public String saveCustomer(@RequestBody Customer customer) {
		 
		Customer addedCustomer = customerRepository.save(customer);
		String statusMsg = rabbitMQSender.send(addedCustomer);
		if (statusMsg.equals("success")){
			return "Succuessfully Created Customer Id:"+addedCustomer.getId();
		}
		return "Encountered Error Creating Customer. Please try again after sometime";
	}
	
//	public String fallBackSaveCustomer() {
//		
//		return "Encountered Error Creating Customer. Please try again after sometime";
//	
//	}

}

