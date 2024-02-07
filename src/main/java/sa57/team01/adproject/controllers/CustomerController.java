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
        String email=credentials.get("email");
        String password = credentials.get("password");
        Customer customer=customerService.findByEmail(email);
        if(customer == null ||!customer.getPassword().equals(password)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Username and Password");
        }

        session.setAttribute("customer",customer);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuthentication(HttpSession session){

        CustomerDTO customerDTO=(CustomerDTO)session.getAttribute("customer");
        if(customerDTO != null){
            return ResponseEntity.ok(customerDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

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
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setContactNumber(customerDTO.getContactNumber());
        customer.setPassword(customerDTO.getPassword());
        customer.setRole(customerDTO.getRole());
        customerService.save(customer);

        return ResponseEntity.ok().build();

    }
}
