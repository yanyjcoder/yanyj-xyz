package test;

import xyz.yanyj.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User extends BaseEntity{


    @Column(name = "username", length = 32, unique = true, nullable = false)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
