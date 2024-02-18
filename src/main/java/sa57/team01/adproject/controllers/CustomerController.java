package sa57.team01.adproject.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa57.team01.adproject.DTO.CustomerDTO;
import sa57.team01.adproject.models.*;
import sa57.team01.adproject.repositories.BuyerReposity;
import sa57.team01.adproject.repositories.OwnerReposity;
import sa57.team01.adproject.repositories.RentalSeekerReposity;
import sa57.team01.adproject.services.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    public CustomerService customerService;
    @Autowired
    OwnerReposity ownerReposity;
    @Autowired
    BuyerReposity buyerReposity;
    @Autowired
    RentalSeekerReposity rentalSeekerReposity;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        if (session.getAttribute("customer") != null) {
            Map<String, Object> response = Map.of(
                    "msg", "already log in ",
                    "status", HttpStatus.CONFLICT
            );
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        String email = credentials.get("email");
        String password = credentials.get("password");
        Customer customer = customerService.findByEmail(email);
        if (customer == null || !customer.getPassword().equals(password)) {
            Map<String, Object> response = Map.of(
                    "msg", "login failed",
                    "status", HttpStatus.INTERNAL_SERVER_ERROR
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long id = customer.getCustomerId();
        session.setAttribute("customerId", id);
        String role = customer.getRole();
        Map<String, Object> response = Map.of(
                "msg", "login ok",
                "status", HttpStatus.OK,
                "sessionID",session.getId(),
                "role", role,
                "id", id
        );
        return new ResponseEntity<>(response, HttpStatus.OK); // return 200 OK
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(HttpSession session) {

        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        long customerId = (long) session.getAttribute("customerId");
        Customer customer = customerService.findById(customerId);
        return ResponseEntity.ok(new CustomerDTO(customer));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> credentials, HttpSession session) {
        Customer existingCustomer = customerService.findByEmail(credentials.get("email"));
        if (existingCustomer != null) {
            Map<String, Object> response = Map.of("msg", "Email already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        if(credentials.get("role").equals("owner")){
            Owner owner = new Owner();
            owner.setName(credentials.get("name"));
            owner.setEmail(credentials.get("email"));
            owner.setContactNumber(credentials.get("contactNumber"));
            owner.setPassword(credentials.get("password"));
            owner.setRole(credentials.get("role"));
            ownerReposity.save(owner);
            session.setAttribute("customerId", owner.getCustomerId());
        }
        if(credentials.get("role").equals("buyer")){
            Buyer buyer = new Buyer();
            buyer.setName(credentials.get("name"));
            buyer.setEmail(credentials.get("email"));
            buyer.setContactNumber(credentials.get("contactNumber"));
            buyer.setPassword(credentials.get("password"));
            buyer.setRole(credentials.get("role"));
            buyerReposity.save(buyer);
            session.setAttribute("customerId", buyer.getCustomerId());
        }
        if(credentials.get("role").equals("rentalSeeker")){
            RentalSeeker rentalSeeker = new RentalSeeker();
            rentalSeeker.setName(credentials.get("name"));
            rentalSeeker.setEmail(credentials.get("email"));
            rentalSeeker.setContactNumber(credentials.get("contactNumber"));
            rentalSeeker.setPassword(credentials.get("password"));
            rentalSeeker.setRole(credentials.get("role"));
            rentalSeekerReposity.save(rentalSeeker);
            session.setAttribute("customerId", rentalSeeker.getCustomerId());
        }

        Map<String, Object> response = Map.of("msg", "Registration ok");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
