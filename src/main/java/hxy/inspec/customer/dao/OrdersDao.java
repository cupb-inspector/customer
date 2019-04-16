package hxy.inspec.customer.dao;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hxy.inspec.customer.datasource.DataConnection;
import hxy.inspec.customer.po.Orders;

public class OrdersDao {
	private final static Logger logger = LoggerFactory.getLogger(OrdersDao.class);
	
	public Orders selectUserByOrdersId(String ordersId) {

		SqlSession sqlSession = null;
		try {
			sqlSession = DataConnection.getSqlSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Orders user = sqlSession.selectOne("Orders.findUserByOrdersId", ordersId);
		sqlSession.commit();//清空缓存
		sqlSession.close();
		return user;
	}
	
	
	//某个用户查看自己的订单
	public List<Orders> selectAllByTel(String custel) throws IOException {
		SqlSession sqlSession = DataConnection.getSqlSession();
		List<Orders> goodsList = sqlSession.selectList("Orders.findOrdersByTel", custel);
		logger.info("查询结果条数"+goodsList.size());
		for (Orders good : goodsList) {
//			System.out.format("%s\n", good.getNetName());
		}
		sqlSession.commit();
		sqlSession.close();
		return goodsList;
	}
	
	
	public int insert(Orders user) throws IOException {
		SqlSession sqlSession = DataConnection.getSqlSession();
		int flag = sqlSession.insert("Orders.insert", user);
		sqlSession.commit();
		sqlSession.close();
		logger.info("插入后结果：" + flag);
		return flag;
	}
	
	public Orders selectAllById(String ordersId) throws IOException {
		SqlSession sqlSession = DataConnection.getSqlSession();
		Orders goodsList = sqlSession.selectOne("Orders.findOrdersById", ordersId);
		logger.info("查询结果条数"+goodsList);
		
		sqlSession.commit();
		sqlSession.close();
		return goodsList;
	}
	
	//修改订单的状态
	public int updateStatus(Orders order) {
		SqlSession sqlSession = null;
		try {
			sqlSession = DataConnection.getSqlSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int flag=sqlSession.update("Orders.updateStatus", order);
		logger.info("修改订单的质检员号码,结果为："+flag);
		sqlSession.commit();//清空缓存
		sqlSession.close();
		return flag;
	
		
	}


	public List<Orders> selectAllByTelAndStatus(Orders tel) throws IOException {
		// TODO Auto-generated method stub

		SqlSession sqlSession = DataConnection.getSqlSession();
		List<Orders> goodsList = sqlSession.selectList("Orders.findAllByTelAndStatus", tel);
		logger.info("查询结果条数"+goodsList.size());
		for (Orders good : goodsList) {
//			System.out.format("%s\n", good.getNetName());
		}
		sqlSession.commit();
		sqlSession.close();
		return goodsList;
	
	}

}
