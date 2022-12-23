package com.vitweb.vitwebapi.application.services.impl;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.ChangePasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.parameter.auth.RefreshPasswordRequest;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.AuthenticationResponse;
import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.*;
import com.vitweb.vitwebapi.application.inputs.user.CreateUserInput;
import com.vitweb.vitwebapi.application.repositories.IRoleRepository;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.application.services.IAuthService;
import com.vitweb.vitwebapi.application.services.ITokenService;
import com.vitweb.vitwebapi.application.utils.JwtUtil;
import com.vitweb.vitwebapi.application.utils.SendMailUtil;
import com.vitweb.vitwebapi.configs.exceptions.InvalidException;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Role;
import com.vitweb.vitwebapi.domain.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

  @Value("${jwt.secret_key}")
  private String SECRET_KEY;

  @Value("${jwt.time_token_expiration}")
  private Integer TIME_TOKEN_EXPIRATION;

  private final IUserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final ModelMapper modelMapper;
  private final ITokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final IRoleRepository roleRepository;

  public AuthServiceImpl(IUserRepository userRepository, JwtUtil jwtUtil, ModelMapper modelMapper, ITokenService tokenService, PasswordEncoder passwordEncoder, IRoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
    this.tokenService = tokenService;
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

  @Override
  public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
    try {
      String refreshToken = getTokenFromRequest(request);
      if (refreshToken != null && jwtUtil.validateJwtToken(refreshToken)) {
        String uuid = jwtUtil.getUUIDFromJwtToken(refreshToken);
        String accessToken = Jwts.builder()
            .setSubject(uuid)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TIME_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
        return new AuthenticationResponse(accessToken, CommonConstant.BEARER_TOKEN, refreshToken);
      }
    } catch (Exception ex) {
      response.setHeader("error", ex.getMessage());
    }
    return null;
  }

  @Override
  public RequestResponse forgotPassword(String email, String applicationUrl) {
    Optional<User> user = userRepository.findByEmail(email);
    checkUserExists(user);

    String sixNum = RandomStringUtils.randomNumeric(6);
    tokenService.createTokenVerify(sixNum, user.get(), 60);
    SendMailUtil.sendMailSimple(user.get().getEmail(), "Mã khôi phục mật khẩu: " + sixNum, "Yêu cầu khôi phục mật khẩu Vit Web");
    return new RequestResponse(CommonConstant.TRUE, "");
  }

  @Override
  public RequestResponse refreshPassword(RefreshPasswordRequest request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());
    checkUserExists(user);

    user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user.get());
    return new RequestResponse(CommonConstant.TRUE, "Refresh password successfully !");
  }

  @Override
  public RequestResponse changePassword(ChangePasswordRequest request) {
    Optional<User> user = userRepository.findByEmail(request.getEmail());
    checkUserExists(user);

    String oldPasswordInput = passwordEncoder.encode(request.getOldPassword());
    if(!user.get().getPassword().equals(oldPasswordInput)) {
      throw new VsException("The current password entered is incorrect");
    }

    user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
    userRepository.save(user.get());
    return new RequestResponse(CommonConstant.TRUE, "Change password successfully");
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }

  private void checkUserExists(Optional<User> user) {
    if(user.isEmpty()) {
      throw new VsException(UserMessageConstant.User.ERR_NOT_FOUND_BY_ID);
    }
  }
}
