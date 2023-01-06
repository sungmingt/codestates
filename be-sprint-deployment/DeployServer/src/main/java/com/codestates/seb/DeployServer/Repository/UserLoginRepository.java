package com.codestates.seb.DeployServer.Repository;

public interface UserLoginRepository {
  Boolean FindByUserInfo(String id, String password);
}
