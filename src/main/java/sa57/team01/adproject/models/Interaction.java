package sa57.team01.adproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long propertyId;
    @NotNull
    private long times;

    public Interaction(Long userId, Long propertyId, long times) {
        this.userId = userId;
        this.propertyId = propertyId;
        this.times = times;
    }
    public Interaction() {
    }
}
