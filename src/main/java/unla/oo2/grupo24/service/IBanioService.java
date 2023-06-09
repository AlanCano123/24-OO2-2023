package unla.oo2.grupo24.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unla.oo2.grupo24.entity.Banio;
import unla.oo2.grupo24.entity.UserRole;
import unla.oo2.grupo24.repository.IUserRepository;

public interface IBanioService {
	
	public List<Banio> listarTodos();
	
	public void guardarDispositivo(Banio b);
	
	public Banio buscarId(long id);
	
	public void eliminar(long id);

    @Service("userService")
    class UserService implements UserDetailsService {

        @Autowired
        @Qualifier("userRepository")
        private IUserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            unla.oo2.grupo24.entity.User user = userRepository.findByUsernameAndFetchUserRolesEagerly(username);
            return buildUser(user, buildGrantedAuthorities(user.getUserRoles()));
        }

        private User buildUser(unla.oo2.grupo24.entity.User user, List<GrantedAuthority> grantedAuthorities) {
            return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
                    true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
                    grantedAuthorities);
        }

        private List<GrantedAuthority> buildGrantedAuthorities(Set<UserRole> userRoles) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for(UserRole userRole: userRoles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
            }
            return new ArrayList<>(grantedAuthorities);
        }
    }
}
