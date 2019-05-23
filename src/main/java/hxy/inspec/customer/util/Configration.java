package hxy.inspec.customer.util;

//静态变量类
public class Configration {
	public static String FILE_ROOT_DIR = "";// 用于存储文件目录,这个目录不可以采用应用下的目录，因为应用一旦更新就没了。
	public static String IMAGE_URL = "";
	public static String MYSQL_HOST = "";
	public static String MYSQL_PASSWD = "";
	public static String MYSQL_USER = "";
	public  static final String BILL_SUBMITTED = "1";
	public  static final String BILL_UNPAY = "2";
	public  static final String BILL_PAY = "3";
}