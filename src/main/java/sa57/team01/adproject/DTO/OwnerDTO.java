package sa57.team01.adproject.DTO;

import lombok.Getter;
import lombok.Setter;
import sa57.team01.adproject.models.Owner;

@Getter
@Setter
public class OwnerDTO {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;

    public OwnerDTO(Owner owner){
        this.id = owner.getCustomerId();
        this.name = owner.getName();
        this.email = owner.getEmail();
        this.contactNumber = owner.getContactNumber();
    }
}
