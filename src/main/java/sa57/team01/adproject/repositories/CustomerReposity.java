package sa57.team01.adproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Customer;

@Repository
public interface CustomerReposity extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findByEmail(String email);


}
