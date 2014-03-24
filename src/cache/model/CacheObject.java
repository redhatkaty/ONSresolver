package cache.model;

/**
 * @author ljh
 *封装了key,value,TTL的缓存元素对象
 * @param <T>
 */
public class CacheObject<T> {

    private String key; 
    private T value; 
    private long timeOut;//过期时间
    private boolean expired = false; //过期状态
    /**
     * 默认构造函数
     */
    public CacheObject() { 
            super(); 
    } 
            
    /**
     * 带参数的构造函数
     * @param key
     * @param value
     * @param timeOut
     */
    public CacheObject(String key, T value, long timeOut) { 
            this.key = key; 
            this.value = value; 
            this.timeOut = timeOut; 
    		if(timeOut < System.currentTimeMillis())
    		{
    			expired = true;
    		}
    } 
    /**
     * 获取key
     * @return key
     */
    public String getKey() { 
        return key; 

	}

	/**
	 * 获取到期时间
	 * @return 到期时间
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * 获取value
	 * @return value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * set value
	 * @param string value
	 */
	public void setKey(String string) {
		key = string;
	}

	/**
	 * 设置到期时间
	 * @param lt
	 */
	public void setTimeOut(long lt) {
		timeOut = lt;
	}

	/**
	 * set value
	 * @param new_value
	 */
	public void setValue(T new_value) {
		value = new_value;
	}

	/**
	 * 判断该元素是否过期
	 * @return true 已过期,false 未过期
	 */
	public boolean isExpired() {
		if(timeOut < System.currentTimeMillis())
		{
			expired = true;
		}
		else
		{
			expired = false;
		}
		return expired;
	}

	/**
	 * 设置元素是否过期的标识
	 * @param b
	 */
	public void setExpired(boolean b) {
		expired = b;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o)
	{
	    if (!(o instanceof CacheObject))
	           return false;
	    CacheObject co = (CacheObject)o;
	    if(this.key.equals(co.getKey()))
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return key+":"+value+":"+isExpired();
	}

}
