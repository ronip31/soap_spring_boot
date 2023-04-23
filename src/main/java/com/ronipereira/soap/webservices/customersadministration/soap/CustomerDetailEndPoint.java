package com.ronipereira.soap.webservices.customersadministration.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ronipereira.CustomerDetail;
import com.ronipereira.DeleteCustomerRequest;
import com.ronipereira.DeleteCustomerResponse;
import com.ronipereira.GetAllCustomerDetailRequest;
import com.ronipereira.GetAllCustomerDetailResponse;
import com.ronipereira.GetCustomerDetailRequest;
import com.ronipereira.GetCustomerDetailResponse;
import com.ronipereira.InsertCustomerRequest;
import com.ronipereira.InsertCustomerResponse;
import com.ronipereira.soap.webservices.customersadministration.bean.Customer;
import com.ronipereira.soap.webservices.customersadministration.service.CustomerDetailService;
import com.ronipereira.soap.webservices.customersadministration.soap.exception.CustomerNotFoundException;



@Endpoint
public class CustomerDetailEndPoint {
	
	@Autowired
	CustomerDetailService service;
	
	@PayloadRoot(namespace="http://ronipereira.com", localPart="GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) throws Exception {
		Customer customer = service.findById(req.getId());
		if(customer == null) {
			throw new CustomerNotFoundException("Invalid Customer id "+req.getId());
		}
		return convertToGetCustomerDetailResponse(customer);
	}
	
	private GetCustomerDetailResponse convertToGetCustomerDetailResponse(Customer customer) {
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertToCustomerDetail(customer));
		return resp;
	}
	
	private CustomerDetail convertToCustomerDetail(Customer customer) {
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		customerDetail.setEmail(customer.getEmail());
		return customerDetail;
	}
	
	@PayloadRoot(namespace="http://ronipereira.com", localPart="GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(@RequestPayload GetAllCustomerDetailRequest req) {
		List<Customer> customers = service.findAll();
		return convertToGetAllCustomerDetailResponse(customers);
	}
	
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers) {
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
		return resp;
	}
	
	@PayloadRoot(namespace="http://ronipereira.com", localPart="DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest req) {
		DeleteCustomerResponse resp = new DeleteCustomerResponse();
		resp.setStatus(convertStatusSoap(service.deleteById(req.getId())));
		return resp;
	}
	
	private com.ronipereira.Status convertStatusSoap(
			com.ronipereira.soap.webservices.customersadministration.helper.Status statusService) {
		if(statusService == com.ronipereira.soap.webservices.customersadministration.helper.Status.FAILURE) {
			return com.ronipereira.Status.FAILURE;
		}
		return com.ronipereira.Status.SUCCESS;
	}
	
	@PayloadRoot(namespace="http://ronipereira.com", localPart="InsertCustomerRequest")
	@ResponsePayload
	public InsertCustomerResponse insertCustomerRequest(@RequestPayload InsertCustomerRequest req) {
		InsertCustomerResponse resp = new InsertCustomerResponse();
		resp.setStatus(convertStatusSoap(service.insert(convertCustomer(req.getCustomerDetail()))));
		return resp;
	}
	
	private Customer convertCustomer(CustomerDetail customerDetail) {
		return new Customer(customerDetail.getId(),customerDetail.getName(),customerDetail.getPhone(),customerDetail.getEmail());
	}
	
}

