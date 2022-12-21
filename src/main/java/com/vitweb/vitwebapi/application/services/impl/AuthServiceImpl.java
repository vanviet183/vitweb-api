package com.vitweb.vitwebapi.application.services.impl;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.vitweb.vitwebapi.application.constants.*;
import com.vitweb.vitwebapi.application.inputs.user.CreateUserInput;
import com.vitweb.vitwebapi.application.repositories.IRoleRepository;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.application.services.IAuthService;
import com.vitweb.vitwebapi.application.services.ITokenService;
import com.vitweb.vitwebapi.application.utils.JwtUtil;
import com.vitweb.vitwebapi.configs.exceptions.InvalidException;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Role;
import com.vitweb.vitwebapi.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

  private final IUserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final IRoleRepository roleRepository;

  public AuthServiceImpl(IUserRepository userRepository, JwtUtil jwtUtil, ModelMapper modelMapper, PasswordEncoder passwordEncoder, IRoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
    Optional<User> user = userRepository.findByEmail(authenticationRequest.getEmail());
    checkUserExists(user);

    // check user authentication
    if(!user.get().getStatus()) {
      throw new VsException("User unconfirmed account!");
    }

    if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.get().getPassword())) {
      throw new InvalidException(UserMessageConstant.INVALID_AUTHENTICATION_INVALID_PASSWORD,
          DevMessageConstant.User.INVALID_PASSWORD);
    }

    return new AuthenticationResponse(jwtUtil.generateToken(user.get(), CommonConstant.FALSE), CommonConstant.BEARER_TOKEN,
        jwtUtil.generateToken(user.get(), CommonConstant.TRUE));
  }

  @Override
  public User signUp(CreateUserInput createUserInput) {
    Optional<User> oldUser = userRepository.findByEmail(createUserInput.getEmail());
    if(oldUser.isPresent()) {
      throw new VsException("Email has already exists");
    }
    User user = modelMapper.map(createUserInput, User.class);
    user.setPassword(passwordEncoder.encode(createUserInput.getPassword()));

    Role role = roleRepository.findByName(RoleConstant.ROLE_STUDENT);
    user.setRoles(List.of(role));
    user.setAuthProvider(AuthenticationProvider.LOCAL);

    return userRepository.save(user);
  }

  private void checkUserExists(Optional<User> user) {
    if(user.isEmpty()) {
      throw new VsException(UserMessageConstant.User.ERR_NOT_FOUND_BY_ID);
    }
  }
}
