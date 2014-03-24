package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import resolver.SimpleResolver;

import configure.PropName;
import configure.SysConfig;

public class TestSimpleResolver {

	private static final Log comlog = LogFactory.getLog(TestExtendResolver.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String logProperPath = "log4j.properties";
		PropertyConfigurator.configure(logProperPath);
		
		SysConfig conf = new SysConfig("SysConfig.properties") ;		//设置配置文件路径
		conf.setProperty(PropName.SERVICE_TYPE, "NS");
		SimpleResolver resolver = new SimpleResolver(conf);		//实例化通用查询类
		//茶几
		String SID = "100010111001000000101110100111000010111000010000000000001000";
		String RID = "0001001000111001000110000110000110010011100010111001100111101101";
		System.out.println(resolver.getInfoByCode(SID, RID));
		//柜子
//		String SID = "100010111001000000101110100111000010111000010000000000001010";
//		String RID = "000100110011100100011000000000101001111101100010000111010100110110100111011011011101011101101001";
		//茶几
//		String SID = "100010111001000000101110100111000010111000010000000000001000";
//		String RID = "0001001000111001000110000110000110010011100010111001100111101101";
	    //沙发
//		String SID = "100010111001000000101110100111000010111000010000000000000100";
//    	String RID = "001100000011010110100101001101111100100001111000100100000000000000000000000000000000000000000001";
		//桌子
    	//String SID = "100010111001000000101110100111000010111000010000000000000110";
    	//String RID = "100001110110011000000100101011000010010110100111001100000100101100111011011110100011101011010101001110110111010101111011010111000111111000000000000110111100000101101101011001110100111011001011000100101101000";
	
    	//System.out.println(resolver.getInfoByCode(SID, RID));

	}

}
