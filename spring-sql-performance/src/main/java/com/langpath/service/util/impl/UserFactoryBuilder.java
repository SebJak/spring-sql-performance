package com.langpath.service.util.impl;

import com.service.api.CrudApi;
import com.langpath.data.model.entity.user.User;
import com.langpath.data.model.entity.word.WordGroup;
import com.service.api.EntityFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Sebastian on 2016-04-03.
 */
@Service("userFactoryBuilder")
public class UserFactoryBuilder implements EntityFactoryBuilder<User> {

    @Autowired
    @Qualifier("userCrudService")
    CrudApi<User, Long> crudUser;

    @Autowired
    @Qualifier("wordGroupBuilder")
    private EntityFactoryBuilder wordGroupBuilder;



    @Override
    public Collection<User> buildAndPersist(int count) {
        Collection<User> users = new ArrayList<>();
        for(int i=0;i<count;i++){
            users.add(buildOne());
        }
        crudUser.save(users);
        return users;
    }

    @Override
    public Collection<User> build(int count) {
        Collection<User> users = new ArrayList<>();
        for(int i=0;i<count;i++){
            users.add(buildOne());
        }
        return users;
    }


    private User buildOne() {
        User user = new User();
        user.setLogin("Login_"+ UUID.randomUUID());
        user.setLastName("Last name_"+ UUID.randomUUID());
        user.setFirstName("First name_"+ UUID.randomUUID());
        user.setEmail("email@email.com" + UUID.randomUUID());
        user.setWordGroups(getWordGroups(5));
        user.setPassword("pass");

        return user;
    }

    private Set<WordGroup> getWordGroups(int count) {
        return new HashSet<>(wordGroupBuilder.build(count));
    }

}
