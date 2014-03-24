package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import resolver.ExtenedResolver;
import configure.SysConfig;

public class TestExtendResolver {

	private static final Log comlog = LogFactory.getLog(TestExtendResolver.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//使用ExtendResolver示例
		
		SysConfig conf = new SysConfig("SysConfig.properties");
		ExtenedResolver.configure(conf);
		ExtenedResolver res = ExtenedResolver.getInstance();
		String SID = "100010111001000000101110100111000010111000010000000000001000";
		String RID = "0001001000111001000110000110000110010011100010111001100111101101";
		comlog.debug(res.getInfoByCode(SID, RID));

	}

}
