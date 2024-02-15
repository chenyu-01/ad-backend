package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa57.team01.adproject.DTO.OwnerDTO;
import sa57.team01.adproject.models.Owner;
import sa57.team01.adproject.services.OwnerService;

import java.util.Optional;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final OwnerService ownerService;
    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long id) {
        Optional<Owner> owner = Optional.ofNullable(ownerService.findOwnerById(id));
        if(owner.isPresent()) {
            // convert to DTO
            OwnerDTO ownerDTO = new OwnerDTO(owner.get());
            return ResponseEntity.ok(ownerDTO);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
