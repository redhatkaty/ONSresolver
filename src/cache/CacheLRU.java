package cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import cache.model.CacheObject;
import cache.model.LinkedList;
import cache.model.ListNode;

/**
 * @author ljh
 *key类型为String，value类型为T的带有TTL值的LRU缓存
 * @param <T>  存储类型参数
 */
public class CacheLRU<T> {
	private HashMap<String, ListNode<CacheObject<T>>>  map= null;
	private LinkedList<CacheObject<T> > lastAccessedList = null;
	private PriorityQueue<CacheObject<T> > heap = null;
	private class CacheComparator implements Comparator<CacheObject<T>>{

		@Override
		public int compare(CacheObject<T> o1, CacheObject<T> o2) {
			// TODO Auto-generated method stub
			if(o1.getTimeOut() < o2.getTimeOut())
			{
				return -1;
			}
			else if(o1.getTimeOut() > o2.getTimeOut())
			{
				return 1;
			}
			else
				return 0;					
		}
		
	};
	//private LiskedList<>
	private int capacity=1000;
	private int size=0;
	/**
	 * 构造函数
	 * @param cap 缓存容量
	 */
	public CacheLRU(int cap)
	{
		this.capacity = cap;
		init();
	}
	/**
	 * 默认构函数
	 */
	public CacheLRU()
	{
		init();
	}
	private void init()
	{
		map = new HashMap<String,ListNode<CacheObject<T>>>();
		lastAccessedList = new LinkedList< CacheObject<T>>();
		heap = new PriorityQueue<CacheObject<T>>(capacity,new CacheComparator());
	}
	
	/**
	 * 判断缓存是否已满
	 * @return true表示 缓存已满
	 */
	public boolean isFull() {
		// TODO Auto-generated method stub
		if(this.capacity == this.size) return true;
		else return false;
	}
	/**
	 * 查找缓存中key对应的value
	 * @param key
	 * @return 如果key存在，则返回key所对应的value，否则返回null
	 */
	public T readCache(String key) {
		// TODO Auto-generated method stub
		ListNode<CacheObject<T>> p =map.get(key);
		if(p == null)
		{
			return null;
		}
		else
		{
			if(p.getData().isExpired())
			{
				return null;
			}
			else
			{
				lastAccessedList.delete(p);
				lastAccessedList.addToTail(p);
				return p.getData().getValue();
			}
		}
	}
	/**
	 * 向缓存中写入数据
	 * @param key
	 * @param value
	 * @param timeOut TTL值
	 */
	public void writeCache(String key, T value, long timeOut) {
		ListNode<CacheObject<T>> p =map.get(key);
		if( p == null )
		{
			if(isFull())
			{
				//如果缓存已满，先择替最久未被访问元素或是已失效元素
				p = this.lastAccessedList.getLast();
				CacheObject<T>  top = heap.peek();
				if(top.isExpired())
				{
					p = map.get(top.getKey());
				}
				//从三个结构中删除
				map.remove(p.getData().getKey());
				this.lastAccessedList.delete(p);
				this.heap.remove(p.getData());
				this.size--;
			}
			CacheObject<T> co = new CacheObject<T>(key,value,timeOut);
			p = new ListNode<CacheObject<T>>(co);
			//添加新结点
			this.lastAccessedList.addToHead(p);
			heap.add(p.getData());
			map.put(key, p);
			this.size++;
		}
		else
		{
			p.getData().setValue(value);
			p.getData().setTimeOut(timeOut);
			heap.remove(p.getData());
			heap.add(p.getData());
		}
	}
	/**
	 *  清空缓存
	 */
	public void clear()
	{
		map.clear();
		this.lastAccessedList.clear();
		heap.clear();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		
		return heap.toString();
	}
}
