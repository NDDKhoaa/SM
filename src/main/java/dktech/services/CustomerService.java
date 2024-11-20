package dktech.services;

import dktech.entity.Customer;
import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer Customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(long id);
    void deleteCustomer(long id);
}
