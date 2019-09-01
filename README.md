# inspection-customer-server
## 阿里云服务器
scp customer.war root@119.23.236.94:/home/apache-tomcat-9.0.19/webapps</br>
## 线上环境</br>
mvn clean package -Dmaven.test.skip=true -Pprod</br>

## log
* 2019/09/01 数据库建表，由于备注字段不知道一般有多少字，所以采用了longtext类型，应该可以解决字数不确定的问题。次数加了comment，用来标记下数据库字段都是啥玩意。
