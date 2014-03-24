package test;

import cache.CacheLRU;

public class TestCache {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		CacheLRU<String> cache = new CacheLRU<String>(20);
		for(int i=0;i<20;i++)
		{
			char c = (char) ('a'+i);
			cache.writeCache(String.valueOf(c), String.valueOf(i), System.currentTimeMillis()+5);
		}
		System.out.println(cache);
		Thread.sleep(6);
		cache.writeCache(String.valueOf('u'),String.valueOf(21),System.currentTimeMillis()+60);
		cache.writeCache(String.valueOf('w'),String.valueOf(22),System.currentTimeMillis()+60);
		System.out.println(cache);

	}

}
