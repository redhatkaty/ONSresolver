package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import resolver.FindResolver;
import configure.SysConfig;

public class TestFindResolver {

	private static final Log comlog = LogFactory.getLog(TestExtendResolver.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String logProperPath = "log4j.properties";
		PropertyConfigurator.configure(logProperPath);
		
		SysConfig conf = new SysConfig("SysConfig.properties");
		String SID = "100010111001000000101110100111000010111000010000000000001000";
		String RID = "0001001000111001000110000110000110010011100010111001100111101101";
		FindResolver resolver = new FindResolver(conf);
		resolver.setFindServerAddr("218.241.108.63");
		resolver.setFindServiceType("DS");
		comlog.debug(resolver.getAllRecordSContent(SID, RID));
	}

}
