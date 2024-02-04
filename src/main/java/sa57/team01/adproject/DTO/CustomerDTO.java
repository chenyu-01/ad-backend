package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Customer;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDTO implements Serializable {
  private long id;
  private String name;
  private String password;

  private String role;

  public CustomerDTO(Customer other){
      this.id= other.getId();
      this.name=other.getName();
      this.password=other.getPassword();
      this.role=other.getRole();
  }
}
