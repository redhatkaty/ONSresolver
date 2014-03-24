package cache.cacheInterface;

public interface CacheInterface {
	
	/**
	 * Read the cache and return the hit result
	 * @param key	the query
	 * @return	if the cache hit, return the result, return null otherwise
	 */
	String readCache(String key);
	
	/**
	 * Write the record to the cache
	 * @param key	the query
	 * @param value	the result of the query
	 * @return	if write successfully return true, return false otherwise
	 */
	boolean writeCache(String key, String value);
	
	/**
	 * Determine whether the cache is full
	 * @return if full, return true, false otherwise
	 */
	boolean isFull();
	
	/**
	 * Replace the useless cache with a new record if the cache is full
	 * @param key	the query
	 * @param value	the result
	 * @return if replace successfully, return true, false otherwise
	 */
	boolean replaceCache(String key, String value);
	
	/**
	 * Remove a record from the cache
	 * @param key	the query
	 * @return	if remove successfully, return the result, null otherwise
	 */
	String removeRecord(String key);
	
	/**
	 * Update the TTL of the record
	 * @param key	the query	
	 * @param TTL	the time to live of the record
	 * @return	if update successfully, return true, false otherwise
	 */
	boolean updateTTL(String key, int TTL);
	
	/**
	 * Move the record of the cache to the tail
	 * @param key
	 * @return
	 */
	boolean moveToCacheTail(String key);
}
