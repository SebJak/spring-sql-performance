package com.langpath.service.api;

import com.langpath.sql.model.entity.user.User;

/**
 * Created by sjakowski on 2016-03-16.
 */
public interface UserServiceApi {

    void save(User user);
}