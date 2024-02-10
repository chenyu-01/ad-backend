package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sa57.team01.adproject.DTO.RentalPropertyDTO;
import sa57.team01.adproject.DTO.SalePropertyDTO;
import sa57.team01.adproject.models.Appointment;
import sa57.team01.adproject.models.Customer;
import sa57.team01.adproject.models.RentalProperty;
import sa57.team01.adproject.models.SaleProperty;
import sa57.team01.adproject.services.*;

import java.util.List;
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
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, String> body) {
        String ownerId = body.get("ownerId");
        String buyerId = body.get("buyerId");
        String propertyId = body.get("propertyId");
        String appointmentDate = body.get("appointmentDate");

        try {
            //decide by propertyType
            if (rentalPropertyService.findRentalPropertyById(Long.parseLong(propertyId)) != null) {
                appointmentService.createAppointment(ownerService.findOwnerById(Long.parseLong(ownerId)), buyerService.findBuyerById(Long.parseLong(buyerId)), rentalPropertyService.findRentalPropertyById(Long.parseLong(propertyId)), appointmentDate);
            } else {
                appointmentService.createAppointment(ownerService.findOwnerById(Long.parseLong(ownerId)), buyerService.findBuyerById(Long.parseLong(buyerId)), salePropertyService.findSalePropertyById(Long.parseLong(propertyId)), appointmentDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error");
        }
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{appointmentId}")
    public String cancelAppointment(@PathVariable Long appointmentId) {
        boolean canceled = appointmentService.cancelAppointment(appointmentId);
        if (canceled) {
            return "Appointment with ID " + appointmentId + " has been canceled and removed from the database.";
        } else {
            return "Appointment with ID " + appointmentId + " not found or already canceled.";
        }
    }


    @GetMapping("/appointments")
    public List<Appointment> getAppointmentsByCustomer(@RequestParam("customerId") Customer customerId) {
        return appointmentService.getAppointmentsByCustomerId(customerId);
    }


}