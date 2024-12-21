package com.zam.uanet.services.imp;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zam.uanet.collections.RoleCollection;
import com.zam.uanet.collections.PersonCollection;
import com.zam.uanet.collections.UserCollection;
import com.zam.uanet.exceptions.DuplicateException;
import com.zam.uanet.exceptions.NotFoundException;
import com.zam.uanet.payload.request.InfoUserRequest;
import com.zam.uanet.payload.request.SignInRequest;
import com.zam.uanet.payload.request.SignUpRequest;
import com.zam.uanet.payload.response.SignInResponse;
import com.zam.uanet.payload.response.SignUpResponse;
import com.zam.uanet.payload.response.UserLoggedResponse;
import com.zam.uanet.repositories.*;
import com.zam.uanet.services.AuthService;
import com.zam.uanet.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthServiceImpl(PersonRepository personRepository, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
                           UserDetailsService userDetailsService, PermissionRepository permissionRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        signUpRequest.getRoleList().forEach(roleId -> {
            RoleCollection roleEntity = roleRepository.findById(new ObjectId(roleId))
                    .orElseThrow(() -> new NotFoundException("Role with id "+roleId+" does not exist" ));
        });

        boolean isDuplicate = userRepository.existsByEmail(signUpRequest.getEmail());

        if (isDuplicate) {
            throw new DuplicateException("Email "+ signUpRequest.getEmail() + " is already registered in the system");
        }

        List<ObjectId> rolesList = signUpRequest.getRoleList().stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());

        UserCollection user = UserCollection.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roleList(rolesList)
                .build();
        UserCollection userCreated = userRepository.save(user);

        PersonCollection person = PersonCollection.builder()
                .userId(userCreated.getIdUser())
                .photoProfile(null)
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .genre(signUpRequest.getGenre())
                .birthDate(signUpRequest.getBirthdate())
                .district(signUpRequest.getDistrict())
                .career(signUpRequest.getCareer())
                .nickname(signUpRequest.getNickname())
                .build();
        PersonCollection personCreated = personRepository.save(person);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCreated.getRoleList().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleRepository.findById(role)
                .orElseThrow(() -> {
                    return new NotFoundException("Role with id "+role+" does not exist" );
                }).getRoleName()))));

        userCreated.getRoleList().stream()
                .flatMap(role -> roleRepository.findById(role).orElseThrow(() -> {
                    return new UsernameNotFoundException("Role does not exist");
                }).getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permissionRepository.findById(permission).orElseThrow(() -> {
                    return new UsernameNotFoundException("Permission does not exist");
                }).getName())));

        String accessToken = jwtUtil.createToken(userCreated.getEmail(), authorityList);

        log.info("Client was registered successfully, email: {} ", signUpRequest.getEmail());

        return SignUpResponse.builder()
                .personId(userCreated.getIdUser().toHexString())
                .email(userCreated.getEmail())
                .picturePhoto(personCreated.getPhotoProfile())
                .fullName(personCreated.getFullName())
                .nickName(personCreated.getNickname())
                .roles(userCreated.getRoleList().stream().map(ObjectId::toString).collect(Collectors.toList()))
                .message("Client was registered successfully")
                .token(accessToken)
                .status(200)
                .build();
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserCollection userCollection = userRepository.findByEmail(authentication.getName()).orElseThrow(()->{
            return new NotFoundException("User not found");
        });


        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userCollection.getRoleList()
                .forEach(role -> authorityList
                        .add(new SimpleGrantedAuthority("ROLE_".concat(roleRepository.findById(role).orElseThrow(() -> {
                            return new UsernameNotFoundException("Role does not exist");
                        }).getRoleName()))));
        userCollection.getRoleList().stream()
                .flatMap(role -> roleRepository.findById(role).orElseThrow(() -> {
                    return new UsernameNotFoundException("Role does not exist");
                }).getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permissionRepository.findById(permission).orElseThrow(() -> {
                    return new UsernameNotFoundException("Permission "+permission+" does not exist");
                }).getName())));
        String accessToken = jwtUtil.createToken(userCollection.getEmail(), authorityList);


        PersonCollection personCollection = personRepository.findByUserId(userCollection.getIdUser()).orElseThrow(() -> {
            return new NotFoundException("person not found");
        });

        return SignInResponse.builder()
                .personId(personCollection.getPersonId().toHexString())
                .email(email)
                .picturePhoto(personCollection.getPhotoProfile())
                .fullName(personCollection.getFullName())
                .nickName(personCollection.getNickname())
                .roles(userCollection.getRoleList().stream().map(ObjectId::toString).collect(Collectors.toList()))
                .message("Welcome "+personCollection.getFullName()+" to UA NET")
                .token(accessToken)
                .status(200)
                .build();
    }

    @Override
    public UserLoggedResponse validateSession(String token) {
        DecodedJWT decodedJWT = jwtUtil.verifyToken(token);
        String email = jwtUtil.extractUsername(decodedJWT);
        UserCollection userCollection = userRepository.findByEmail(email).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });

        PersonCollection personCollection = personRepository.findByUserId(userCollection.getIdUser()).orElseThrow(() -> {
            return new NotFoundException("Person not found");
        });

        return UserLoggedResponse.builder()
                .personId(personCollection.getPersonId().toHexString())
                .email(email)
                .picturePhoto(personCollection.getPhotoProfile())
                .fullName(personCollection.getFullName())
                .nickName(personCollection.getNickname())
                .roles(userCollection.getRoleList().stream().map(ObjectId::toString).collect(Collectors.toList()))
                .district(personCollection.getDistrict())
                .career(personCollection.getCareer())
                .build();
    }

    @Override
    public UserLoggedResponse addInfoUser(InfoUserRequest infoUserRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCollection userCollection = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> {
            return new NotFoundException("User not found");
        });
        PersonCollection personCollection = personRepository.findByUserId(userCollection.getIdUser()).orElseThrow(() -> {
            return new NotFoundException("Person not found");
        });
        personCollection.setDistrict(infoUserRequest.getDistrict());
        personCollection.setCareer(infoUserRequest.getCareer());
        personCollection.setGenre(infoUserRequest.getGenre());
        personRepository.save(personCollection);

        RoleCollection role = roleRepository.findById(new ObjectId(infoUserRequest.getRoleId())).orElseThrow(() -> {
            return new NotFoundException("Role not found");
        });

        userCollection.setRoleList(List.of(role.getRoleId()));
        userRepository.save(userCollection);

        return UserLoggedResponse.builder()
                .personId(personCollection.getPersonId().toHexString())
                .email(authentication.getName())
                .picturePhoto(personCollection.getPhotoProfile())
                .fullName(personCollection.getFullName())
                .nickName(personCollection.getNickname())
                .roles(userCollection.getRoleList().stream().map(ObjectId::toString).collect(Collectors.toList()))
                .career(personCollection.getCareer())
                .district(personCollection.getDistrict())
                .build();
    }

    public Authentication authenticate (String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
