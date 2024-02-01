package sa57.team01.adproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static sa57.team01.adproject.models.PropertyStatus.forRent;
import static sa57.team01.adproject.models.PropertyStatus.forSale;

@SpringBootApplication
public class AdprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdprojectApplication.class, args);
    }
    @Bean
    CommandLineRunner initDatabase(BuyerReposity buyerReposity, OwnerReposity ownerReposity,
                                   PreferencesReposity preferencesReposity,
                                   RentalPropertyReposity rentalPropertyReposity,
                                   RentalSeekerReposity rentalSeekerReposity, SalePropertyReposity salePropertyReposity,
                                   AppointmentReposity appointmentReposity) {
        return args -> {

            // clean database
            buyerReposity.deleteAll();
            ownerReposity.deleteAll();
            preferencesReposity.deleteAll();
            rentalPropertyReposity.deleteAll();
            rentalSeekerReposity.deleteAll();
            salePropertyReposity.deleteAll();
            appointmentReposity.deleteAll();




            // add preference
            Preferences preferencesBuyer = preferencesReposity.save(new Preferences(true, true, true, true, "q", 10, true, true, true));
            Preferences preferencesOwner = preferencesReposity.save(new Preferences(true, true, true, true, "q", 10, true, true, true));
            // add buyer
            Buyer buyer = new Buyer();
            buyer.setName("buyer");
            buyer.setEmail("buyer@qq.com");
            buyer.setPassword("123");
            buyer.setContactNumber("123");
            buyer.setPreferences(preferencesBuyer);

            buyerReposity.save(buyer);

            // add owner
            Owner owner = new Owner();
            owner.setName("owner");
            owner.setEmail("owner@qq.com");
            owner.setPassword("234");
            owner.setContactNumber("234");
            owner.setPreferences(preferencesOwner);
            ownerReposity.save(owner);
            //add rentalproperty
            RentalProperty rentalProperty = new RentalProperty();
            rentalProperty.setTown("redhill");
            rentalProperty.setPropertyStatus(forRent);
            rentalProperty.setFlatType(1);
            rentalProperty.setStoreyRange("?");
            rentalProperty.setStreetName("?");
            rentalProperty.setFlatType(1);
            rentalProperty.setRentalPrice(1400);
            rentalProperty.setOwner(owner);
            rentalProperty.setContractMonthPeriod(1);
            rentalPropertyReposity.save(rentalProperty);

            //add saleproperty
            SaleProperty saleProperty = new SaleProperty();
            saleProperty.setTown("redhill");
            saleProperty.setPropertyStatus(forSale);
            saleProperty.setFlatType(1);
            saleProperty.setStoreyRange("?");
            saleProperty.setStreetName("?");
            saleProperty.setFlatType(1);
            saleProperty.setLeaseCommenceDate(LocalDate.parse("2024-01-27"));
            saleProperty.setRemainingLease(1);
            saleProperty.setOwner(owner);
            saleProperty.setResalePrice(1400);
            salePropertyReposity.save(saleProperty);

            // update owner properties
            List<Property> listProperty = new ArrayList<>(List.of(rentalProperty, saleProperty));
            owner.setProperties(listProperty);
            ownerReposity.save(owner);


            //add appointment
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(1);
            appointment.setDate(dateFormat.parse("2024-0-27"));
            appointment.setContactCustomer(owner);
            appointment.setProperty(rentalProperty);
            appointment.setRequestCustomer(buyer);
            appointmentReposity.save(appointment);



        };
    }
}
