package dktech.services.impl;

import dktech.entity.Customer;
import dktech.repository.CustomerRepository;
import dktech.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository CustomerRepository;

    @Override
    public Customer createCustomer(Customer Customer) {
        return CustomerRepository.save(Customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return CustomerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(long id) {
        return CustomerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCustomer(long id) {
        CustomerRepository.deleteById(id);
    }
}
