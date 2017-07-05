package xu.ye.bean;

import com.lidroid.xutils.db.annotation.Id;

public class KuaiXu {//快速索引数据库
	@Id
	private int id;		//序号
	private int number;//手机号
	private String phone;//联系人名字
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
