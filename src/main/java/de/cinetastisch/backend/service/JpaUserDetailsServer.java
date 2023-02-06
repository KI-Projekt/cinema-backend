package de.cinetastisch.backend.service;

import de.cinetastisch.backend.model.SecurityUser;
import de.cinetastisch.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsServer implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsServer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                             .map(SecurityUser::new)
                             .orElseThrow(() -> new UsernameNotFoundException("E-Mail not found: " + email));
    }
}
