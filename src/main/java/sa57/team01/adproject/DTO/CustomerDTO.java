package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Customer;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDTO implements Serializable {

  private String name;
  private String password;
  private String email;
  private String role;
  public CustomerDTO(){}
  public CustomerDTO(String contactNumber,String email, String name, String password, String preferences,String role){

      this.name=name;
      this.password=password;
      this.email = email;
      this.role=role;
  }
}
