package com.vitweb.vitwebapi.application.services;

import com.vitweb.vitwebapi.adapter.web.v1.transfer.response.RequestResponse;
import com.vitweb.vitwebapi.application.inputs.user.ChangeAvatarInput;
import com.vitweb.vitwebapi.application.inputs.user.UpdateUserInput;
import com.vitweb.vitwebapi.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

  List<User> getAll();

  User getUserById(String id);

  User updateUser(UpdateUserInput updateUserInput);

  RequestResponse changeAvatar(ChangeAvatarInput changeAvatarInput);

  RequestResponse follow(String idFollow);

  RequestResponse unfollow(String idFollow);

  RequestResponse deleteById(String id);
}
