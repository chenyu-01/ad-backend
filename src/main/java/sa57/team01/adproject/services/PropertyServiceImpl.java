package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.DTO.SearchDTO;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.repositories.PropertyReposity;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyReposity propertyReposity;

    public List<Property> getAllPropertiesWithSearchDTO(SearchDTO searchDTO) {

        Boolean room1, room2, room3, room4;
        Boolean priceCondition = searchDTO.getLowPrice() != 0 && searchDTO.getHighPrice() != 0;
        Boolean townCondition = searchDTO.getTown() != null;
        Boolean flatTypeCondition = searchDTO.isRoomOne() || searchDTO.isRoomTwo() || searchDTO.isRoomThree() || searchDTO.isRoomFour();

        room1 = searchDTO.isRoomOne();
        room2 = searchDTO.isRoomTwo();
        room3 = searchDTO.isRoomThree();
        room4 = searchDTO.isRoomFour();
        if (!priceCondition && !townCondition && !flatTypeCondition) {
            throw new IllegalArgumentException("At least one search condition must be set");
        }

        if (searchDTO.getLowPrice() > searchDTO.getHighPrice()) {
            throw new IllegalArgumentException("Low price cannot be higher than high price");
        }
        // get all properties
        List<Property> all = propertyReposity.findAll();
        // filter by searchDTO
        // if lowPrice and highPrice are not set, then don't filter by price, else filter by price. initial lowPrice and highPrice are 0
        if (priceCondition) {
            all = filterByPrice(all, searchDTO.getLowPrice(), searchDTO.getHighPrice());
        }
        // if town is not set, then don't filter by town
        if (townCondition) {
            all = filterByTown(all, searchDTO.getTown());
        }
        // if flatType is not set, then don't filter by flatType
        if (flatTypeCondition) {
            all = filterByFlatType(all, room1, room2, room3, room4);
        }
        return all;
    }

    @Override
    public Property findById(Long id) {
        return propertyReposity.findById(id).orElse(null);
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyReposity.findById(id);
    }

    private List<Property> filterByPrice(List<Property> properties, double lowPrice, double highPrice) {
        return properties.stream().filter(property -> property.getPrice() >= lowPrice && property.getPrice() <= highPrice).toList();
    }

    private List<Property> filterByTown(List<Property> properties, String searchTown) {
        return properties.stream()
                .filter(property -> {
                    // convert to lower case, trim the non-alphabetic characters
                    String town = property.getTown().toString().toLowerCase().replaceAll("[^a-z]", ""); // remove non-alphabetic characters
                    String search = searchTown.toLowerCase().replaceAll("[^a-z]", ""); // remove non-alphabetic characters
                    // check if the town contains the searchDTO town
                    return town.contains(search);
                }).toList();
    }

    private List<Property> filterByFlatType(List<Property> properties, boolean room1, boolean room2, boolean room3, boolean room4) {
        return properties.stream()
                .filter(property -> {
                    if (room1 && property.getFlatType() == 1) return true;
                    if (room2 && property.getFlatType() == 2) return true;
                    if (room3 && property.getFlatType() == 3) return true;
                    if (room4 && property.getFlatType() == 4) return true;
                    return false;
                }).toList();
    }


    public Property saveProperty(Property property) {
        return propertyReposity.save(property);
    }

    public void deleteProperty(Long id) {
        propertyReposity.deleteById(id);
    }


}
