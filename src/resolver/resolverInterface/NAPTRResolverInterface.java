package resolver.resolverInterface;

import java.util.Vector;

import org.xbill.DNS.NAPTRRecord;


public interface NAPTRResolverInterface {
	
	/**
	 * Resolve the domain name to get the regexp field of the NAPTR record,
	 * maybe there are several NAPTR records related to the domain name, select
	 * the proper one.
	 * @param name	the domain name to resolve
	 * @param sever_addr  the sever address may be used for SID of RID query
	 * @return	the content of the regexp field of the resolved NAPTR record
	 */
	NAPTRRecord resolver_getRecord(String name,String sever_addr);
	/**
	 * Resolve the domain name to get the related NAPTR records, maybe there are
	 * several records, get all of them.
	 * @param name	the domain name to resolve
	 * @return	all the related NAPTR records
	 */
	Vector<NAPTRRecord> resolver_getNAPTRRecords(String name,String severaddr);
	
	/**
	 * Select the most proper records among all the related records
	 * and return the content of the regexp field of the record.
	 * @param records	all the NAPTR records related to one domain name
	 * @return	the content of the regexp field of the most proper NAPTR record
	 */
	NAPTRRecord resolver_selectNAPTRRecord(Vector<NAPTRRecord> records);
	
}

