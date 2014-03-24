package configure.configureInterface;

public interface ConfigerInterface {
	
	/**
	 * Get the regular expression used to convert the SID of the code
	 * @return	the regular expression
	 */
	String resolver_getSIDConvertRule();
	
	/**
	 * Set the path of the configuration file
	 * @param path	the path of the configuration file
	 */
	void resolver_init(String path);
}
