package resolver;

import log.LogImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import resolver.model.ResultModel;

import configure.SysConfig;

import cache.CacheLRU;

/**
 * @author Administrator
 * 高级解析器，采用单例模式，加入了日志记录和缓存功能
 */
public class ExtenedResolver {

	private static ExtenedResolver resolver = null;
	private ResolverImp resImp = null;
	private CacheLRU<String> cache = null;
	private static final Log comlog = LogFactory.getLog(ExtenedResolver.class);
	private static final LogImp quelog = LogImp.getLogger();
	private static SysConfig conf = new SysConfig();
	//可补充预测缓存相关内容
	private ExtenedResolver()
	{
		resImp = new ResolverImp(conf);
		cache = new CacheLRU<String>();
	}
	/**
	 * 获取单例高级解析器的引用
	 * @return 高级解析器的引用
	 */
	public synchronized static ExtenedResolver getInstance()
	{
		if(resolver == null)
		{
			resolver = new ExtenedResolver();
		}
		return resolver;
	}
	/**
	 * 设置解析器的配置
	 * @param sysconfig
	 */
	public static void configure(SysConfig sysconfig)
	{
		conf = sysconfig;
	}
	/**
	 * 根据SID和RID，完成解析功能
	 * @param sid
	 * @param rid
	 * @return 解析成功，返回url或ip；否则返回null
	 */
	public String getInfoByCode(String sid,String rid)
	{
		quelog.log(String.valueOf(System.currentTimeMillis()), sid+rid);
		String info = cache.readCache(sid+rid);
		if(info == null)
		{
			ResultModel rm = resImp.resolver_getInfoByCode(sid, rid);
			if( rm != null) 
			{
				info = rm.getURL();
				cache.writeCache(sid+rid,info, rm.getExpiredTime());
			}
		}
		return info;
		
	}

}
