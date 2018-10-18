package com.mapper;

import java.util.List;

import com.model.User;

public interface UserMapper {
	
    User selectByPrimaryKey(Integer userId);
    
    List<User> selectAllUser();
    
    int insert(User record);

}
