<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="hxy.inspec.customer.po.User"%>
<!--  -->
<%
request.setCharacterEncoding("utf-8");
User user = (User) request.getSession().getAttribute("user");
if (user == null) {
	//登录过期！重新登录提示页！
	response.sendRedirect(request.getContextPath() + "/admin-lose");
	//request.getRequestDispatcher("/WEB-INF/a.jsp").forward(request, response);
} else {
	
}
%>