package sa57.team01.adproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa57.team01.adproject.services.OwnerService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final OwnerService ownerService;
    @Autowired
    public UserController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }



}
