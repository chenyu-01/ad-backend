package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private String name;
    private String password;
    private String email;
    private String contactNumber;
    public ProfileDTO(){}
    public ProfileDTO(String name,String password,String email, String contactNumber){
        this.name = name;
        this.password = password;
        this.email = email;
        this.contactNumber = contactNumber;
    }
}
