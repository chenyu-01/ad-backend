package sa57.team01.adproject.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa57.team01.adproject.services.*;

import java.util.Map;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final OwnerService ownerService;
    private final BuyerService buyerService;
    private final RentalPropertyService rentalPropertyService;
    private final SalePropertyService salePropertyService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, OwnerService ownerService, BuyerService buyerService, RentalPropertyService rentalPropertyService, SalePropertyService salePropertyService) {
        this.appointmentService = appointmentService;
        this.ownerService = ownerService;
        this.buyerService = buyerService;
        this.rentalPropertyService = rentalPropertyService;
        this.salePropertyService = salePropertyService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody Map<String,String> body) {
        String ownerId = body.get("ownerId");
        String buyerId = body.get("buyerId");
        String propertyId = body.get("propertyId");
        String appointmentDate = body.get("appointmentDate");

        try{
        //decide by propertyType
        if (rentalPropertyService.findRentalPropertyById(Long.parseLong(propertyId)) != null) {
            appointmentService.createAppointment(ownerService.findOwnerById(Long.parseLong(ownerId)), buyerService.findBuyerById(Long.parseLong(buyerId)), rentalPropertyService.findRentalPropertyById(Long.parseLong(propertyId)), appointmentDate);
        } else {
            appointmentService.createAppointment(ownerService.findOwnerById(Long.parseLong(ownerId)), buyerService.findBuyerById(Long.parseLong(buyerId)), salePropertyService.findSalePropertyById(Long.parseLong(propertyId)), appointmentDate);
        }
    }catch (Exception e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Error");
    }
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId) {
        boolean isCancelled = appointmentService.cancelAppointment(appointmentId);
        if (isCancelled) {
            return new ResponseEntity<>("Appointment with id " + appointmentId + " has been cancelled.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to cancel appointment with id " + appointmentId, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAppointments")
    public ResponseEntity<?> getAppointmentsByCustomerId(HttpSession session) {
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        return appointmentService.getAppointmentsByCustomerId(id);
    }

    @GetMapping("/getAppointmentsForOwner")
    public ResponseEntity<?> getAppointmentsByOwnerId(HttpSession session) {
        if (session.getAttribute("customerId") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
        }
        long id = (long) session.getAttribute("customerId");
        return ResponseEntity.ok(appointmentService.getAppointmentsByOwnerId(ownerService.findOwnerById(id)));
    }

    @GetMapping("/confirm/{appointmentId}")
    public ResponseEntity<?> confirmAppointment(@PathVariable Long appointmentId) {
        appointmentService.confirmAppointment(appointmentId);
        return ResponseEntity.ok("Appointment confirmed");
    }



}
