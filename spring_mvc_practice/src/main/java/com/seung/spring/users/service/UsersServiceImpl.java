package com.seung.spring.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seung.spring.users.dao.UsersDao;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private UsersDao usersDao;
}
