package configure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * the class is used for system configuration
 *
 */
public class SysConfig {
	private Properties propertie = new Properties();
	private FileInputStream inputFile;
	private static final Log comlog = LogFactory.getLog(SysConfig.class);

	/**
	 * 构选配置对象
	 * @param path 配置文件路径
	 */
	public SysConfig(String path) {
		initDefaultValue();
		try {
			inputFile = new FileInputStream(path);
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			comlog.info("cannot find the file" + path
					+ "use the default config");
		} catch (IOException ex) {
			comlog.info("IOException");
		}
	}
	/**
	 * default constructor
	 */
	public SysConfig()
	{
		initDefaultValue();
	}
	/**
	 * Giving the default value for configuration if user dosen't provide values
	 */
	private void initDefaultValue()
	{
		propertie.setProperty(PropName.DELIMETOR, ".");
		propertie.setProperty(PropName.SID_NAME, "oid");
		propertie.setProperty(PropName.DOMAINNAME, "tnsroot.cn");
		propertie.setProperty(PropName.RID_REGEX, "!\\^([^!]+)!([^!]+)!");
		propertie.setProperty(PropName.REGEX_FOR_DOMAIN, "\\$([\\d]+)\\.");
		propertie.setProperty(PropName.URL_REGEX, "!\\^\\.\\*\\$!([^!]+)!");
		propertie.setProperty(PropName.SID_SERVER, "218.241.108.59");
		propertie.setProperty(PropName.RID_SERVER, "218.241.108.63");
		propertie.setProperty(PropName.SERVICE_TYPE, "ONS");
		propertie.setProperty(PropName.DOT, "00101110");
		propertie.setProperty(PropName.CODE_COUNT, "4");// parts divided by dote
	}
	/**
	 * 设置配置中某个配置项的值
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value)
	{
		propertie.setProperty(key, value);
	}
	/**
	 * Get the value of the input key. If the key doesn't in the config file
	 * then return an empty string.
	 * 
	 * @param key
	 * @return
	 */
	public String getStringValue(String key) {
		String value = "";
		if (key == null || key.equals("") || key.equals("null")) {
			return value;
		} else {
			value = this.propertie.getProperty(key, "");
		}

		return value;
	}

	/**
	 * Get the int value of the input key. If the key doesn't in the config file
	 * or the value isn't a digit then return 0.
	 * 
	 * @param key
	 * @return
	 */
	public int getIntValue(String key) {
		int value = 0;
		try {
			String strValue = getStringValue(key);
			if (key.equals(""))
				value = 0;
			else
				value = Integer.valueOf(strValue);
		} catch (NumberFormatException e) {
			value = 0;
			System.out
					.println("Error: happens when read config file to translate to int for [KEY]="
							+ key);
			System.out.println(e.getMessage());
		}

		return value;
	}

	/**
	 * 获取SID转换规则
	 * @return
	 */
	public String getSIDConvertRule() {
		// TODO Auto-generated method stub
		
		String str_8BitBinaryDot = getStringValue("DOT");
		String str_BinaryCode = "([01]+)";
		String SIDConvertRule = str_BinaryCode;
		int CodeCount = getIntValue("codecount");
		int i;
		for (i = 1; i < CodeCount; i++)
			SIDConvertRule += str_8BitBinaryDot + str_BinaryCode;
		return SIDConvertRule;
	}
}
