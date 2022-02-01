package com.main.contract;

import com.main.helpers.PaginatedListResponse;
import com.main.model.ServerResponse;
import com.main.model.User;

import java.util.Optional;

    public interface IAuthService {

        User GetUserByEmail(String email);

        Optional<User> getUserByUsername(String username);
    }

