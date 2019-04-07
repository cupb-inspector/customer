package hxy.inspec.customer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hxy.inspec.customer.po.User;
import hxy.inspec.customer.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/lo", method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
		return "index";
	}
	
	@RequestMapping(value = "/loginVerify", method = RequestMethod.POST)
	public void loginVerify(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		int resultCode = 0;
		String tel = request.getParameter("tel").trim();// 这个应该是电话号码
		String password = request.getParameter("passwd").trim();
		logger.info("login Post tel is:" + tel + "Post password is:" + password);
		UserService userService = new UserService();
		if (tel != null && password != null && !"".equals(tel) && !"".equals(password)) {
			User user = userService.login(tel);
			if (user != null) {
				logger.info("用户存在" + user.getName());
				// 检查密码
				if (password.equals(user.getPasswd())) {
					// 匹配成功
					resultCode = 200;
					// 把用户对象存储到session
					request.getSession().setAttribute("user", user);
				} else {
					// 提示密码不正确
					resultCode = 601;
				}
			} else {
				// 提示用户未注册
				resultCode = 404;
			}
		}
		resultCode = 200;
		
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
	

	@RequestMapping(value = "/user-login-out", method = RequestMethod.GET)
	public String userLoginOut(ModelMap model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			logger.info(user.getName() + "将要退出登录");
			// false代表：不创建session对象，只是从request中获取。
			HttpSession session = request.getSession(false);
			if (session == null) {

			} else
				session.removeAttribute("user");
//	https://blog.csdn.net/u010143291/article/details/51597507 
		}
		return "login";
	}
}
