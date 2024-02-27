package com.blinked.services;

import com.blinked.apis.requests.UserPropsParam;
import com.blinked.apis.requests.UserPropsUpdateParam;
import com.blinked.entities.User;
import com.blinked.repositories.base.CrudService;

public interface UserService extends CrudService<User, Long> {
//	public User find(Long id);

//	public Page<User> find(Optional<Integer> page, Optional<Integer> size);

	public User create(UserPropsParam props);

	public User createForProvider(UserPropsParam props);

	public void remove(Long id);

	public User update(Long id, UserPropsUpdateParam body);

}
