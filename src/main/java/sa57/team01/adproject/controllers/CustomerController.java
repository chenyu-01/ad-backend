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
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already logged in");
        }
        String email=credentials.get("email");
        String password = credentials.get("password");
        Customer customer= customerService.findByEmail(email);
        if(customer == null ||!customer.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username and Password");
        }
        long id=customer.getCustomerId();
        session.setAttribute("customerId", id);

        return ResponseEntity.ok().build();
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
    public ResponseEntity<?> register(@RequestBody Map<String, String> credentials){
        Customer existingCustomer=customerService.findByEmail(credentials.get("email"));
        if(existingCustomer!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        customerService.saveByRole(credentials);
        return ResponseEntity.ok().build();

    }



}
