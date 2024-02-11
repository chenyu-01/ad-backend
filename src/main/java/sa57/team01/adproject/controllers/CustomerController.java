package sa57.team01.adproject.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa57.team01.adproject.DTO.CustomerDTO;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.services.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String ,String> credentials, HttpSession session){
        if (session.getAttribute("customer")!=null){
            Map<String, Object> response = Map.of(
                    "msg", "already log in ",
                    "status", HttpStatus.CONFLICT
            );
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }
        String email=credentials.get("email");
        String password = credentials.get("password");
        Customer customer= customerService.findByEmail(email);
        if(customer == null ||!customer.getPassword().equals(password)){
            Map<String, Object> response = Map.of(
                    "msg", "login failed",
                    "status", HttpStatus.INTERNAL_SERVER_ERROR
            );
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        long id=customer.getCustomerId();
        session.setAttribute("customerId", id);


        Map<String, Object> response = Map.of(
                "msg", "login ok",
                "status", HttpStatus.OK
        );
        return new ResponseEntity<>(response,HttpStatus.OK); // return 200 OK
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(HttpSession session){

        if(session.getAttribute("customerId")==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        long id=(long)session.getAttribute("customerId");
        return ResponseEntity.ok(id);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok().build();

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerDTO customerDTO){
        Customer existingCustomer=customerService.findByEmail(customerDTO.getEmail());
        if(existingCustomer!=null){

            Map<String, Object> response = Map.of(
                    "msg", "Email already exists",
                     "status", HttpStatus.CONFLICT
            );
            return new ResponseEntity<>(response,HttpStatus.CONFLICT);
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setContactNumber(customerDTO.getContactNumber());
        customer.setPassword(customerDTO.getPassword());
        customer.setRole(customerDTO.getRole());
        customerService.save(customer);

        Map<String, Object> response = Map.of(
                "msg", "Registration ok",
                "status", HttpStatus.OK
        );
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
