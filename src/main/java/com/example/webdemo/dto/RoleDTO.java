package com.example.webdemo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private String role;

    @Override
    public String toString() {
        return "Role [id=" + id + ", role="
                + role + "]";
    }
}
