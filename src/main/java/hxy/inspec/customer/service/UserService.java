package hxy.inspec.customer.service;

import hxy.inspec.customer.po.User;

public class UserService {

	public User login(String tel) {
		// TODO Auto-generated method stub
		User user = new User();
		if (tel=="123456") {
			user.setName("管理员");
			user.setPasswd("123456");
		}
		return user;
	}

}
