package com.main.implementation;

import com.main.contract.IAuthService;
import com.main.helpers.Constants;
import com.main.helpers.PaginatedListResponse;
import com.main.model.ServerResponse;
import com.main.model.User;
import com.main.model.response.BaseResponse;
import com.repository.factories.GetUserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private GetUserQuery getUserQuery;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;





    @Override
    public User GetUserByEmail(String email) {
        return null;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        var result=  getUserQuery.GetUser(username);
        Optional<User> opt = Optional.ofNullable(result);
        return opt;
    }


    public static boolean isNull(String str) {
        return str == null ? true : false;
    }

    public static boolean isNullOrBlank(String param) {
        if (isNull(param) || param.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
