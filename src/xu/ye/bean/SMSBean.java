package xu.ye.bean;

public class SMSBean {//发送的短信数据库

	private String thread_id;//发送短信序号
	private String msg_count;//已发送数量
	private String address;//发送 短信的联系人地址
	private Long date;//发送短信的日期
	private String read;//发送短信的内容
	
	private String msg_snippet;
	
	public SMSBean(String threadId, String msgCount, String msgSnippet) {
		thread_id = threadId;
		msg_count = msgCount;
		msg_snippet = msgSnippet;
	}
	public SMSBean() {
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getThread_id() {
		return thread_id;
	}
	public void setThread_id(String threadId) {
		thread_id = threadId;
	}
	public String getMsg_count() {
		return msg_count;
	}
	public void setMsg_count(String msgCount) {
		msg_count = msgCount;
	}
	public String getMsg_snippet() {
		return msg_snippet;
	}
	public void setMsg_snippet(String msgSnippet) {
		msg_snippet = msgSnippet;
	}
}
