package se.visionmate.test.config.security.dto;

import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import se.visionmate.test.model.Permission;
import se.visionmate.test.model.Role;
import se.visionmate.test.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    String username;
    String password;
    List<String> authorities;

    public static UserDetails from(User user){
        return new UserDetails(user.getUsername(), user.getPassword(), authoritiesFrom(user.getRole()));
    }

    private static List<String> authoritiesFrom(Role role) {
        List<String> authorities = role.getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
        authorities.add(role.getName());
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
