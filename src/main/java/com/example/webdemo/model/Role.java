package com.example.webdemo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROLE_ID")
	private Long roleId;

	@Column(name="ROLE_NAME")
	private String roleName;

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

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles")
	private Set<User> user;

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName="
				+ roleName + "]";
	}
}
