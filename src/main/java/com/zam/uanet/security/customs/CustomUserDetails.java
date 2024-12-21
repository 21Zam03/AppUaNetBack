package com.zam.uanet.security.customs;

import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.repositories.PermissionRepository;
import com.zam.uanet.repositories.RoleRepository;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserCollection user;

    public CustomUserDetails(RoleRepository roleRepository, PermissionRepository permissionRepository,
                             UserCollection user) {
        this.user = user;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        List<ObjectId> roles = user.getRoleList();
        roles.forEach(role -> authorityList
                .add(new SimpleGrantedAuthority("ROLE_".concat(roleRepository.findById(role).orElseThrow(() -> {
                    return new NotFoundException("Role not found");
                }).getRoleName()))));
        roles.stream().flatMap(role -> roleRepository.findById(role).orElseThrow(() -> {
                return new NotFoundException("Role not found");
        }).getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permissionRepository.findById(permission).orElseThrow(() -> {
                    return new NotFoundException("Permission not found");
                }).getName())));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNoExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNoLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialNoExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
