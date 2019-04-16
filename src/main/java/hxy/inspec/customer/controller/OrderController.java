package hxy.inspec.customer.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hxy.inspec.customer.po.Orders;
import hxy.inspec.customer.po.User;
import hxy.inspec.customer.service.OrderService;

@Controller
@RequestMapping("/")
public class OrderController {
	private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
	@RequestMapping(value = "/cusInsertOrder", method = RequestMethod.POST)
	public void cusInsertOrder(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		int resultCode = -1;
		if (user != null) {
			// https://blog.csdn.net/u013230511/article/details/48314491
			String excdate = null;
			String facname = null;
			String facaddress = null;
			String facman = null;
			String factel = null;
			String profile = null;
			String file = null;
			String reports = null;
			String status = null;
			String fee = null;
			String cost = null;
			String otherCost = null;
			String profit = null;
			String goods=null;
			boolean flag = false;
			try {
				excdate = request.getParameter("excdate").trim();// 执行日期
				facname = request.getParameter("facname").trim();// 工厂名称
				facaddress = request.getParameter("facaddress").trim();// 工厂地址
				facman = request.getParameter("facman").trim();// 联系人
				factel = request.getParameter("factel").trim();// 联系人电话
				profile = request.getParameter("profile").trim();// 备注
				goods = request.getParameter("goods").trim();// 备注
//			file = request.getParameter("file").trim();// 附件
				flag = true;
			} catch (NullPointerException e) {
				logger.warn("传入的是一个null");
			}
			if (flag) {
//			获取时间
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
				String date = dateFormat.format(now);
				status = "1";//1.提交成功 2.正在验货员正在接单 3.验货员已经出发，4.报告撰写中，5，已完成
				Orders order = new Orders();
				order.setCustel(user.getCustel());
				order.setCost(cost);
				order.setDate(date);
				order.setExcedate(excdate);
				order.setFactoryaddress(facaddress);
				order.setFactoryman(facman);
				order.setFactoryname(facname);
				order.setFactorytel(factel);
				order.setFile(file);
				order.setReportfile(reports);
				order.setProfile(profile);
				order.setStatus(status);
				order.setGoods(goods);
				OrderService orderService = new OrderService();
				if (orderService.insert(order)) {
					resultCode = 200;
				} else {
					resultCode = 500;
				}
			} else {
				resultCode = 400;// bad request
			}
		}
		else {
			resultCode=604;//返回没有数据
		}
		logger.info("返回注册信息");
		org.json.JSONObject user_data = new org.json.JSONObject();
		user_data.put("resultCode", resultCode);
		user_data.put("key2", "today4");
		user_data.put("key3", "today2");
		String jsonStr2 = user_data.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().append(jsonStr2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/cusSelectOrder", method = RequestMethod.POST)
	public void cusSelectOrder(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");

	}
	
	@RequestMapping(value = "/details2", method = RequestMethod.GET)
	public String details2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if (user!=null) {
			
			String ordersId = request.getParameter("id").trim();// 备注
			//先依据id查询该订单，再判断该订单是否是该用户的，防止恶意的爬虫
			OrderService orderService = new OrderService();
			try {
			Orders orders=	orderService.selectAllById(ordersId);
			if (orders!=null) {
				model.addAttribute("status", orders.getStatusString());
				model.addAttribute("ordersId", ordersId);
				model.addAttribute("goods", orders.getGoods());
				model.addAttribute("custel", orders.getCustel());
				model.addAttribute("exceData", orders.getExcedate());
				String inspectTel=orders.getQualtel();
				if("null".equals(inspectTel)) {
					model.addAttribute("inspec", "请填写质检员号码");
				}else
					model.addAttribute("inspec", orders.getQualtel());
				
				
				model.addAttribute("exceData", orders.getExcedate());
				model.addAttribute("factoyName", orders.getFactoryname());
				model.addAttribute("facAddress", orders.getFactoryaddress());
				model.addAttribute("facMan", orders.getFactoryman());
				model.addAttribute("facTel", orders.getFactorytel());
				model.addAttribute("date", orders.getDate());
				model.addAttribute("", orders.getExcedate());
				String report=orders.getReportfile();
				String reportuuid=orders.getReportfileuuid();
				
				if (report!=null&&!"".equals(report)&&!"null".equals(report)) {
					model.addAttribute("report", report);
				}else
					model.addAttribute("report", "没有报告文件");
					
				if (reportuuid!=null&&!"".equals(reportuuid)&&!"null".equals(reportuuid)) {
					model.addAttribute("reportuuid", reportuuid );
				}else {
					model.addAttribute("reportuuid", "null" );
				}
			
			
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
			
			return "order/orders-details2";
		}
		else
			return "lose";
		
	}
//	报告完成后的详情，已经不可以修改报告了
	@RequestMapping(value = "/details3", method = RequestMethod.GET)
	public String details3(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if (user!=null) {
			
			String ordersId = request.getParameter("id").trim();// 备注
			//先依据id查询该订单，再判断该订单是否是该用户的，防止恶意的爬虫
			OrderService orderService = new OrderService();
			try {
			Orders orders=	orderService.selectAllById(ordersId);
			if (orders!=null) {
				model.addAttribute("status", orders.getStatusString());
				model.addAttribute("ordersId", ordersId);
				model.addAttribute("goods", orders.getGoods());
				model.addAttribute("custel", orders.getCustel());
				model.addAttribute("exceData", orders.getExcedate());
				String inspectTel=orders.getQualtel();
				if("null".equals(inspectTel)) {
					model.addAttribute("inspec", "请填写质检员号码");
				}else
					model.addAttribute("inspec", orders.getQualtel());
				
				
				model.addAttribute("exceData", orders.getExcedate());
				model.addAttribute("factoyName", orders.getFactoryname());
				model.addAttribute("facAddress", orders.getFactoryaddress());
				model.addAttribute("facMan", orders.getFactoryman());
				model.addAttribute("facTel", orders.getFactorytel());
				model.addAttribute("date", orders.getDate());
				model.addAttribute("", orders.getExcedate());
				String report=orders.getReportfile();
				String reportuuid=orders.getReportfileuuid();
				
				if (report!=null&&!"".equals(report)&&!"null".equals(report)) {
					model.addAttribute("report", report);
				}else
					model.addAttribute("report", "没有报告文件");
					
				if (reportuuid!=null&&!"".equals(reportuuid)&&!"null".equals(reportuuid)) {
					model.addAttribute("reportuuid", reportuuid );
				}else {
					model.addAttribute("reportuuid", "null" );
				}
			
			
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		
			
			return "order/orders-details-finished";
		}
		else
			return "lose";
		
	}

}
