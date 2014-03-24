package resolver.resolverInterface;

import resolver.model.ResultModel;

public interface CodeResolverInterface {
	
	/**
	 * Given the SID and RID of a code, resolve to get the result
	 * @param SID	SID of the code, used to identify what standard the code belong to
	 * @param RID	RID of the code, used to distinguish each code of the same standard, it's unique
	 * @return		the information related to the code, mostly URL or IP
	 */
	ResultModel resolver_getInfoByCode(String SID, String RID);
	
	/**
	 * Get the regular expression with SID, which is used to transform the RID to a domain name
	 * @param SID	SID of the code, used to identify what standard the code belong to
	 * @return		the regular expression
	 */
	ResultModel resolver_getRegexBySID(String SID);
	
	/**
	 * Transform the SID to domain name with the regular expression
	 * @param SID		SID of the code, used to identify what standard the code belong to
	 * @param regexp	regular expression used to transform the SID
	 * @return			the domain name of SID
	 */
	String resolver_SID2DomainName(String SID, String regexp);
	
	/**
	 * Transform the RID to domain name with the regular expression
	 * @param RID		RID of the code, used to distinguish each code of the same standard, it's unique
	 * @param regexp	regular expression used to transform the RID
	 * @return			the domain name of RID
	 */
	String resolver_RID2DomainName(String RID, String regexp);
	
	/**
	 * Get the regular expression with the domain name of SID, which is used to transform the RID
	 * @param SIDDomainName	the domain name of SID
	 * @return	the regular expression 
	 */
	ResultModel resolver_SIDDomainName2Regex(String SIDDomainName);
	
	/**
	 * Get the result of the domain name of RID
	 * @param RIDDomainName
	 * @return	the result of the domain name of RID
	 */
	String resolver_RIDDomainName2Info(String RIDDomainName);
}
