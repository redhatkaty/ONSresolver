package test;

import configure.SysConfig;

public class TestGetSIDRule {

	/**
	 * @param args
	 */
	public static String resolver_getSIDConvertRule() {
		// TODO Auto-generated method stub
		SysConfig config = new SysConfig("SysConfig.properties");
		String str_8BitBinaryDot=config.getStringValue("DOT");
		String str_BinaryCode="([01]+)";
		String SIDConvertRule=str_BinaryCode;
		int CodeCount=config.getIntValue("codecount");
		int i;
		for(i=1;i<CodeCount;i++)
			SIDConvertRule+=str_8BitBinaryDot+str_BinaryCode;
		return SIDConvertRule;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(resolver_getSIDConvertRule());
	}

}
