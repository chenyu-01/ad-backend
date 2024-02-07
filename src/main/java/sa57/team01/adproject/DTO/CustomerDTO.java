package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Customer;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDTO implements Serializable {

  private String name;
  private String email;
  private String password;
  private String contactNumber;
  private String role;
  public CustomerDTO(){}
  public CustomerDTO(String name, String email, String password, String contactNumber,String role, String preferences){
      this.name=name;
      this.email = email;
      this.password=password;
      this.contactNumber=contactNumber;
      this.role=role;
  }
}
