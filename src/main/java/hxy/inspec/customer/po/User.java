package hxy.inspec.customer.po;

import lombok.Data;

@Data
public class User {
	private String id;
	private String tel;
	private String name;
	private String passwd;
	private String grade;//等级，与信用相关
	private String vip;
	private String address;// 地址
	private String date;// 注册日期
	private String trade;//行业

}
