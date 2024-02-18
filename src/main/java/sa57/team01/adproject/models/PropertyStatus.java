package sa57.team01.adproject.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum PropertyStatus {
    rented,soldOut,forRent,forSale;

    public static PropertyStatus getByName(String propertyStatus) {
        for (PropertyStatus status : PropertyStatus.values()) {
            if (status.name().equals(propertyStatus)) {
                return status;
            }
        }
        return null;
    }
}
