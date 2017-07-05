package xu.ye.bean;

import com.lidroid.xutils.db.annotation.Id;

public class BackList {//短信备份数据库
	@Id
	private int id;//短信序号
	private String lxid;//接收短信的序号
	private String smsid;//发送短信的序号
	
	public String getSmsid() {
		return smsid;
	}
	public void setSmsid(String smsid) {
		this.smsid = smsid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLxid() {
		return lxid;
	}
	public void setLxid(String lxid) {
		this.lxid = lxid;
	}
	
}
