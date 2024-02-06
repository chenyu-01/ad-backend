package sa57.team01.adproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sa57.team01.adproject.models.Property;

import java.util.List;

@Repository
public interface PropertyReposity extends JpaRepository<Property,Long> {
//   @Query("Select p from Property p where p.datePosted = :date")
//    List<Property> findByDate(Date date);
   @Query("Select p from Property p where p.price > :low and p.price < :high")
    List<Property> findByPriceRange(double low, double high);

   @Query("Select p from Property p where p.town = :town ")
   List<Property> findBytown(String town);
    @Query("Select p from Property p where p.streetName = :street ")
   List<Property>findByStreet(String street);
}
