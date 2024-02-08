package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Customer;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDTO implements Serializable {

  private long customerId;
  private String name;
  private String email;
  private String role;
  public CustomerDTO(){}
  public CustomerDTO(Customer customer){
        this.customerId=customer.getCustomerId();
      this.name=customer.getName();
      this.email=customer.getEmail();
        this.role=customer.getRole();
  }
}
