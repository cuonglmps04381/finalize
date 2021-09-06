package com.example.webdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;
    private Date createDate;
    private Date modifiedDate;
    private Set<RoleDTO> roleDTO;


    @Override
    public String toString() {
        return "User [id=" + id + ", email="
                + email + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", active=" + active + ", createDate="
                + createDate + ", modifiedDate=" + modifiedDate + "]";
    }
}
