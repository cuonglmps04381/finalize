package com.example.webdemo.service.impl;

import com.example.webdemo.model.Role;
import com.example.webdemo.model.User;
import com.example.webdemo.model.dto.UserDTO;
import com.example.webdemo.repository.UserRepository;
import com.example.webdemo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (Objects.nonNull(user)) {
			return modelMapper.map(user, UserDTO.class);
		}
		return null;
	}

	@Override
	public List<UserDTO> getAll() {
		List<User> lstUser = userRepository.getAll();
		if (lstUser.size() > 0 && Objects.nonNull(lstUser)) {
			return lstUser.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
		}
		return null;
	}


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRoleName()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRemovalFlag(), true, true, true, authorities);
	}
}
