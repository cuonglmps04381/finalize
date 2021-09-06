package com.example.webdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "EMAIL", nullable = false)
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;

	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@NotEmpty(message = "*Please provide your firstName")
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@NotEmpty(message = "*Please provide your lastName")
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "REMOVAL_FLAG", nullable = false)
	private Boolean removalFlag;

	@CreatedBy
	@Column(name = "CREATED_BY", updatable = false)
	protected String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATED_DATE", updatable = false)
	protected Date createdDate;

	@LastModifiedBy
	@Column(name = "UPDATED_BY")
	protected String modifiedBy;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "UPDATED_DATE")
	protected Date modifiedDate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
	private Set<Role> roles;


	@Override
	public String toString() {
		return "User [userId=" + userId + ", email="
				+ email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName +  ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + "]";
	}
}
