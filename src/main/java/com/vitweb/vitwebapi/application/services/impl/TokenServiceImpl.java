package com.vitweb.vitwebapi.application.services.impl;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.constants.CommonConstant;
import com.vitweb.vitwebapi.application.constants.DevMessageConstant;
import com.vitweb.vitwebapi.application.constants.UserMessageConstant;
import com.vitweb.vitwebapi.application.repositories.ITokenRepository;
import com.vitweb.vitwebapi.application.repositories.IUserRepository;
import com.vitweb.vitwebapi.application.services.ITokenService;
import com.vitweb.vitwebapi.application.utils.SendMailUtil;
import com.vitweb.vitwebapi.configs.exceptions.VsException;
import com.vitweb.vitwebapi.domain.entities.Token;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements ITokenService {
  private final ITokenRepository tokenRepository;
  private final IUserRepository userRepository;

  public TokenServiceImpl(ITokenRepository tokenRepository, IUserRepository userRepository) {
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<Token> getAll() {
    return tokenRepository.findAll();
  }

  @Override
  public Token getTokenById(Long id) {
    Optional<Token> oldToken = tokenRepository.findById(id);
    checkTokenExists(oldToken);

    return oldToken.get();
  }

  @Override
  public RequestResponse verify(String token) {
    Optional<Token> oldToken = tokenRepository.findByToken(token);
    checkTokenExists(oldToken);

    User user = oldToken.get().getUser();
    if (user.getStatus()) {
      throw new VsException("Account confirmed!");
    }

    Calendar calendar = Calendar.getInstance();
    if((oldToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
      tokenRepository.delete(oldToken.get());
      throw new VsException("Token expired");
    }

    // Update status user and verification token
    user.setStatus(true);
    userRepository.save(user);

    return new RequestResponse(CommonConstant.TRUE, "Verify Successfully!");
  }

  @Override
  public RequestResponse resendToken(String token, String applicationUrl) {
    Optional<Token> oldToken = tokenRepository.findByToken(token);
    checkTokenExists(oldToken);

    String newToken = UUID.randomUUID().toString();
    oldToken.get().setToken(newToken);
    tokenRepository.save(oldToken.get());

    User user = oldToken.get().getUser();

    String url = applicationUrl
        + "/api/v1/tokens/verify/"
        + newToken;
    SendMailUtil.sendMailSimple(user.getEmail(), url, "Verify sign up Vit Web");
    return new RequestResponse(CommonConstant.TRUE, "");
  }

  @Override
  public void createTokenVerify(String token, User user) {
    Token newToken = new Token(token, user);
    tokenRepository.save(newToken);
  }

  private void checkTokenExists(Optional<Token> Token) {
    if(Token.isEmpty()) {
      throw new VsException(UserMessageConstant.ERR_EXCEPTION_GENERAL,
          DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID);
    }
  }
}
