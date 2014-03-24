package resolver;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.xbill.DNS.NAPTRRecord;

import configure.PropName;
import configure.SysConfig;
import resolver.NAPTRResolverImp;
import resolver.model.ResultModel;
import resolver.resolverInterface.CodeResolverInterface;

public class ResolverImp implements CodeResolverInterface {

	///this parameters are set by the property text"SysConfig.properties"
	private static String DELIMETOR = ".";
	private static String SIDName = "oid";
	private static String projectDomainName = "tnsroot.cn";
	private static String regexForRegex = "!\\^([^!]+)!([^!]+)!";
	private static String regexForDomain = "\\$([\\d]+)\\.";
	private static String str_8BitBinaryDot = "00101110"; // 00101110 stands for a dot
	private static String str_BinaryCode = "([01]+)";
	private static String sidRegex = str_BinaryCode + str_8BitBinaryDot + str_BinaryCode
	+ str_8BitBinaryDot + str_BinaryCode + str_8BitBinaryDot
	+ str_BinaryCode;
	private static String urlRegex = "!\\^\\.\\*\\$!([^!]+)!";
	private static NAPTRResolverImp naptrgetter = new NAPTRResolverImp("ONS");
	private static String SID_SERVER="218.241.108.59";
	private static String RID_SERVER="218.241.108.63";
	
	private static final Log comlog = LogFactory.getLog(ResolverImp.class);
	
