package sa57.team01.adproject.services;

import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Customer;

@Service
public interface CustomerService {
    Customer findByEmail(String email);

    Customer save(Customer customer);
}
