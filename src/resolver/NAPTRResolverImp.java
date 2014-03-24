package resolver;

import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

//import com.sun.jndi.dns.*;

//���������ļ�

//import resolver.model.NAPTRRecord;
import resolver.resolverInterface.NAPTRResolverInterface;
import configure.PropName;


public class NAPTRResolverImp implements NAPTRResolverInterface {
	private static final Log comlog = LogFactory.getLog(NAPTRResolverImp.class);
	//public static String SERVICE_TYPE = "";//ONS
	private String service_type ="";
	/**
	 * default constructor creating a ResolverImp object using service type
	 */
	public NAPTRResolverImp(String type)
	{
		service_type = type;
	}
	/**
	 * setting service type of NAPTR record 
	 */
	public void setServiceType(String type)
	{
		service_type = type;
	}
	/**
	 * getting service type of NAPTR record 
	 * @return
	 */
	public String getServiceType()
	{
		return service_type;
	}
	
	@Override
	public NAPTRRecord resolver_getRecord(String name, String severaddr) {
		// TODO Auto-generated method stub
		NAPTRRecord record=null;
		Vector<NAPTRRecord> Records= null;
		
		Records=resolver_getNAPTRRecords(name,severaddr);
		if(Records.isEmpty()) return null;
		record=resolver_selectNAPTRRecord(Records);
		return record;
	}	

	@Override
	public Vector<NAPTRRecord> resolver_getNAPTRRecords(String name, String severaddr) {
		// TODO Auto-generated method stub
		comlog.debug("name:"+name);
		comlog.debug("addr:"+severaddr);
		comlog.debug("serviceType:"+service_type);
		Vector<NAPTRRecord> NAPTRRecords = new Vector<NAPTRRecord>();
//		String[] path={"218.241.108.59"};
//		NAPTRRecord Record= null; 
//		String SeverAddr="dns://"+severaddr;
//		DirContext ctx = null;
		
		try {
			
//			// Hashtable for environmental information
//			Hashtable env = new Hashtable();
//			// Specify which class to use for our JNDI provider
//			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
//			env.put(Context.PROVIDER_URL, SeverAddr);//DNS��������ַ�ɲ�����
//
//			String dns_attributes[] = { "NAPTR" };
//
//			// Get a reference to a directory context
//			ctx = new InitialDirContext(env);
//			
//			Attributes attrs1 = ctx.getAttributes(name, dns_attributes);//��������DNS��������naptr������ԡ�attrIds - Ҫ���������Եı�ʶ��null ָʾӦ�ü����������ԣ�������ָʾ��Ӧ�����κ����ԡ� 
			
			Lookup lookUpAgent = new Lookup(name, Type.NAPTR);
			// Initial a DNS resolver
			Resolver resolv = new SimpleResolver(severaddr);
			lookUpAgent.setResolver(resolv);
			Record [] records = lookUpAgent.run();
			if(records == null)
			{
				comlog.debug("Failed to get NAPTR record from: "+severaddr);
				return NAPTRRecords;
			}
			for (int i = 0; i < records.length; i++) {			
					NAPTRRecord record = (NAPTRRecord)records[i];
					if(record.getService().equalsIgnoreCase(this.service_type))
					{
						NAPTRRecords.addElement(record);
					}
				//ARecord record = (ARecord) records[i];
				
				comlog.debug("TTL:"+record.getTTL()+"\nRegexp"+record.getRegexp());
			}
			comlog.debug("after filtering:\n"+NAPTRRecords.toString());
			

			if (NAPTRRecords.size() == 0) {
				comlog.error("happens in resolver_getNAPTRRecords method when get NAPTRRecods.\n"
						+ "\tHost has none of the specified attributes [" + service_type + "] for the domain name [" + name + "]");
				return NAPTRRecords;
			} else {
//				NamingEnumeration vals=attrs1.getAll();
//				String onerecord="";
//				while (vals.hasMore()) {
//					Attribute attr = (Attribute) vals.next();
//					NamingEnumeration attrval = attr.getAll();
//					while(attrval.hasMore())
//					{
//						onerecord = attrval.next().toString();
//						Record = null;
//						Record=ExtractInfoFromNAPTR(URLDecoder.decode(onerecord, "UTF-8"));//convert to UTF-8 code
//						if(Record != null) Records.addElement(Record);
//					}
//				}
//			}	
//		} catch (Exception e) {
//			comlog.error("happens in resolver_getNAPTRRecords method when get NAPTRRecods.");
//			comlog.debug(e.getMessage());
//	
			return NAPTRRecords;
			}//end else
		}//end try
		catch (TextParseException e) {
			// TODO Auto-generated catch block
			comlog.error("happens in resolver_getNAPTRRecords method,when get NAPTRRecods.\n"
					+"\tlookup method failed.");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			comlog.error("happens in resolver_getNAPTRRecords method when get NAPTRRecods.\n"
					+ "\tcannot find domain name [" + severaddr + "]");
		}
		return NAPTRRecords;
	}

	@Override
	public NAPTRRecord resolver_selectNAPTRRecord(Vector<NAPTRRecord> records) {
		NAPTRRecord record=null;
		//NAPTRRecord Record=new NAPTRRecord();
		Vector<NAPTRRecord> TempRecords = new Vector<NAPTRRecord>();
		
		if(records.isEmpty())
			return record;
		else{
			if(records.size()==1)
				return records.get(0);
			else
			{
				int present_lowest_pref=0;
				int i;
//				while(TempRecords.size()==0){
//					for(i=0;i<records.size();i++)
//					{
//						if(records.get(i).getPreference()==present_lowest_pref)
//							TempRecords.addElement(records.get(i));
//					}	
//					present_lowest_pref++;
//				}
//				
//				present_lowest_pref=records.get(0).getPreference();
				for(i=0;i<records.size();i++)
				{
					if(records.get(i).getPreference()<present_lowest_pref)
					{
						TempRecords.removeAllElements();
						TempRecords.addElement(records.get(i));
					}
					if(records.get(i).getPreference()==present_lowest_pref)
						TempRecords.addElement(records.get(i));
				}
				Random random = new Random();
				return TempRecords.get(random.nextInt(TempRecords.size()));
			}	
		}	
		// TODO Auto-generated method stub
	}
}
