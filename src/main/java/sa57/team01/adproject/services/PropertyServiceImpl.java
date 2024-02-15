package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sa57.team01.adproject.DTO.SearchDTO;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.repositories.PropertyReposity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Value("${upload.path}") // Define this property in your application.properties
    private String uploadDir;
    @Autowired
    private PropertyReposity propertyReposity;

    @Override
    public Property findPropertyById(Long id) {
        Optional<Property> property = propertyReposity.findById(id);
        return property.orElse(null);
    }
    @Override
    public List<Property> getAllPropertiesWithSearchDTO(SearchDTO searchDTO) {

        boolean room1, room2, room3, room4;
        boolean priceCondition = searchDTO.getLowPrice() != 0 && searchDTO.getHighPrice() != 0;
        boolean townCondition = !searchDTO.getTown().isEmpty();
        boolean flatTypeCondition = searchDTO.isRoomOne() || searchDTO.isRoomTwo() || searchDTO.isRoomThree() || searchDTO.isRoomFour();

        room1 = searchDTO.isRoomOne();
        room2 = searchDTO.isRoomTwo();
        room3 = searchDTO.isRoomThree();
        room4 = searchDTO.isRoomFour();

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
        long page = searchDTO.getPage();
        String propertyType = searchDTO.getPropertyType();
        if(propertyType.equals("rental")){
            all = all.stream().filter(property -> property instanceof RentalProperty).toList();
        } else if(propertyType.equals("sale")){
            all = all.stream().filter(property -> property instanceof SaleProperty).toList();
        }
        return all;
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


    @Override
    public Property saveProperty(Property property) {
        return propertyReposity.save(property);
    }

    @Override
    public ResponseEntity<?> uploadImage(long propertyId, MultipartFile file) {
        // if property has existing images, delete them
        Property property = findPropertyById(propertyId);
        if (property == null) {
            return ResponseEntity.notFound().build();
        }
        if (property.getImageUrl() != null) {
            // delete existing image
            String filename = property.getImageUrl().substring(property.getImageUrl().lastIndexOf("/") + 1);
            Path path = Paths.get(uploadDir + filename);
            if (path.toFile().exists()) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            // Construct path with property ID to avoid name clashes, use UUID
            String filename = UUID.randomUUID() + "_" + propertyId + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Construct URL to access the uploaded file
            String fileAccessUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
            property.setImageUrl(fileAccessUrl);
            saveProperty(property);
            return ResponseEntity.ok(fileAccessUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename());
        }
    }

    @Override
    public void deleteAll() {
        propertyReposity.deleteAll();
    }



    public void deleteProperty(Long id) {
        propertyReposity.deleteById(id);
    }



    public List<Property> sortProperties(List<Property> properties, String sortDirection) {
        if (sortDirection.equals("asc")) {
            return properties.stream()
                    .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                    .collect(Collectors.toList());
        } else if (sortDirection.equals("desc")) {
            return properties.stream()
                    .sorted((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()))
                    .collect(Collectors.toList());
        } else {
            // Handle invalid sort direction
            throw new IllegalArgumentException("Invalid sort direction: " + sortDirection);
        }
    }

    public List<Property> getAllProperties(){
        return propertyReposity.findAll();
    }


}
