package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa57.team01.adproject.models.Property;
import sa57.team01.adproject.repositories.PropertyReposity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService{

    @Autowired
    private PropertyReposity propertyReposity;

    public List<Property> getAllProperties(){
        return propertyReposity.findAll();
    }

    public Optional<Property> getPropertyById(Long id){
        return propertyReposity.findById(id);
    }

    @Override
    public List<Property> getListByDate(Date date) {
        return propertyReposity.findByDate(date);
    }

    @Override
    public List<Property> getListByPrice(double price) {
        return propertyReposity.findByPrice(price);
    }

    @Override
    public List<Property> getListByTown(String town) {
        return propertyReposity.findBytown(town);
    }

    @Override
    public List<Property> getListByStreet(String street) {
        return propertyReposity.findByStreet(street);
    }

    @Override
    public List<Property> getListByStoreyRange(String storey) {
        return propertyReposity.findByStoreyRange(storey);
    }

    @Override
    public List<Property> getListByFloorArea(int floorarea) {
        return propertyReposity.findByFloorArea(floorarea);
    }


    public Property saveProperty(Property property){
        return propertyReposity.save(property);
    }

    public void deleteProperty(Long id){
        propertyReposity.deleteById(id);
    }


}
