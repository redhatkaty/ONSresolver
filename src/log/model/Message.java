package log.model;

/**
 * @author Administrator
 * 封装查询情况的消息
 */
public class Message {
	
	public Message(String time, String code) {
		//this.m_strIP = IP;//remove
		this.m_strTime = time;
		this.m_strCode = code;//SID+RID
		//this.m_strContent = content;//remove
	}

	/**
	 * 获取消息产生时间
	 * @return
	 */
	public String getTime() {
		return m_strTime;
	}
	/**
	 * 设置消息产生时间
	 * @param mStrTime
	 */
	public void setTime(String mStrTime) {
		m_strTime = mStrTime;
	}
	/**
	 * 获得消息内容，即编码
	 * @return
	 */
	public String getCode() {
		return m_strCode;
	}
	/**
	 * 设置消息内容，即编码
	 * @param mStrCode
	 */
	public void setCode(String mStrCode) {
		m_strCode = mStrCode;
	}
	private String m_strTime;
	private String m_strCode;
}
