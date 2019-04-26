package hxy.inspec.customer.po;

import lombok.Data;

@Data
public class Account {
	private String id;
	private String userTel;//用户
	private String operate;//加减
	private String value;//值
	private String surplus;//账户剩余
	private String time;//操作时间
	private String type;//类别：提现，充值，还是消费,消费可能是一句话。依据类别来显示
	private String file;//证明，提现或者充值都会有凭证图片，文件名
	private String fileUuid;//证明，提现或者充值都会有凭证图片，文件的uuid，下载定位
	private String adminTel;//操作的管理员
	private String notes;//备注
}


