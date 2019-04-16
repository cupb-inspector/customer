package hxy.inspec.customer.po;

import lombok.Data;

@Data
public class Orders {
	private String orderid;//订单号
	private String custel;//用户信息
	private String qualtel;//质检员信息
	private String excedate;//质检日期
	private String date;//下单日期
	private String factoryname;
	private String factoryaddress;
	private String factoryman;
	private String factorytel;
	private String profile;
	private String file;
	private String fileuuid;
	private String reportfile;
	private String reportfileuuid;
	private String status;
	private String fee;
	private String cost;
	private String othercost;
	private String profit;
	private String goods;//产品名称
	
	
	public String getStatusString() {
		String value="未知";
		switch (this.status) {
		case "1":
			value="提交成功";
			break;
		case "2":
			value="订单已分配";
			break;
		case "3":
			value="正在验货";
			break;
		case "4":
			value="报告已提交";
			break;
		case "5":
			value="报告已审核";
			break;
		case "6":
			value="报告已通过";
			break;
		case "7":
			value="投诉中";
			break;
		default:
			value="未知";
			break;
		}
		return value;
	}
	
	
}
