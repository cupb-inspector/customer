package hxy.inspec.customer.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hxy.inspec.customer.po.Account;
import hxy.inspec.customer.po.DataStatistic;
import hxy.inspec.customer.po.Orders;
import hxy.inspec.customer.po.User;
import hxy.inspec.customer.service.DataStatisticService;
import hxy.inspec.customer.service.OrderService;
import hxy.inspec.customer.service.UserService;
import hxy.inspec.customer.util.Configuration;
import hxy.inspec.customer.util.DateUtil;

@Controller
@RequestMapping("/")
public class OrderController {
	private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@RequestMapping(value = "/cusInsertOrder", method = RequestMethod.POST)
	public void cusInsertOrder(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		int resultCode = -1;
		String orderId = DateUtil.getCurrentDateStr();// 采用微信的同样方式生成订单号
		int cusMoney = -1;// 用户钱包余额
		int moneyStatus = -1;// 0不足，1充足
		int billPrice = Configuration.BILL_PRICE;// 订单默认价格
		if (user != null) {
			// https://blog.csdn.net/u013230511/article/details/48314491

			String excdate = null;
			String facname = null;
			String facaddress = null;
			String facman = null;
			String factel = null;
			String profile = null;
			String type = null;
			String reports = null;
			int status = 0;
			String fee = null;
			String cost = null;
			String otherCost = null;
			String profit = null;
			String goods = null;
			String goodsType = null;
			String fileName = null;
			String fileUuidName = null;
			boolean flag = false;
			try {
				// 使用Apache文件上传组件处理文件上传步骤：
				// 1、创建一个DiskFileItemFactory工厂
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 2、创建一个文件上传解析器
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 解决上传文件名的中文乱码
				upload.setHeaderEncoding("UTF-8");
				// 3、判断提交上来的数据是否是上传表单的数据
				if (!ServletFileUpload.isMultipartContent(request)) {
					// 按照传统方式获取数据
					return;
				}

				// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项

				List<FileItem> list = null;

				// 解决：https://blog.csdn.net/sinat_34104446/article/details/82755403

				RequestContext context = new ServletRequestContext(request);
				try {
					list = upload.parseRequest(context);
					logger.info("遍历的大小" + list.size());
				} catch (FileUploadException e) {
					e.printStackTrace();
				}

				for (FileItem item : list) {
					logger.info("遍历文件");
					if (item.isFormField()) {

						String key = item.getFieldName();
						String value = null;
						try {
							value = item.getString("UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						logger.info(key + "\t" + value);
						switch (key) {
						case "excdate":
							excdate = value;
							break;
						case "facname":
							facname = value;
							break;
						case "facaddress":
							facaddress = value;
							break;
						case "facman":
							facman = value;
							break;
						case "factel":
							factel = value;
							break;
						case "profile":
							profile = value;
							break;
						case "goods":
							goods = value;
							break;
						case "goodsType":
							goodsType = value;
							break;
						case "post_type":
							if ("temp".equals(value)) {
								status = Configuration.BILL_TEMP;// 保存草稿
							} else if ("unpay".equals(value)) {
								status = Configuration.BILL_UNPAY;// 提交未付款，下一步就是付款
							}
						default:
							break;
						}

					} else {
						String uuid = UUID.randomUUID().toString().replace("-", "");

						// 生成随机数和id，文件重新命名为id+原来名字，存入数据库.
						fileName = item.getName();
						fileUuidName = uuid + fileName;
						File file = new File(Configuration.FILE_ROOT_DIR, fileUuidName);
						try { // 创建一个文件输出流
							InputStream in = item.getInputStream();
							FileOutputStream out = new FileOutputStream(file);
							// 创建一个缓冲区
							byte buffer[] = new byte[1024]; // 判断输入流中的数据是否已经读完的标识
							int len = 0;
							// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
							while ((len = in.read(buffer)) > 0) {
								out.write(buffer, 0, len);
							} // 关闭输入流
							in.close();
							// 关闭输出流
							out.close(); // 删除处理文件上传时生成的临时文件
							item.delete();
							resultCode = 200;

						} catch (FileNotFoundException e) {
							e.printStackTrace();
							resultCode = 601;// 错误
						} catch (IOException e) {
							// TODO: handle exception
						}
						logger.info("文件名路径：" + file.getAbsolutePath());
					}
				}
				flag = true;
			} catch (NullPointerException e) {
				logger.warn("传入的是一个null");
			}
			if (flag) {
//			获取时间
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
				String date = dateFormat.format(now);
				Orders order = new Orders();
				order.setOrderid(orderId);
				order.setCusId(user.getCusid());
				order.setCost(cost);
				order.setDate(date);
				order.setExcedate(excdate);
				order.setFactoryaddress(facaddress);
				order.setFactoryman(facman);
				order.setFactoryname(facname);
				order.setFactorytel(factel);
				order.setFile(type);
				order.setReportfile(reports);
				order.setProfile(profile);
				order.setStatus(status);
				order.setGoods(goods);
				order.setGoodsType(goodsType);
				order.setFileuuid(fileUuidName);
				order.setFile(fileName);
				OrderService orderService = new OrderService();
				if (orderService.insert(order)) {
					resultCode = 200;
					// 更新订单总数
					int a = Integer.parseInt(user.getCusOrders()) + 1;
					user.setCusOrders(String.valueOf(a));
					DataStatisticService dataStatisticService = new DataStatisticService();
					DataStatistic dataStatistic = dataStatisticService.select();
					int b = dataStatistic.getTotal();
					b = b + 1;
					dataStatistic.setTotal(b);
					int c = dataStatistic.getUnfinishedBill();
					c = c + 1;
					dataStatistic.setUnfinishedBill(c);

					try {
						// 更新订单
						dataStatisticService.update(dataStatistic);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					resultCode = 500;
				}
			} else {
				resultCode = 400;// bad request
			}

			if (status == Configuration.BILL_UNPAY) {
				// 返回钱包信息，
				UserService userService = new UserService();
				user = userService.selectUserByTel(user.getCustel());

				cusMoney = user.getCusMoney();

				if (cusMoney >= billPrice) {
					// 余额充足
					moneyStatus = 1;

				} else {
					// 余额不足
					moneyStatus = 0;
				}

			}

		} else {
			resultCode = 604;// 返回没有数据
		}

		logger.info("下单信息返回");
		org.json.JSONObject user_data = new org.json.JSONObject();
		user_data.put("resultCode", resultCode);// 返回操作状态
		user_data.put("orderId", orderId);// 返回订单号
		user_data.put("moneyStatus", moneyStatus);// 返回订单是否有足够支付的的余额
		user_data.put("cusMoney", cusMoney);// 用户余额
		user_data.put("billPrice", billPrice);// 订单价格
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
	@ResponseBody
	@RequestMapping(value = "/orderPay", method = RequestMethod.POST)
	public HashMap<String, Object>  orderPay(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		int resultCode=0;
		if(user!=null) {
			logger.info(String.format("用户%s在支付订单", user.getCusname()));
		}
		HashMap<String, Object> hashMap = new HashMap<>();
		// 获取订单号即可完成支付？这个逻辑有点扯？
		boolean flag = false;
		String ordersId = null;
		String pay = null;
		try {
			ordersId = request.getParameter("orderId").trim();// 订单号
			pay = request.getParameter("pay").trim();// 备注
			flag=true;
		} catch (Exception e) {
		}
		if (flag) {
			OrderService orderService = new OrderService();
			try {
				Orders orders = orderService.selectAllById(ordersId);
				if (orders != null && "true".equals(pay)) {
					// 扣款
					UserService userService = new UserService();
					user = userService.selectUserById(user.getCusid());
					orders.setStatus(Configuration.BILL_PAY);// 订单支付成功
					int a = user.getCusMoney() - Configuration.BILL_PRICE;// 每单的定价
					user.setCusMoney(a);
					userService.update(user);
					// 更新订单状态
					orders.setStatus(Configuration.BILL_PAY);// 订单已支付
					orderService.updateStatus(orders);
					resultCode=200;
					//需要加上钱包明细
					Account account = new Account();
//					account.set
				}else {
					resultCode=601;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}else {
			resultCode=404;
		}
		logger.info(String.format("返回支付信息%s", resultCode));
		hashMap.put("resultCode", resultCode);
		return hashMap;
	}

	@RequestMapping(value = "/details2", method = RequestMethod.GET)
	public String details2(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {

			String ordersId = request.getParameter("id").trim();// 备注
			// 先依据id查询该订单，再判断该订单是否是该用户的，防止恶意的爬虫
			OrderService orderService = new OrderService();
			try {
				Orders orders = orderService.selectAllById(ordersId);
				if (orders != null) {
					model.addAttribute("status", orders.getStatusString());
					model.addAttribute("ordersId", ordersId);
					model.addAttribute("goods", orders.getGoods());
					model.addAttribute("custel", orders.getCusId());
					model.addAttribute("exceData", orders.getExcedate());
					String inspectTel = orders.getQualId();
					if ("null".equals(inspectTel)) {
						model.addAttribute("inspec", "请填写质检员号码");
					} else
						model.addAttribute("inspec", orders.getQualId());

					model.addAttribute("exceData", orders.getExcedate());
					model.addAttribute("factoyName", orders.getFactoryname());
					model.addAttribute("facAddress", orders.getFactoryaddress());
					model.addAttribute("facMan", orders.getFactoryman());
					model.addAttribute("facTel", orders.getFactorytel());
					model.addAttribute("date", orders.getDate());
					model.addAttribute("", orders.getExcedate());
					String report = orders.getReportfile();
					String reportuuid = orders.getReportfileuuid();

					if (report != null && !"".equals(report) && !"null".equals(report)) {
						model.addAttribute("report", report);
					} else
						model.addAttribute("report", "没有报告文件");

					if (reportuuid != null && !"".equals(reportuuid) && !"null".equals(reportuuid)) {
						model.addAttribute("reportuuid", reportuuid);
					} else {
						model.addAttribute("reportuuid", "null");
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "order/orders-details2";
		} else
			return "lose";

	}

//	报告完成后的详情，已经不可以修改报告了
	@RequestMapping(value = "/details3", method = RequestMethod.GET)
	public String details3(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// 获取用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {

			String ordersId = request.getParameter("id").trim();// 备注
			// 先依据id查询该订单，再判断该订单是否是该用户的，防止恶意的爬虫
			OrderService orderService = new OrderService();
			try {
				Orders orders = orderService.selectAllById(ordersId);
				if (orders != null) {
					model.addAttribute("status", orders.getStatusString());
					model.addAttribute("ordersId", ordersId);
					model.addAttribute("goods", orders.getGoods());
					model.addAttribute("custel", orders.getCusId());
					model.addAttribute("exceData", orders.getExcedate());
					String inspectTel = orders.getQualId();
					if ("null".equals(inspectTel)) {
						model.addAttribute("inspec", "请填写质检员号码");
					} else
						model.addAttribute("inspec", orders.getQualId());

					model.addAttribute("exceData", orders.getExcedate());
					model.addAttribute("factoyName", orders.getFactoryname());
					model.addAttribute("facAddress", orders.getFactoryaddress());
					model.addAttribute("facMan", orders.getFactoryman());
					model.addAttribute("facTel", orders.getFactorytel());
					model.addAttribute("date", orders.getDate());
					model.addAttribute("", orders.getExcedate());
					String report = orders.getReportfile();
					String reportuuid = orders.getReportfileuuid();

					if (report != null && !"".equals(report) && !"null".equals(report)) {
						model.addAttribute("report", report);
					} else
						model.addAttribute("report", "没有报告文件");

					if (reportuuid != null && !"".equals(reportuuid) && !"null".equals(reportuuid)) {
						model.addAttribute("reportuuid", reportuuid);
					} else {
						model.addAttribute("reportuuid", "null");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "order/orders-details-finished";
		} else
			return "lose";

	}

}
