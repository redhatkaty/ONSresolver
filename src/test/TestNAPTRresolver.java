package test;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.xbill.DNS.NAPTRRecord;

import resolver.NAPTRResolverImp;

public class TestNAPTRresolver {
	private static final Log comlog = LogFactory.getLog(NAPTRResolverImp.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String logProperPath = "log4j.properties";
		PropertyConfigurator.configure(logProperPath);
		//SysConfig conf = new SysConfig("SysConfig.properties");
		NAPTRResolverImp resolver = new NAPTRResolverImp("ONS");
		
//		/***testing for method Vector<NAPTRRecord> resolver_getNAPTRRecords(String name)***/
//		String name="9148909.549267.10008.epc.tnsroot.cn";//63
//		//String name="65544.156.16.2.oid.tnsroot.cn";//59
//		String severaddr="218.241.108.63";
//		
//		Vector<NAPTRRecord> Records=new Vector<NAPTRRecord>();
//		Records=resolver.resolver_getNAPTRRecords(name, severaddr);
//		comlog.debug(Records.toString());
		
//		/***testing for method String resolver_selectNAPTRRecord(Vector<NAPTRRecord> records)***/
//		Vector<NAPTRRecord> Records=new Vector<NAPTRRecord>();
//		for(int c=0;c<=3;c++){
//			Records.addElement(new NAPTRRecord());
//			Records.get(c).setPreference(c/2);
//			Records.get(c).setRegexp("record"+c);
//		}
//		String regex=resolver_selectNAPTRRecord(Records);
//		System.out.println(regex);
//	
		/***testing for method NAPTRRecord resolver_getRecord(String name, String severaddr)***/
		String name="9148909.549267.10008.epc.tnsroot.cn";//63
		//String name="65544.156.16.2.oid.tnsroot.cn";//59
		String severaddr="218.241.108.63";
		
		//Vector<NAPTRRecord> Records=new Vector<NAPTRRecord>();
		NAPTRRecord record=resolver.resolver_getRecord(name, severaddr);
		comlog.debug(record.toString());
	}
}
