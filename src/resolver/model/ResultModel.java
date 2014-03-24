package resolver.model;

/**
 * @author Administrator
 * 存储解析过程的中间结果
 */
public class ResultModel {
	private String url;
	private long expiredTime;
	
	/**
	 * 获得 字符串
	 * @return
	 */
	public String getURL()
	{
		return url;
	}
	/**
	 * 设置字符串
	 * @param url
	 */
	public void setURL(String url)
	{
		this.url = url;
	}
	/**
	 * 获取该结果的到期时间
	 * @return
	 */
	public long getExpiredTime()
	{
		return expiredTime;
	}
	/**
	 * 设置结果的到期时间
	 * @param time
	 */
	public void setExpiredTime(long time)
	{
		this.expiredTime = time;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return url+" "+String.valueOf(expiredTime);
	}
}
