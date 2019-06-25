package hxy.inspec.customer.po;

import hxy.inspec.customer.util.Configuration;
import lombok.Data;

@Data
public class Orders {
	private String orderid;// 订单号
	private String cusId;// 用户信息
	private String adminIdOrder;// 订单审核管理员
	private String adminIdReport;// 报告审核管理员
	private String adminIdFinance;// 审核管理员
	private String qualId;// 质检员信息x
	private String excedate;// 质检日期
	private String date;// 下单日期
	private String factoryname;
	private String factoryaddress;
	private String factoryman;
	private String factorytel;
	private String profile;
	private String file;// 这个文件是客户下单的时候提交的
	private String fileuuid;
	private String reportfile;
	private String reportfileuuid;
	private int status;// 订单状态字
	private String type;// 验货类型，不同的类型有不同的价格
	private String fee;// 付款多少，或者是是否付款。
	private String cost;
	private String othercost;
	private String profit;
	private String goods;// 产品名称
	private String goodsType;// 产品类型

	// 下面应该设置全局静态变量，全局设置？
	public String getStatusString() {
		String value = "未知";
		switch (this.status) {
		case Configuration.BILL_TEMP:
			value = "草稿";
			break;
		case Configuration.BILL_SUBMITTED:
			value = "提交成功";
			break;
		case Configuration.BILL_UNPAY:
			value = "提交成功未付款";
			break;
		case Configuration.BILL_PAY:
			value = "已付款";
			break;
		case Configuration.BILL_ASSIGNING:
		case Configuration.BILL_ASSIGNING_BY_ADMIN_UNPAID:
			value = "正在分配质检员中";
			break;
		case Configuration.BILL_ASSIGNED:
		case Configuration.BILL_ASSIGNED_BY_ADMIN_UNPAID:
			value = "已分配质检员";
			break;
		case Configuration.BILL_DELAY_BY_ADMIN:
		case Configuration.BILL_DELAY_BY_ADMIN_UNPAID:
			value = "管理员分配订单延期";
			break;
		case Configuration.BILL_REFUSED_BY_ADMIN:
		case Configuration.BILL_REFUSED_BY_ADMIN_UNPAID:
			value = "管理员取消订单，退回重新填写";
			break;
		case Configuration.BILL_INSPECTOR_CONFIRM:
		case Configuration.BILL_ASSIGNED_UNPAID:
			value = "质检员确认验货";
			break;
		case Configuration.BILL_REFUSED_BY_INSPECTOR:
		case Configuration.BILL_REFUSED_BY_INSPECTOR_UNPAID:
			value = "质检员拒绝验货";
			break;
		case Configuration.BILL_DELAY_BY_ADMIN_0INSPECTOR:
		case Configuration.BILL_DELAY_BY_ADMIN_0INSPECTOR_UNPAID:
			value = "目前没有质检员可分配";
			break;
		case Configuration.BILL_REPORT_SUBMIT:
		case Configuration.BILL_REPORT_SUBMIT_UNPAID:
			value = "质检员已完成验货报告";
			break;
		case Configuration.BILL_REPORT_DELAY:
		case Configuration.BILL_REPORT_DELAY_UNPAID:
			value = "质检员未准时完成验货报告";
			break;
		case Configuration.BILL_CANCEL_BY_INSPECTOR:
		case Configuration.BILL_CANCEL_BY_INSPECTOR_UNPAID:
			value = "质检员退回质检任务";
			break;
		case Configuration.BILL_REPORT_VERIFIED:
			value = "报告已通过管理员审核";
			break;
		case Configuration.BILL_REPORT_PASSED_BY_ADMIN_UNPAID:
			value = "报告已通过管理员审核，请付款后查看质检报告";
			break;
		case Configuration.BILL_REPORT_REFUSED_BY_ADMIN:
		case Configuration.BILL_REPORT_REFUSED_BY_ADMIN_UNPAID:
			value = "报告被管理员驳回";
			break;
		case Configuration.BILL_REPORT_UNPASSED:
			value = "客户驳回质检报告";
			break;
		case Configuration.BILL_REPORT_PASSED:
			value = "客户通过质检报告";
			break;
		case Configuration.BILL_PASSED_DONE:
		case Configuration.BILL_DELETE_BY_ADMIN:
		case Configuration.BILL_DELETE_BY_USER:
		case Configuration.BILL_UNPAY_FOR_INSPECTOR:
		case Configuration.BILL_DELETE_BY_INSPECTOR:
			value = "该订单服务已结束";
			break;
		//此后德状态客户看不到，请根据自己需要修改
		/*case Configuration.BILL_DELETE_BY_ADMIN:
			value = "管理员删除订单";
			break;
		case Configuration.BILL_DELETE_BY_USER:
			value = "用户删除订单";
			break;
		case Configuration.BILL_UNPAY_FOR_INSPECTOR:
			value = "未支付给质检员报酬";
			break;
		case Configuration.BILL_DELETE_BY_INSPECTOR:
			value = "质检员删除订单";
			break;*/
		default:
			value = "未知";
			break;
		}
		return value;
	}

}
