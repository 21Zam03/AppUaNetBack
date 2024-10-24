package com.zam.uanet.services.imp;

import com.zam.uanet.collections.RoleCollection;
import com.zam.uanet.payload.response.MessageResponse;
import com.zam.uanet.repositories.RoleRepository;
import com.zam.uanet.services.MaintenanceService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final RoleRepository roleRepository;

    @Autowired
    public MaintenanceServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public MessageResponse createRole(String roleName) {
        if(roleRepository.existsByRoleName(roleName)) {
            return new MessageResponse("Role already exists with name " + roleName);
        }
        RoleCollection roleCollection = RoleCollection.builder()
                .roleName(roleName.toUpperCase())
                .label(roleName)
                .build();
        roleRepository.save(roleCollection);
        return new MessageResponse("Role created successfully");
    }

    @Override
    public MessageResponse deleteRole(ObjectId roleName) {
        roleRepository.deleteById(roleName);
        return new MessageResponse("Role deleted successfully");
    }

    @Override
    public MessageResponse updateRole(ObjectId roleId, String roleName) {
        RoleCollection roleCollection = roleRepository.findById(roleId).get();
        roleCollection.setRoleName(roleName.toUpperCase());
        roleCollection.setLabel(roleName);
        roleRepository.save(roleCollection);
        return new MessageResponse("Role updated successfully");
    }


}