	/**
	 * default constructor creating a ResolverImp object
	 */
	public ResolverImp()
	{
		
	}
	/**
	 * constructor creating a ResolverImp object using Sysconfig objec
	 * @param conf
	 */
	public ResolverImp(SysConfig conf)
	{
		this.configure(conf);
	}
	@Override
	public String resolver_RID2DomainName(String RID, String regexp) {
		// TODO Auto-generated method stub
		String failedResult = null;
		if(RID == null|| regexp == null) return failedResult;
		if(RID.isEmpty() || regexp.isEmpty() ) return failedResult;
		StringBuilder domainName_str = new StringBuilder(50);
		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		String ridDivisionRegex_str = "";
		String domainNamePattern_str = "";
		ArrayList<String> ary_dividedPart = new ArrayList<String>();
		try {
			pattern = compiler.compile(regexForRegex,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		} catch (MalformedPatternException e) {
			return failedResult;
		}
		PatternMatcher matcher = new Perl5Matcher();
		//get the regular expression for translating the RID regex and domain name regex
		if (matcher.contains(regexp, pattern)) {
			MatchResult result = matcher.getMatch();
			ridDivisionRegex_str = result.group(1);
			domainNamePattern_str = result.group(2);
		} else {
			return failedResult;
		}
		//
		try {
			pattern = compiler.compile(ridDivisionRegex_str,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		} catch (MalformedPatternException e) {
			return failedResult;
		}
		if (matcher.contains(RID, pattern)) {
			MatchResult result = matcher.getMatch();
			int groupNum = result.groups();
			for (int i = 1; i < groupNum; i++) {
				try{
					ary_dividedPart.add(new BigInteger(result.group(i), 2).toString());
				}catch(NumberFormatException e)
				{
					return failedResult;
				}
			}
		} else {
			return failedResult;
		}
		//Resolve the domain name pattern
		ArrayList<Integer> ary_dividedPartIndex = new ArrayList<Integer>();
		PatternMatcherInput domainNamePattern_input = new PatternMatcherInput(domainNamePattern_str);
		try {
			pattern = compiler.compile(regexForDomain,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		} catch (MalformedPatternException e) {
			return failedResult;
		}
		int offset = 0;
		while(matcher.contains(domainNamePattern_input, pattern))
		{
			MatchResult matchResult = matcher.getMatch();
			try{
				int groupNum = matchResult.groups();
				for(int i=1;i<groupNum;i++)
				{
					ary_dividedPartIndex.add(Integer.valueOf(matchResult.group(i)));
				}
				offset = domainNamePattern_input.getCurrentOffset();
			}catch(NumberFormatException e)
			{
				return failedResult;
			}
			
		}
		String postDomainNamePattern = domainNamePattern_str.substring(offset);
		Iterator<Integer> it = ary_dividedPartIndex.iterator();
		while(it.hasNext())
		{
			int diviedPartIndex = it.next()-1;
			if(diviedPartIndex < 0 || diviedPartIndex >= ary_dividedPart.size())
			{
				return failedResult;
			}
			else
			{
				domainName_str.append(ary_dividedPart.get(diviedPartIndex));
				domainName_str.append(DELIMETOR);
			}
		}
		domainName_str.append(postDomainNamePattern);
		return domainName_str.toString();
	}
	@Override
	public String resolver_RIDDomainName2Info(String RIDDomainName) {
		// TODO Auto-generated method stub
		String failedResult = null;
		if(RIDDomainName == null) return failedResult;
		if(RIDDomainName.isEmpty()) return failedResult;
		String result;
		String urlstr="";
		try {
			NAPTRRecord rec = naptrgetter.resolver_getRecord(RIDDomainName, RID_SERVER);
			if(rec == null)
			{
				return failedResult;
			}
			urlstr = URLDecoder.decode(rec.getRegexp(), "UTF-8");
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			comlog.error("happens in resolver_getNAPTRRecords method when get NAPTRRecods.\n"
					+"when decode regex using UTF-8");
			return failedResult;
		}
		comlog.debug(urlstr);
		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		try {
			pattern = compiler.compile(urlRegex, Perl5Compiler.CASE_INSENSITIVE_MASK);
		} catch (MalformedPatternException e) {
			comlog.debug("error in regex create");
			return failedResult;
		}
		PatternMatcher matcher = new Perl5Matcher();
		if(matcher.contains(urlstr, pattern))
		{
			MatchResult matchResult = matcher.getMatch();
			result = matchResult.group(1);
			return result;
		}
		return failedResult;
	}

	@Override
	public String resolver_SID2DomainName(String SID, String regexp) {
		// TODO Auto-generated method stub
		String failedResult = null;
		if(SID == null || regexp == null) return failedResult;
		if(SID.isEmpty() || regexp.isEmpty()) return failedResult;
		StringBuilder domainName_str = new StringBuilder(50);
		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		try {
			pattern = compiler.compile(regexp,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		} catch (MalformedPatternException e) {
			return failedResult;
		}
		PatternMatcher matcher = new Perl5Matcher();
		if (matcher.contains(SID, pattern)) {
			// get the matching result
			MatchResult result = matcher.getMatch();
			try {
				for (int i = result.groups() - 1; i > 0; i--) {
					domainName_str.append(Integer.valueOf(result.group(i), 2));
					domainName_str.append(DELIMETOR);
				}
				domainName_str.append(SIDName);
				domainName_str.append(DELIMETOR);
				domainName_str.append(projectDomainName);
				return domainName_str.toString();
			} catch (NumberFormatException e) {
				return failedResult;
			}
		}
		return failedResult;
	}

	@Override
	public ResultModel resolver_SIDDomainName2Regex(String SIDDomainName) {
		// TODO Auto-generated method stub
		ResultModel failedResult = null;
		ResultModel result = new ResultModel();
		if(SIDDomainName==null ) return failedResult;
		String urlstr="";
		try {
			
			NAPTRRecord record = naptrgetter.resolver_getRecord(SIDDomainName, SID_SERVER);
			if(record == null) return failedResult;
			urlstr = record.getRegexp();
			urlstr = URLDecoder.decode(urlstr, "UTF-8");
			result.setExpiredTime(System.currentTimeMillis() + record.getTTL());
			result.setURL(urlstr);
			comlog.debug(result);
			return result;
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			comlog.error("happens in resolver_getNAPTRRecords method when get NAPTRRecods.\n"
					+"when decode regex using UTF-8");
			return failedResult;
		}
	}

	@Override
	public ResultModel resolver_getInfoByCode(String SID, String RID) {
		// TODO Auto-generated method stub
		if(SID == null || RID == null) return null;
		ResultModel result = this.resolver_getRegexBySID(SID);
		if(result == null) return null;
		String ridRegex = result.getURL();
		String ridDomainName = this.resolver_RID2DomainName(RID, ridRegex);
		comlog.debug(ridDomainName);
		String info = resolver_RIDDomainName2Info(ridDomainName);
		if(info == null) return null;
		result.setURL(info);
		comlog.debug(result);
		return result;
	}
	
	/**
	 * Given the SID and RID of a code, resolve to get the RID domain
	 * @param SID
	 * @param RID
	 * @return RID domain if find successfully, else return null
	 */
	public String resolver_getRidDomainNameByCode(String SID, String RID) {
		if(SID == null || RID == null) return null;
		// TODO Auto-generated method stub
		ResultModel result = this.resolver_getRegexBySID(SID);
		if(result == null) return null;
		String ridRegex = result.getURL();
		String ridDomainName = this.resolver_RID2DomainName(RID, ridRegex);
		comlog.debug(ridDomainName);
		return ridDomainName;
	}
	
	@Override
	public ResultModel resolver_getRegexBySID(String SID) {
		// TODO Auto-generated method stub
		String sidDomainName = this.resolver_SID2DomainName(SID, sidRegex);
		comlog.debug(sidDomainName);
		if(sidDomainName == null) return null;
		ResultModel result = this.resolver_SIDDomainName2Regex(sidDomainName);
		comlog.debug(result);
		return result;
	}
	/**
	 * configuration according to the objects that needs configuring 
	 * @param conf
	 */
	public void configure(SysConfig conf)
	{
		DELIMETOR = conf.getStringValue(PropName.DELIMETOR);
		SIDName = conf.getStringValue(PropName.SID_NAME);
		projectDomainName = conf.getStringValue(PropName.DOMAINNAME);
		regexForRegex = conf.getStringValue(PropName.RID_REGEX);//regex for translating the "regex" field
		regexForDomain = conf.getStringValue(PropName.REGEX_FOR_DOMAIN);//regex for translating the domain
		
		str_8BitBinaryDot = conf.getStringValue(PropName.DOT); // 00101110 stands for a dot
		str_BinaryCode = "([01]+)";
		sidRegex = str_BinaryCode;
		sidRegex = conf.getSIDConvertRule();
		urlRegex = conf.getStringValue(PropName.URL_REGEX);
		naptrgetter = new NAPTRResolverImp(conf.getStringValue(PropName.SERVICE_TYPE));
		SID_SERVER= conf.getStringValue(PropName.SID_SERVER);
		RID_SERVER= conf.getStringValue(PropName.RID_SERVER);
	}
}
