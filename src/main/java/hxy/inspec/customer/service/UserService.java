package hxy.inspec.customer.service;

import java.io.IOException;

import hxy.inspec.customer.dao.UserDao;
import hxy.inspec.customer.po.User;

public class UserService {
	public User login(String phone) {
		//依据电话号码查询数据库，返回对象，比对是否正确
		UserDao userDao = new UserDao();
		return userDao.selectUserByPhone(phone);
	}
	public boolean insert(User user) {
		UserDao userDao = new UserDao();
		try {
		int flag=	userDao.insert(user);
		if (flag==1) {
			return true;
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		
	}

}
