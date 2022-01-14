package com.example.webdemo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @NotEmpty(message = "*Please provide your firstName")
    private String firstName;

    @NotEmpty(message = "*Please provide your lastName")
    private String lastName;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your rePassword")
    private String rePassword;

    private boolean checked;

    private Set<RoleDTO> roleDTO;

    private Boolean isActive;

    private String token;

    @Override
    public String toString() {
        return "User [id=" + id + ", email="
                + email + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", checked=" + checked + "]";
    }
}
