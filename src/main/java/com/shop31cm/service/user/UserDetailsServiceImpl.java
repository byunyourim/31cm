package com.shop31cm.service.user;

import com.shop31cm.domain.User;
import com.shop31cm.domain.common.UserDetailsImpl;
import com.shop31cm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException(email));

        return new UserDetailsImpl(user);
    }
}
