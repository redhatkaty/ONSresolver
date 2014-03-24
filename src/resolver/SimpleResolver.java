package resolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import resolver.model.ResultModel;

import configure.SysConfig;

public class SimpleResolver {
	private ResolverImp resolver = new ResolverImp();
	private static final Log comlog = LogFactory.getLog(SimpleResolver.class);
	/**
	 * default constructor creating SimpleResolver objects
	 */
	public SimpleResolver()
	{
	}
	/**
	 * constructor creating SimpleResolver objects using SysConfig objects
	 * @param conf
	 */
	public SimpleResolver(SysConfig conf)
	{
		configure(conf);
	}
	/**
	 * creating a configuration objects
	 * @param conf
	 */
	public void configure(SysConfig conf)
	{
		resolver.configure(conf);
	}
	/**
	 * Transform the ID to URL with the regular expression translated by SID
	 * @param sid
	 * @param rid
	 * @return
	 */
	public String getInfoByCode(String sid,String rid) {
		ResultModel rm = resolver.resolver_getInfoByCode(sid, rid);
		if(rm != null) return rm.getURL();
		else
			return null;
	}
	/**
	 * Transform the ID(sid+rid) to domain name( a midterm result) 
	 * @param sid
	 * @param rid
	 * @return
	 */
	public String getRidDomainNameByCode(String sid,String rid)
	{
		return resolver.resolver_getRidDomainNameByCode(sid, rid);
	}
}
