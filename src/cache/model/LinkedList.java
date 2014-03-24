package cache.model;

/**
 * @author Administrator
 *双向链表类
 * @param <T>
 */
public class LinkedList<T> {

	private ListNode<T> head;
	private ListNode<T> iter;
	/**
	 * 默认构造函数
	 */
	public LinkedList()
	{
		init();
	}
	private void init()
	{
		head = new ListNode<T>();
		head.setNext(head);
		head.setPrev(head);
		iter = head;
	}
	/**
	 * 判断链表是否为空
	 * @return true表示链表为空,否则不为空
	 */
	public boolean isEmpty()
	{
		if(head == head.getNext()) return true;
		else return false;
	}
	/**
	 * 将p所指结点插入到cur所指结点之后
	 * @param cur
	 * @param p
	 * @return true表示插入成功，false表示插入失败
	 */
	public boolean insertCurNext(ListNode<T> cur,ListNode<T> p)
	{
		if(cur!= null && p != null)
		{
			p.setNext(cur.getNext());
			p.setPrev(cur);
			cur.getNext().setPrev(p);
			cur.setNext(p);
			return true;
		}
		return false;
	}
	/**
	 * 将p所指结点插入到cur所指结点之前
	 * @param cur
	 * @param p
	 * @return true表示插入成功，false表示插入失败
	 */
	public boolean insertCurPrev(ListNode<T> cur,ListNode<T> p)
	{
		if(cur != null && p!= null)
		{
			p.setNext(cur);
			p.setPrev(cur.getPrev());
			cur.getPrev().setNext(p);
			cur.setPrev(p);
			return true;
		}
		return false;
	}
	/**
	 * 将cur所指结点从链表中删除
	 * @param cur
	 * @return true表示删除成功，false表示删除失败
	 */
	public boolean delete(ListNode<T> cur)
	{
		if(cur == null) return false;
		if(cur == head) return false;
		if(cur == iter)
		{
			iter = cur.getNext();
		}
		cur.getNext().setPrev(cur.getPrev());
		cur.getPrev().setNext(cur.getNext());
		return true;
	}
	
	/**
	 * 将value值插入链表头部
	 * @param value
	 */
	public void addToHead(T value)
	{
		ListNode<T> p = new ListNode<T>(value);
		addToHead(p);
	}
	/**
	 * 将p所指结点插入链表头部
	 * @param p
	 */
	public void addToHead(ListNode<T> p)
	{
		if(p!= null)
		{
			insertCurNext(head,p);
		}
	}
	/**
	 * 将value值插入链表尾部
	 * @param p
	 */
	public void addToTail(ListNode<T> p)
	{
		if(p!= null)
		{
			insertCurPrev(head,p);
		}
	}
	/**
	 * 将p所指结点插入链表尾部
	 * @param value
	 */
	public void addToTail(T value)
	{
		ListNode<T> p = new ListNode<T>(value);
		addToTail(p);
	}
	/**
	 * 获得第一个数据结点引用
	 * @return  如果链表为空，返回null;否则返回第一个数据结点引用
	 */
	public ListNode<T> getFirst()
	{
		if(this.isEmpty()) return null;
		else return head.getNext();
	}
	/**
	 * 获得链最后一个数据结点引用
	 * @return 如果链表为空，返回null;否则返回最后一个数据结点引用
	 */
	public ListNode<T> getLast()
	{
		if(this.isEmpty()) return null;
		else return head.getPrev();
	}
	/**
	 * 清空链表
	 */
	public void clear()
	{
		head.setNext(head);
		head.setPrev(head);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("[");
		ListNode<T> p = head.getNext();
		while(p != head)
		{
			sb.append(p.getData().toString());
			sb.append(",");
			p = p.getNext();
		}
		if(sb.charAt(sb.length()-1)== ',')
		{
			sb.setCharAt(sb.length()-1, ']');
		}
		else
		{
			sb.append(']');
		}
		return sb.toString();
	}

}
