package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.repositories.CustomerReposity;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    public CustomerReposity customerReposity;


    @Override
    public Customer findByEmail(String email) {
        return customerReposity.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerReposity.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerReposity.findById(id).orElse(null);
    }
}
