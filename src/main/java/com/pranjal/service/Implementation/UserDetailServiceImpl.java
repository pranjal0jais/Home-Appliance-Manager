package com.pranjal.service.Implementation;

import com.pranjal.dtos.UserRequest;
import com.pranjal.entity.User;
import com.pranjal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found with email : " + email));
    }

    public boolean saveUser(UserRequest userRequest){
        try {
            User user = User.builder()
                    .userId(UUID.randomUUID().toString())
                    .fullName(userRequest.getFullName())
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .mobile_no(userRequest.getMobileNo())
                    .build();

            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return false;
    }
}
