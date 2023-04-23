package com.ronipereira.soap.webservices.customersadministration.service;



import org.springframework.stereotype.Component;

import com.ronipereira.soap.webservices.customersadministration.bean.Customer;
import com.ronipereira.soap.webservices.customersadministration.helper.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerDetailService {

    private static List<Customer> customers = new ArrayList<>();

    static {
        Customer customer1 = new Customer(1, "Bob", "99999999", "bob@gmail.com");
        customers.add(customer1);

        Customer customer2 = new Customer(2, "Mark", "8888888", "Mark@gmail.com");
        customers.add(customer2);

        Customer customer3 = new Customer(3, "Joao", "777777777", "Joao@gmail.com");
        customers.add(customer3);

        Customer customer4 = new Customer(4, "Clark", "6666666", "Clark@gmail.com");
        customers.add(customer4);
    }

    public Customer findById(long id) {
        Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
        if (customerOptional.isPresent()) {
            return customerOptional.get();

        }
        return null;
    }

    public List<Customer> findAll() {
        return customers;
    }
    public Status deleteById(long id){
        Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
        if(customerOptional.isPresent()){
           customers.remove(customerOptional.get());
           return Status.SUCCESS;
        }
        return Status.FAILURE;
    }

	public Status insert(Customer convertCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

}
