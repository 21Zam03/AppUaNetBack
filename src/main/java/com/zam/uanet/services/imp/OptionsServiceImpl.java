package com.zam.uanet.services.imp;

import com.zam.uanet.collections.RoleCollection;
import com.zam.uanet.payload.response.RoleResponse;
import com.zam.uanet.repositories.RoleRepository;
import com.zam.uanet.services.OptionsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionsServiceImpl implements OptionsService {

    private final RoleRepository roleRepository;

    public OptionsServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponse> getRolesList() {
        List<RoleCollection> listRole = roleRepository.findAll();
        List<RoleResponse> roleResponses = new ArrayList<RoleResponse>();
        for (RoleCollection roleCollection : listRole) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRoleId(roleCollection.getRoleId().toHexString());
            roleResponse.setLabel(roleCollection.getLabel());
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }
}
