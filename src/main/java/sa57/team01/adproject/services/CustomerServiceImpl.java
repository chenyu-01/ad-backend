package sa57.team01.adproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sa57.team01.adproject.DTO.*;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Value("${upload.path}") // Define this property in your application.properties
    private String uploadDir;

    private int ifileid;

    public CustomerReposity customerReposity;

    public PreferencesReposity preferencesReposity;

    public OwnerReposity ownerReposity;

    public PropertyReposity propertyReposity;

    public SalePropertyReposity salePropertyReposity;

    public RentalPropertyReposity rentalPropertyReposity;

    public BuyerReposity buyerReposity;

    public RentalSeekerReposity rentalSeekerReposity;

    public AppointmentReposity appointmentReposity;

    @Autowired
    public CustomerServiceImpl(CustomerReposity customerReposity, PreferencesReposity preferencesReposity,
                               OwnerReposity ownerReposity, PropertyReposity propertyReposity,
                               SalePropertyReposity salePropertyReposity, RentalPropertyReposity rentalPropertyReposity,
                               BuyerReposity buyerReposity,RentalSeekerReposity rentalSeekerReposity,
                               AppointmentReposity appointmentReposity) {
        this.customerReposity = customerReposity;
        this.preferencesReposity = preferencesReposity;
        this.ownerReposity = ownerReposity;
        this.propertyReposity = propertyReposity;
        this.salePropertyReposity = salePropertyReposity;
        this.rentalPropertyReposity = rentalPropertyReposity;
        this.buyerReposity = buyerReposity;
        this.rentalSeekerReposity = rentalSeekerReposity;
        this.appointmentReposity = appointmentReposity;
    }


    @Override
    public Customer findByEmail(String email) {
        return customerReposity.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return customerReposity.save(customer);
    }

    @Override
    public ResponseEntity<ProfileDTO> getProfile(long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Customer> optCustomer = customerReposity.findById(id);
        if (optCustomer.isEmpty()) {
            response.put("message", "Customer Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer = optCustomer.get();
        ProfileDTO profileDTO = new ProfileDTO(customer.getName(), customer.getPassword(),
                customer.getEmail(), customer.getContactNumber());
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);


    }

    @Override
    public ResponseEntity<?> saveProfile(long id, ProfileDTO profileDTO, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> optCustomer = customerReposity.findById(id);
        if (optCustomer.isEmpty()) {
            response.put("message", "Customer Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Customer customer = optCustomer.get();
        customer.setName(profileDTO.getName());
        customer.setPassword(profileDTO.getPassword());
        customer.setEmail(profileDTO.getEmail());
        customer.setContactNumber(profileDTO.getContactNumber());
        customerReposity.save(customer);
        response.put("message", "Successfully Updated Profile");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPreferences(long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Customer> optCustomer = customerReposity.findById(id);
        if (optCustomer.isEmpty()) {
            response.put("message", "Customer Not Found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer customer = optCustomer.get();
        Preferences preferences = customer.getPreferences();
        PreferencesDTO preferencesDTO = new PreferencesDTO(preferences.isBedroom1(), preferences.isBedroom2(),
                preferences.isBedroom3(), preferences.isBedroom4(), preferences.getTown(), preferences.getStoryRange(),
                preferences.isLowPriceRange(), preferences.isMidPriceRange(), preferences.isHighPriceRange());
        return new ResponseEntity<>(preferencesDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> savePreferences(long id, PreferencesDTO preferencesDTO, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> optCustomer = customerReposity.findById(id);
        if (optCustomer.isEmpty()) {
            response.put("message", "Customer Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Customer customer = optCustomer.get();
        Preferences preferences = customer.getPreferences();
        preferences.setBedroom1(preferencesDTO.isBedroom1());
        preferences.setBedroom2(preferencesDTO.isBedroom2());
        preferences.setBedroom3(preferencesDTO.isBedroom3());
        preferences.setBedroom4(preferencesDTO.isBedroom4());
        preferences.setTown(preferencesDTO.getTown());
        preferences.setStoryRange(preferencesDTO.getStoryRange());
        preferences.setLowPriceRange(preferencesDTO.isLowPriceRange());
        preferences.setMidPriceRange(preferencesDTO.isMidPriceRange());
        preferences.setHighPriceRange(preferencesDTO.isHighPriceRange());
        preferencesReposity.save(preferences);
        response.put("message", "Successfully Updated Preferences");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> saveProperty(long id, MixPropertyDTO mixPropertyDTO, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<Owner> optOwner = ownerReposity.findById(id);
        if (optOwner.isEmpty()) {
            response.put("message", "Owner Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        Long propertyId = mixPropertyDTO.getId();
        PropertyStatus currentPropertyStatus = PropertyStatus.valueOf(mixPropertyDTO.getPropertyStatus());
        if(propertyId == null || propertyId == 0){
            if(currentPropertyStatus.equals(PropertyStatus.forSale) || currentPropertyStatus.equals(PropertyStatus.soldOut)){
                SaleProperty saleProperty = new SaleProperty();
                salePropertyReposity.save(DTOtoSaleProperty(id,saleProperty,mixPropertyDTO));
                response.put("message", "Successfully Updated Property");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }else if(currentPropertyStatus.equals(PropertyStatus.forRent) || currentPropertyStatus.equals(PropertyStatus.rented)){
                RentalProperty rentalProperty = new RentalProperty();
                rentalPropertyReposity.save(DTOtoRentalProperty(id,rentalProperty,mixPropertyDTO));
                response.put("message", "Successfully Updated Property");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                response.put("message","Current Status Null");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        }
        Optional<Property> optProperty = propertyReposity.findById(mixPropertyDTO.getId());

        PropertyStatus prePropertyStatus = optProperty.get().getPropertyStatus();
        if (prePropertyStatus.equals(PropertyStatus.forSale) || prePropertyStatus.equals(PropertyStatus.soldOut)) {
            if (currentPropertyStatus.equals(PropertyStatus.forSale) || currentPropertyStatus.equals(PropertyStatus.soldOut)) {
                SaleProperty saleProperty = salePropertyReposity.findById(mixPropertyDTO.getId()).get();
                salePropertyReposity.save(DTOtoSaleProperty(id,saleProperty,mixPropertyDTO));

            } else if (currentPropertyStatus.equals(PropertyStatus.forRent) || currentPropertyStatus.equals(PropertyStatus.rented)) {
                RentalProperty rentalProperty = new RentalProperty();
                SaleProperty saleProperty = salePropertyReposity.findById(mixPropertyDTO.getId()).get();
                rentalProperty.setPropertyid(saleProperty.getPropertyid());
                salePropertyReposity.delete(saleProperty);
                rentalPropertyReposity.save(DTOtoRentalProperty(id,rentalProperty,mixPropertyDTO));
            } else {
                response.put("message","Current Status Null");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

            }

        } else if (prePropertyStatus.equals(PropertyStatus.forRent) || prePropertyStatus.equals(PropertyStatus.rented)) {
            if (currentPropertyStatus.equals(PropertyStatus.forRent) || currentPropertyStatus.equals(PropertyStatus.rented)) {
                RentalProperty rentalProperty = rentalPropertyReposity.findById(mixPropertyDTO.getId()).get();
                rentalPropertyReposity.save(DTOtoRentalProperty(id,rentalProperty,mixPropertyDTO));
            } else if (currentPropertyStatus.equals(PropertyStatus.forSale) || currentPropertyStatus.equals(PropertyStatus.soldOut)) {
                SaleProperty saleProperty = new SaleProperty();
                RentalProperty rentalProperty = rentalPropertyReposity.findById(mixPropertyDTO.getId()).get();
                saleProperty.setPropertyid(saleProperty.getPropertyid());
                rentalPropertyReposity.delete(rentalProperty);
                salePropertyReposity.save(DTOtoSaleProperty(id,saleProperty,mixPropertyDTO));
            } else {
                response.put("message","Current Status Null");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
        }


        response.put("message", "Successfully Updated Property");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public SaleProperty DTOtoSaleProperty(long id,SaleProperty saleProperty,MixPropertyDTO mixPropertyDTO){
        saleProperty.setTown(TownName.valueOf(mixPropertyDTO.getTown()));
        saleProperty.setPropertyStatus(PropertyStatus.valueOf(mixPropertyDTO.getPropertyStatus()));
        saleProperty.setFlatType(FlatType.valueOf((mixPropertyDTO.getFlatType())));
        saleProperty.setStoreyRange(mixPropertyDTO.getStoreyRange());
        saleProperty.setStreetName(mixPropertyDTO.getStreetName());
        saleProperty.setFloorArea(Integer.parseInt(mixPropertyDTO.getFloorArea()));
        saleProperty.setBlock(mixPropertyDTO.getBlock());
        saleProperty.setOwner(ownerReposity.findById(id).get());
        saleProperty.setPrice(Double.parseDouble(mixPropertyDTO.getPrice()));
        saleProperty.setLeaseCommenceDate(LocalDate.parse(mixPropertyDTO.getLeaseCommenceDate()));
        saleProperty.setImageUrl(mixPropertyDTO.getImageUrl());
        saleProperty.setFlatModel(FlatModel.valueOf(mixPropertyDTO.getFlatModel()));
        return saleProperty;
    }

    public RentalProperty DTOtoRentalProperty(long id,RentalProperty rentalProperty,MixPropertyDTO mixPropertyDTO){
        rentalProperty.setTown(TownName.valueOf(mixPropertyDTO.getTown()));
        rentalProperty.setPropertyStatus(PropertyStatus.valueOf(mixPropertyDTO.getPropertyStatus()));
        rentalProperty.setFlatType(FlatType.valueOf((mixPropertyDTO.getFlatType())));
        rentalProperty.setStoreyRange(mixPropertyDTO.getStoreyRange());
        rentalProperty.setStreetName(mixPropertyDTO.getStreetName());
        rentalProperty.setFloorArea(Integer.parseInt(mixPropertyDTO.getFloorArea()));
        rentalProperty.setBlock(mixPropertyDTO.getBlock());
        rentalProperty.setOwner(ownerReposity.findById(id).get());
        rentalProperty.setPrice(Double.parseDouble(mixPropertyDTO.getPrice()));
        rentalProperty.setContractMonthPeriod(Integer.parseInt(mixPropertyDTO.getContractMonthPeriod()));
        rentalProperty.setImageUrl(mixPropertyDTO.getImageUrl());
        rentalProperty.setFlatModel(FlatModel.valueOf(mixPropertyDTO.getFlatModel()));
        return rentalProperty;
    }

    @Override
    public ResponseEntity<?> getPropertyLists(long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Owner> optOwner = ownerReposity.findById(id);
        if (optOwner.isEmpty()) {
            response.put("message", "Customer Not Found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        Owner owner = optOwner.get();
        List<Property> propertyList = owner.getProperties();
        List<MixPropertyDTO> mixPropertyDTOS = new ArrayList<>();
        for (Property property : propertyList) {
            MixPropertyDTO mixPropertyDTO = propertyConvertToDTO(property);
            mixPropertyDTOS.add(mixPropertyDTO);
        }
        return new ResponseEntity<>(mixPropertyDTOS, HttpStatus.OK);
    }

    public MixPropertyDTO propertyConvertToDTO(Property property){
        MixPropertyDTO mixPropertyDTO = new MixPropertyDTO();
        mixPropertyDTO.setId(property.getPropertyid());
        mixPropertyDTO.setStoreyRange(property.getStoreyRange());
        mixPropertyDTO.setStreetName(property.getStreetName());
        mixPropertyDTO.setFloorArea(String.valueOf(property.getFloorArea()));
        mixPropertyDTO.setBlock(property.getBlock());
        mixPropertyDTO.setPropertyStatus(String.valueOf(property.getPropertyStatus()));
        mixPropertyDTO.setFlatType(String.valueOf(property.getFlatType()));
        mixPropertyDTO.setPrice(String.valueOf(property.getPrice()));
        mixPropertyDTO.setTown(String.valueOf(property.getTown()));
        mixPropertyDTO.setOwnerid(property.getOwner().getCustomerId());
        mixPropertyDTO.setImageUrl(property.getImageUrl());
        mixPropertyDTO.setFlatModel(String.valueOf(property.getFlatModel()));
        PropertyStatus propertyStatus = property.getPropertyStatus();
        if(propertyStatus.equals(PropertyStatus.forSale) || propertyStatus.equals(PropertyStatus.soldOut)){
            SaleProperty saleProperty = salePropertyReposity.findById(property.getPropertyid()).get();
            mixPropertyDTO.setLeaseCommenceDate(String.valueOf(saleProperty.getLeaseCommenceDate()));
            return mixPropertyDTO;
        }
        RentalProperty rentalProperty = rentalPropertyReposity.findById(property.getPropertyid()).get();
        mixPropertyDTO.setContractMonthPeriod(String.valueOf(rentalProperty.getContractMonthPeriod()));
        return mixPropertyDTO;
    }

    @Override
    public ResponseEntity<?> saveByRole(Map<String, String> credentials) {
        String role = credentials.get("role");
        String name = credentials.get("name");
        String email = credentials.get("email");
        String password = credentials.get("password");
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setRole(role);
        customerReposity.save(newCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getProperty(long id,String status){
        if(status.equals("forSale") || status.equals("soldOut")){
            SaleProperty saleProperty =  salePropertyReposity.findById(id).get();
            MixPropertyDTO mixPropertyDTO = new MixPropertyDTO();
            mixPropertyDTO.setId(saleProperty.getPropertyid());
            mixPropertyDTO.setTown(String.valueOf(saleProperty.getTown()));
            mixPropertyDTO.setPropertyStatus(status);
            mixPropertyDTO.setFlatType(String.valueOf(saleProperty.getFlatType()));
            mixPropertyDTO.setStoreyRange(saleProperty.getStoreyRange());
            mixPropertyDTO.setStreetName(saleProperty.getStreetName());
            mixPropertyDTO.setFloorArea(String.valueOf(saleProperty.getFloorArea()));
            mixPropertyDTO.setOwnerid(saleProperty.getOwner().getCustomerId());
            mixPropertyDTO.setPrice(String.valueOf(saleProperty.getPrice()));
            mixPropertyDTO.setBlock(saleProperty.getBlock());
            mixPropertyDTO.setLeaseCommenceDate(String.valueOf(saleProperty.getLeaseCommenceDate()));
            mixPropertyDTO.setImageUrl(saleProperty.getImageUrl());
            mixPropertyDTO.setFlatModel(String.valueOf(saleProperty.getFlatModel()));
            return new ResponseEntity<>(mixPropertyDTO,HttpStatus.OK);

        }
        RentalProperty rentalProperty =  rentalPropertyReposity.findById(id).get();
        MixPropertyDTO mixPropertyDTO = new MixPropertyDTO();
        mixPropertyDTO.setId(rentalProperty.getPropertyid());
        mixPropertyDTO.setTown(String.valueOf(rentalProperty.getTown()));
        mixPropertyDTO.setPropertyStatus(status);
        mixPropertyDTO.setFlatType(String.valueOf(rentalProperty.getFlatType()));
        mixPropertyDTO.setStoreyRange(rentalProperty.getStoreyRange());
        mixPropertyDTO.setStreetName(rentalProperty.getStreetName());
        mixPropertyDTO.setFloorArea(String.valueOf(rentalProperty.getFloorArea()));
        mixPropertyDTO.setOwnerid(rentalProperty.getOwner().getCustomerId());
        mixPropertyDTO.setPrice(String.valueOf(rentalProperty.getPrice()));
        mixPropertyDTO.setBlock(rentalProperty.getBlock());
        mixPropertyDTO.setContractMonthPeriod(String.valueOf(rentalProperty.getContractMonthPeriod()));
        mixPropertyDTO.setImageUrl(rentalProperty.getImageUrl());
        mixPropertyDTO.setBlock(String.valueOf(rentalProperty.getFlatModel()));
        return new ResponseEntity<>(mixPropertyDTO,HttpStatus.OK);

    }


    @Override
    public ResponseEntity<?> getPropertyListsDivide(long id, int page, int itemsPerPage){
        int totalPages = 0;
        Map<String, Object> response = new HashMap<>();
        Optional<Owner> optOwner = ownerReposity.findById(id);
        if (optOwner.isEmpty()) {
            response.put("message", "Customer Not Found");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        Owner owner = optOwner.get();
        List<Property> propertyList = owner.getProperties();
        List<MixPropertyDTO> mixPropertyDTOS = new ArrayList<>();
        for(int i = (page - 1)*10; i < ((page - 1)*10 + 10); i++ ){
            if(i < propertyList.size()){
                Property property = propertyList.get(i);
                mixPropertyDTOS.add( propertyConvertToDTO(property));
            }
        }
        if(propertyList.size() % itemsPerPage != 0){
            totalPages = propertyList.size() / itemsPerPage + 1;
        }else{
            totalPages = propertyList.size() / itemsPerPage;
        }
        response.put("results",mixPropertyDTOS);

        response.put("totalPages",totalPages);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public Customer findById(long customerId) {
        if (customerReposity.findById(customerId).isEmpty()) {
            throw new NoSuchElementException("Customer Not Found");
        } else {
            return customerReposity.findById(customerId).get();
        }
    }

    @Override
    public ResponseEntity<?> deleteProperty(long propertyid){
        Map<String,Object> response = new HashMap<>();
        List<Appointment> appointments = appointmentReposity.fingByPropertyId(propertyid);
        appointmentReposity.deleteAll(appointments);
        propertyReposity.deleteById(propertyid);

        response.put("message","Delete Property Successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadImage(String propertyid,MultipartFile file) {
        Map<String,Object> response = new HashMap<>();
        if(propertyid.equals("null")){
            try {
                String filename = UUID.randomUUID() + "_" + ifileid + "_" + file.getOriginalFilename();
                ifileid = ifileid + 1;
                Path path = Paths.get(uploadDir + filename);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Construct URL to access the uploaded file
                String fileAccessUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(filename)
                        .toUriString();
                response.put("imageUrl",fileAccessUrl);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }catch (IOException e){
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename());
            }
        }
        Property property = propertyReposity.findById(Long.valueOf(propertyid)).get();
        System.out.println(property.getImageUrl().lastIndexOf("/"));
        String filename = property.getImageUrl().substring(property.getImageUrl().lastIndexOf("/") + 1);
        Path path = Paths.get(uploadDir + filename);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            filename = UUID.randomUUID() + "_" + ifileid + "_" + file.getOriginalFilename();
            ifileid = ifileid + 1;
            path = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Construct URL to access the uploaded file
            String fileAccessUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
            response.put("imageUrl",fileAccessUrl);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename());
        }
    }

    @Override
    public ResponseEntity<?> fetchImg(long propertyId){
        Map<String,Object> response = new HashMap<>();
        Property property = propertyReposity.findById(propertyId).get();
        response.put("imageUrl",property.getImageUrl());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}


