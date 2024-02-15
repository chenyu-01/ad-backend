package sa57.team01.adproject.config;

import com.opencsv.CSVReaderHeaderAware;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.*;
import sa57.team01.adproject.services.PropertyService;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sa57.team01.adproject.models.PropertyStatus.forSale;

@Configuration
public class DataLoader {

    private final ResourceLoader resourceLoader;

    public DataLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    CommandLineRunner commandLineRunner(OwnerReposity ownerReposity,
                                        BuyerReposity buyerReposity,
                                        PreferencesReposity preferencesReposity,
                                        RentalPropertyReposity rentalPropertyReposity,
                                        AppointmentReposity appointmentReposity,
                                        SalePropertyReposity salePropertyReposity,
                                        RentalSeekerReposity rentalSeekerReposity,
                                        PropertyService propertyService,
                                        SalePropertyReposity salePropertyRepository) {
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
            List<Customer> listCustomer = new ArrayList<>();
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
            listCustomer.add(buyer);
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
            List<Owner> listOwner = new ArrayList<>();
            listOwner.add(owner);

            for (int i = 0; i < 10; i++) {
                Owner ownerRandom = new Owner();
                Buyer buyerRandom = new Buyer();
                RentalSeeker rentalSeeker = new RentalSeeker();
                ownerRandom.randomize();
                buyerRandom.randomize();
                rentalSeeker.randomize();
                rentalSeekerReposity.save(rentalSeeker);
                buyerReposity.save(buyerRandom);
                ownerReposity.save(ownerRandom);
                listOwner.add(ownerRandom);
                listCustomer.add(buyerRandom);
                listCustomer.add(rentalSeeker);
            }

            // Open the CSV
            Resource resource = resourceLoader.getResource("classpath:static/property.csv");
            if (!resource.exists()) {
                System.err.println("File not found: property.csv");
                return;
            }
            try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
                CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(reader);

                Map<String, String> valueMap;
                while ((valueMap = csvReader.readMap()) != null) {
                    SaleProperty saleProperty = new SaleProperty();
                    saleProperty.randomize();
                    saleProperty.setOwner(owner);
                    // Manually map and convert each CSV column to the corresponding SaleProperty field
                    TownName town = TownName.getByName(valueMap.get("town"));
                    saleProperty.setTown(town);
                    saleProperty.setFlatType(FlatType.getByName(valueMap.get("flat_type")));
                    saleProperty.setFloorArea(Integer.parseInt(valueMap.get("floor_area_sqm")));
                    saleProperty.setPrice(Double.parseDouble(valueMap.get("resale_price")));
                    listProperty.add(salePropertyReposity.save(saleProperty));
                }
            }

            // update owner properties
            owner.setProperties(listProperty);
            List<Appointment> listAppointment = new ArrayList<>();


            //add appointment
            for (int i = 0; i < listProperty.size(); i++) {
                Appointment appointment = new Appointment();
                appointment.setDate(LocalDate.now().plusDays(i));
                appointment.setContactCustomer(owner);
                appointment.setProperty(listProperty.get(i)); // avoid conflict
                appointment.setRequestCustomer(buyer);
                listAppointment.add(appointmentReposity.save(appointment));
            }
            owner.setReceivedAppointments(listAppointment);
            buyer.setAppointmentRequestList(listAppointment);
            ownerReposity.save(owner);
            buyerReposity.save(buyer);
            // add rental property to random owner
            for (int i = 0; i < listOwner.size(); i++) {
                Owner randomOwner = listOwner.get(i);
                int randomPropertyCount = (int) (Math.random() * 30);
                List<Property> rentalProperties = new ArrayList<>();
                for (int j = 0; j < randomPropertyCount; j++) {
                    RentalProperty rentalProperty = new RentalProperty();
                    rentalProperty.randomize();
                    rentalProperty.setOwner(randomOwner);
                    rentalProperties.add(rentalPropertyReposity.save(rentalProperty));
                }
                randomOwner.setProperties(rentalProperties);
                ownerReposity.save(randomOwner);
            }
        };
    }
}
