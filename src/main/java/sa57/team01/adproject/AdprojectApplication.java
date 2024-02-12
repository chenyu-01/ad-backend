package sa57.team01.adproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.*;
import sa57.team01.adproject.services.PropertyService;

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
                                   AppointmentReposity appointmentReposity, PropertyService propertyService) {
        return args -> {

            // clean database
            buyerReposity.deleteAll();
            ownerReposity.deleteAll();
            preferencesReposity.deleteAll();
            rentalPropertyReposity.deleteAll();
            rentalSeekerReposity.deleteAll();
            salePropertyReposity.deleteAll();
            appointmentReposity.deleteAll();
            propertyService.deleteAll();




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
            buyer.setRole("buyer");
            buyerReposity.save(buyer);

            // add owner
            Owner owner = new Owner();
            owner.setRole("owner");
            owner.setName("BAO");
            owner.setEmail("owner@qq.com");
            owner.setPassword("234");
            owner.setContactNumber("234");
            owner.setPreferences(preferencesOwner);
            ownerReposity.save(owner);


            List<Property> listProperty = new ArrayList<>();
            //add properties
            for(int i = 0; i < 20; i++) {
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
                rentalProperty.setPrice(990 + i * 100);
                rentalProperty.setOwner(owner);
                rentalProperty.setContractMonthPeriod(1);
                rentalProperty.setForSale(false);
                rentalProperty =  rentalPropertyReposity.save(rentalProperty);
                //add saleproperty
                SaleProperty saleProperty = new SaleProperty();
                saleProperty.setTown(TownName.getRandomTown());
                saleProperty.setBlock("101");
                saleProperty.setFloorArea(100);
                saleProperty.setPropertyStatus(forSale);
                saleProperty.setFlatType(1);
                saleProperty.setStoreyRange("10 TO 12");
                saleProperty.setStreetName("ANG MO KIO AVE 10");
                saleProperty.setFlatType(i % 4 + 1);
                saleProperty.setLeaseCommenceDate(LocalDate.parse("2024-01-27"));
                saleProperty.setRemainingLease(1);
                saleProperty.setOwner(owner);
                saleProperty.setPrice(100000 + i * 11111);
                saleProperty.setForSale(true);
                saleProperty = salePropertyReposity.save(saleProperty);
                listProperty.add(rentalProperty);
                listProperty.add(saleProperty);
            }

            // update owner properties
            owner.setProperties(listProperty);
            ownerReposity.save(owner);


            List<Appointment> listAppointment = new ArrayList<>();
            //add appointment
            for (int i = 0; i < 10; i++) {
                LocalDate date = LocalDate.now().plusDays(i);
                Appointment appointment = new Appointment();
                appointment.setDate(date);
                appointment.setContactCustomer(owner);
                appointment.setProperty(listProperty.get(i));
                appointment.setRequestCustomer(buyer);
                appointment.setStatus(AppointmentStatus.pending);
                listAppointment.add(appointmentReposity.save(appointment));
            }
            owner.setReceivedAppointments(listAppointment);
            ownerReposity.save(owner);
            buyer.setAppointmentRequestList(listAppointment);
            buyerReposity.save(buyer);



        };
    }
}
