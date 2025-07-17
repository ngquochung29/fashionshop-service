package shop.server.webconfig.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
public class UserDT implements UserDetails {
    public UserDT(String username, List<SimpleGrantedAuthority> role) {
        this.username = username;
        this.role = role;
    }

    private String username;
    private List<SimpleGrantedAuthority> role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }


}
