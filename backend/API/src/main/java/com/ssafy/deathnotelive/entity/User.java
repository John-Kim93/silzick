package com.ssafy.deathnotelive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user",
        indexes = {
                @Index(columnList = "id"),
                @Index(columnList = "userId"),
                @Index(columnList = "email"),
                @Index(columnList = "password"),
                @Index(columnList = "authority"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "modifiedAt"),
        })
@Entity
public class User extends BaseEntity implements UserDetails {

    @Setter
    @Column(nullable = false, unique = true, columnDefinition = "varchar(20)")
    private String userId;

    @Setter
    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String email;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String authority;

    @JsonIgnore //setter로 직접 입력 못하게 막음.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, columnDefinition = "varchar(100)")
    String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    public Boolean isAdmin() {
        return authority.equals("ROLE_ADMIN");
    }

    @Override
    public String getUsername() {
        return null;
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
