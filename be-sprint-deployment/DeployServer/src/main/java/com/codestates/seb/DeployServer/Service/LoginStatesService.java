package com.codestates.seb.DeployServer.Service;

import com.codestates.seb.DeployServer.Domain.LoginStates;

public interface LoginStatesService {
  void InitializeStates(boolean isLogin, boolean isConnectedToDatabase);
  LoginStates GetStates();
}