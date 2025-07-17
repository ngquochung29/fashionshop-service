package shop.server.webconfig.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
public class GrantAuth implements GrantedAuthority {

    public GrantAuth(String authority) {
        this.authority = authority;
    }

    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
