package hxy.inspec.customer.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hxy.inspec.customer.dao.AccountDao;
import hxy.inspec.customer.po.Account;

public class AccountService {
	

	private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

	// 插入订单
	public boolean insert(Account order) throws IOException {
		AccountDao orderDao = new AccountDao();
	
			int flag = orderDao.insert(order);
			if (flag == 1) {
				return true;
			}
		
		return false;
	}

}
