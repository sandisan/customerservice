package com.casestudy.customer;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {
	//@Autowired
	//private RabbitMQProperties rabbitMQProperties;
	
	@Autowired
	private RabbitTemplate  rabbitTemplate;
	
	@Value("${customer.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${customer.rabbitmq.routingkey}")
	private String routingkey;	
	
	public String send(Customer customer) {
		System.out.println("RabbitMQSender -> send ******************************");
		try {
			rabbitTemplate.convertAndSend(exchange, routingkey, customer);		
		}catch(AmqpException amqe) {
			return "error";
		}
		System.out.println("Send msg = " + customer);
		return "success";
	}
	
//	public String fallBackSendMessage() {
//		System.out.println("fallBackSendMessage ++++++++++++++++++++++++++++++++++++");
//		return "Encountered Error Creating Customer. Please try again after sometime";
//	
//	}
}
