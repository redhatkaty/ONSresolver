package resolver;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.xbill.DNS.NAPTRRecord;

import resolver.model.ResultModel;

import configure.PropName;
import configure.SysConfig;

public class FindResolver {

	private ResolverImp resolver = new ResolverImp();
	private NAPTRResolverImp  naptr = new NAPTRResolverImp("DS");
	private String serverAddr = "218.241.108.63";
	private static final Log comlog = LogFactory.getLog(FindResolver.class);
	/**
	 * default constructor creating a FindResolver object
	 */
	public FindResolver()
	{
	}
	/**
	 * constructor creating a FindResolver object using SysConfig object 
	 */
	public FindResolver(SysConfig conf)
	{
		configure(conf);
	}
	/**
	 * configuration
	 */
	public void configure(SysConfig conf)
	{
		resolver.configure(conf);
	}
	/**
	 * setting the find server address
	 * @param ipaddr the address used for setting
	 */
	public void setFindServerAddr(String ipaddr)
	{
		serverAddr = ipaddr;
	}
	/**
	 * getting the find server address
	 * @return
	 */
	public String getFindServerAddr()
	{
		return serverAddr;
	}
	/**
	 * setting the find server service type
	 * @param serviceType the service type used for setting
	 */
	public void setFindServiceType(String serviceType)
	{
		naptr.setServiceType(serviceType);
	}
	/**
	 * getting the find server service type
	 * @return
	 */
	public String getFindServiceType()
	{
		return naptr.getServiceType();
	}
	
	/**
	 * Transform the ID(SID+RID) to domain name with the regular expression translated by SID
	 * @param SID
	 * @param RID
	 * @return
	 */
	private String convertID2DomainName(String SID,String RID)
	{
		ResultModel rm = resolver.resolver_getRegexBySID(SID);
		if( rm == null) return null;
		String ridRegex = rm.getURL();
		String ridDomainName = resolver.resolver_RID2DomainName(RID, ridRegex);
		comlog.debug(ridDomainName);
		return ridDomainName;
	}
	/**
	 * Transform the ID(SID+RID) to NAPTR records, return all records with type"DS"
	 * @param SID
	 * @param RID
	 * @return
	 */
	public Vector<String> getAllRecordSContent(String SID,String RID)
	{
		Vector<String> vec = new Vector<String>();
		String ridDomainName = convertID2DomainName(SID,RID);
		if(ridDomainName == null) return null;
		Vector<NAPTRRecord> recordVec=naptr.resolver_getNAPTRRecords(ridDomainName, serverAddr);
		for(int i=0;i< recordVec.size();i++)
		{
			vec.add(recordVec.get(i).getRegexp());
		}
		return vec;
	}

}
