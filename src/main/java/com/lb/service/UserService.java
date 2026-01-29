package com.lb.service;

import com.lb.entity.User;
import com.lb.payload.dto.UserDTO;

import java.util.List;

public interface UserService {

   public User getCurrentUser() throws Exception;
   public List<UserDTO> getAllUsers();

   User findById(Long id) throws Exception;
}

