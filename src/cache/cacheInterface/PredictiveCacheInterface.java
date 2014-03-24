package cache.cacheInterface;

public interface PredictiveCacheInterface {
	
	/**
	 * Tell whether use the predictive cache or not
	 * @return	true if use the predictive cache, false otherwise
	 */
	boolean resolver_usePredictiveCache();
	
	/**
	 * set to determine whether use the predictive cache or not
	 * @param use	true if use the predictive cache, false otherwise
	 */
	void resolver_usePredictiveCache(boolean use);
}
