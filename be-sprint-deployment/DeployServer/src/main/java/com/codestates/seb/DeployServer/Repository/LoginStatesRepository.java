package com.codestates.seb.DeployServer.Repository;

import com.codestates.seb.DeployServer.Domain.LoginStates;

public interface LoginStatesRepository {
  void InitializeLoginStates(boolean isLogin, boolean isConnectedToDatabase);
  LoginStates GetLoginStates();
}
