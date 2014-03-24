package cache.model;

/**
 * @author Administrator
 * 链表结点类型
 * @param <T>
 */
public class ListNode<T> {
	private ListNode<T> prev;
	private ListNode<T> next;
	private T data;
	/**
	 * 默认构造函数
	 */
	public ListNode()
	{
	}
	/**
	 * 指定值的构造函数
	 * @param value
	 */
	public ListNode(T value)
	{
		init();
		data = value;
	}
	/**
	 * 指定值、前驱、后继的构造函数
	 * @param value 数据值
	 * @param prv	前驱结点的引用
	 * @param nxt	后继结点的引用
	 */
	public ListNode(T value,ListNode<T> prv,ListNode<T> nxt)
	{
		data = value;
		prev = prv;
		next = nxt;
	}
	private void init()
	{
		prev = null;
		next = null;
		data = null;
	}
	/**
	 * 获取前驱引用
	 * @return 如果有，则返回前驱结点的引用；否则返回null
	 */
	public ListNode<T> getPrev()
	{
		return prev;
	}
	/**
	 * 获取后继结点引用
	 * @return 如果有，后继结点的引用;否则返回null
	 */
	public ListNode<T> getNext()
	{
		return next;
	}
	/**
	 * 获取结点的value
	 * @return 数据值
	 */
	public T getData()
	{
		return data;
	}
	/**
	 * 设置结点的前驱
	 * @param new_prev
	 */
	public void setPrev(ListNode<T> new_prev)
	{
		prev = new_prev;
	}
	/**
	 * 设置结点的后继
	 * @param new_next
	 */
	public void setNext(ListNode<T> new_next)
	{
		next = new_next;
	}
	/**
	 * 设置结点的数据值
	 * @param new_data
	 */
	public void setData(T new_data)
	{
		data = new_data;
	}
	
}
