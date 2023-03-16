package com.example.diplom.config;

import com.example.diplom.model.modelUser;
import com.example.diplom.model.modelUserPrincipal;
import com.example.diplom.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        modelUser user = userRepository.findByUsername(username);
                //.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new modelUserPrincipal(user);
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                AuthorityUtils.createAuthorityList(user.getAuthorities().toArray(new String[0]))
        //);
//        Collection<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//        GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthorities());

//        grantList.add(authority);
    }
}
