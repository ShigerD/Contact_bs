package xu.ye.bean;

import java.util.ArrayList;

public class GroupBean {//联系人群组

	private int id;//群组序号
	private String name;//群组名称
	private ArrayList<ContactBean> contact;//群组列表
	private int count;//群组成员书目

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ContactBean> getContact() {
		return contact;
	}
	public void setContact(ArrayList<ContactBean> contact) {
		this.contact = contact;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
