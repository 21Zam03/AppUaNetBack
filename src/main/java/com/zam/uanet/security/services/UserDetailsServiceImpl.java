package com.zam.uanet.security.services;

import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.repositories.PermissionRepository;
import com.zam.uanet.repositories.RoleRepository;
import com.zam.uanet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                                  PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCollection userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User "+username+" does not exist"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoleList()
                .forEach(role -> authorityList
                        .add(new SimpleGrantedAuthority("ROLE_".concat(roleRepository.findById(role).orElseThrow(() -> {
                            return new UsernameNotFoundException("Role does not exist");
                        }).getRoleName()))));
        userEntity.getRoleList().stream()
                .flatMap(role -> roleRepository.findById(role).orElseThrow(() -> {
                    return new UsernameNotFoundException("Role does not exist");
                }).getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permissionRepository.findById(permission).orElseThrow(() -> {
                    return new UsernameNotFoundException("Permission "+permission+" does not exist");
                }).getName())));
        return new User(userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }
}