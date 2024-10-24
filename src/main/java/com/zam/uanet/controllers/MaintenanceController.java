package com.zam.uanet.controllers;

import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.services.MaintenanceService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(MaintenanceController.API_PATH)
public class MaintenanceController {

    public static final String API_PATH = "/api/maintenance";

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createRole(@RequestParam String roleName) {
        return new ResponseEntity<>(maintenanceService.createRole(roleName), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MessageResponse> updateRole(@RequestParam String roleId, @RequestParam String roleName) {
        return new ResponseEntity<>(maintenanceService.updateRole(new ObjectId(roleId), roleName), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteRole(@RequestParam String roleId) {
        return new ResponseEntity<>(maintenanceService.deleteRole(new ObjectId(roleId)), HttpStatus.CREATED);
    }

}
