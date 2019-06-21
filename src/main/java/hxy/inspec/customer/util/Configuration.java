package hxy.inspec.customer.util;

//静态变量类
public class Configuration {
	public static String FILE_ROOT_DIR = "";// 用于存储文件目录,这个目录不可以采用应用下的目录，因为应用一旦更新就没了。
	public static String IMAGE_URL = "";
	public static String MYSQL_HOST = "";
	public static String MYSQL_PASSWD = "";
	public static String MYSQL_USER = "";
	public  static final int BILL_SUBMITTED = 1;//订单已提交
	public  static final int BILL_UNPAY = 2;//订单未付款
	public  static final int BILL_PAY = 3;//订单已经付款
	public  static final int BILL_ASSIGNING = 4;//订单已经付款
	public  static final int BILL_ASSIGNED = 5;//验货员已接单
	public  static final int BILL_INSPECTOR_CONFIRM = 6;//验货员已接单
	public  static final int BILL_REPORT_SUBMIT = 7;//验货员已接单
	public  static final int BILL_REPORT_VERIFIED = 8;//验货员已接单
	public  static final int BILL_REPORT_FINISH = 15;//验货员完成报告
	
	public  static final int BILL_REPORT_PASSED = 9;//验货员已接单，报告通过
	public  static final int BILL_REPORT_UNPASSED = 10;//验货员已接单，报告未通过
	public  static final int BILL_CANCEL_BY_USER = 12;      //订单被客户自己取消
	public  static final int BILL_CANCEL_BY_ADMIN = 13;         //订单被管理员
	public  static final int BILL_TEMP =11;                 //草稿订单
	public  static final int BILL_CANCEL_BY_INSPECTOR = 14;   //验货员取消订单
	public  static final int BILL_DELAY_BY_INSPECTOR = 14;     //验货员延期              
	public  static final int BILL_REPORT_REFUSED1 = 14;          //报告被客户拒绝原因1
	public  static final int BILL_REPORT_REFUSED2 = 14;          //报告被客户拒绝原因2
	public  static final int BILL_NEW_USER_FREE = 14;           //新用户订单
	public  static final int BILL_BORROW = 14;                     //老用户赊账订单
	public  static final int BILL_BORROW_OK = 14;                 //管理员通过老用户赊账订单
	public  static final int BILL_BORROW_DONE_BUT_UNPAY = 14;   //老用户赊账但未结清订单
	public  static final int BILL_DELAY_BY_ADMIN = 14;           //管理员延期分配订单
	public  static final int BILL_REPORT_REFUSED_BY_ADMIN = 14;  //订单报告被管理员拒绝
	public  static final int BILL_REPORT_PASSED_BY_ADMIN = 14;   //订单报告被管理员通过
	public  static final int BILL_PAYED_SURE = 14;                 //订单通过确定性支付成功
	public  static final int BILL_DELETE_BY_ADMIN = 14;          //管理员删除的订单
	public  static final int BILL_DELETE_BY_USER = 14;         //用户删除的订单
	public  static final int BILL_UNPAY_FOR_INSPECTOR = 14; //未给验货员结清订单
	public  static final int BILL_PASSED_DONE = 14;       //结清订单
}