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
                                   AppointmentReposity appointmentReposity,PropertyReposity propertyReposity) {
        return args -> {

            // clean database
            buyerReposity.deleteAll();
            ownerReposity.deleteAll();
            preferencesReposity.deleteAll();
            rentalPropertyReposity.deleteAll();
            rentalSeekerReposity.deleteAll();
            salePropertyReposity.deleteAll();
            appointmentReposity.deleteAll();
            propertyReposity.deleteAll();




            // add preference
            Preferences preferencesBuyer = preferencesReposity.save(new Preferences(true, true, true, true, TownName.QUEENSTOWN, 10, true, true, true));
            Preferences preferencesOwner = preferencesReposity.save(new Preferences(true, true, true, true, TownName.QUEENSTOWN, 10, true, true, true));
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
            rentalProperty.setTown(TownName.getRandomTown());
            rentalProperty.setBlock("101");
            rentalProperty.setFloorArea(100);
            rentalProperty.setPropertyStatus(forRent);
            rentalProperty.setFlatType(1);
            rentalProperty.setStoreyRange("10 TO 12");
            rentalProperty.setStreetName("BEDOK NTH ST 3");
            rentalProperty.setFlatType(1);
            rentalProperty.setPrice(1400);
            rentalProperty.setOwner(owner);
            rentalProperty.setContractMonthPeriod(1);
            rentalPropertyReposity.save(rentalProperty);

            List<Property> listProperty = new ArrayList<>();
            listProperty.add(rentalProperty);
            //add saleproperty
            for(int i = 0; i < 100; i++) {
                SaleProperty saleProperty = new SaleProperty();
                saleProperty.setTown(TownName.getRandomTown());
                saleProperty.setBlock("101");
                saleProperty.setFloorArea(100);
                saleProperty.setPropertyStatus(forSale);
                saleProperty.setFlatType(1);
                saleProperty.setStoreyRange("10 TO 12");
                saleProperty.setStreetName("ANG MO KIO AVE 10");
                saleProperty.setFlatType(1);
                saleProperty.setLeaseCommenceDate(LocalDate.parse("2024-01-27"));
                saleProperty.setRemainingLease(1);
                saleProperty.setOwner(owner);
                saleProperty.setPrice(1000 + i * 100);
                salePropertyReposity.save(saleProperty);
                listProperty.add(saleProperty);
            }

            // update owner properties
            owner.setProperties(listProperty);
            ownerReposity.save(owner);


            //add appointment
            LocalDate date = LocalDate.parse("2024-01-27");
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(1);
            appointment.setDate(date);
            appointment.setContactCustomer(owner);
            appointment.setProperty(rentalProperty);
            appointment.setRequestCustomer(buyer);
            appointmentReposity.save(appointment);


        };
    }
}
