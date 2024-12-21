package com.zam.uanet.security.services;

import com.zam.uanet.collections.PersonCollection;
import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.repositories.PermissionRepository;
import com.zam.uanet.repositories.PersonRepository;
import com.zam.uanet.repositories.RoleRepository;
import com.zam.uanet.repositories.UserRepository;
import com.zam.uanet.security.customs.CustomUserDetails;
import org.bson.types.ObjectId;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;
    private final PermissionRepository permissionRepository;

    public CustomOidcUserService(UserRepository userRepository, RoleRepository roleRepository, PersonRepository personRepository,
                                 PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> userClaims = oidcUser.getAttributes();

        String email = (String) userClaims.get("email");
        String firstName = (String) userClaims.get("given_name");
        String lastName = (String) userClaims.get("family_name");
        String profilePicture = (String) userClaims.get("picture");

        UserCollection user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserCollection newUser = UserCollection.builder()
                            .email(email)
                            .password(null)
                            .isEnabled(true)
                            .accountNoExpired(true)
                            .accountNoLocked(true)
                            .credentialNoExpired(true)
                            .roleList(List.of(new ObjectId("66e9b7939618aad29d46b7a9")))
                            .build();
                    UserCollection userCreated = userRepository.save(newUser);
                    PersonCollection personCollection = PersonCollection.builder()
                            .userId(userCreated.getIdUser())
                            .photoProfile(profilePicture)
                            .firstName(firstName)
                            .lastName(lastName)
                            .build();
                    personRepository.save(personCollection);
                    return userCreated;
                });
        CustomUserDetails customUserDetails = new CustomUserDetails(roleRepository, permissionRepository, user);
        return new DefaultOidcUser(customUserDetails.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

}
