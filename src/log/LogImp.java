package log;

import java.util.LinkedList;
import java.util.Queue;
import log.logInterface.LogInterface;
import log.model.Message;

/**
 * @author Administrator
 * 记录查询日志
 */
public class LogImp implements LogInterface {

	private static LogImp logger = null;
	private static final int LIMIT =1000;
	
	private Queue<Message> m_queue = null;
	private Thread m_writeThread = null;
	//RID searching log path
	private String m_logFilePath = "log.txt";
	/**
	 * 类方法，获取日志记录器引用
	 * @return 查询日志记录器
	 */
	public static synchronized LogImp getLogger()
	{
		if(logger == null)
		{
			logger = new LogImp();
		}
		return logger;
	}
	private LogImp(){
		m_queue = new LinkedList<Message>();
		m_writeThread = new LogWriteThread(m_queue,m_logFilePath);
		m_writeThread.start();
		
	}
    protected void finalize() 
    { 

      try {
		super.finalize();
		if(m_writeThread == null)
		{
			m_writeThread.interrupt();
		}
	   } catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    } 

	@Override
	public void log(Message message) {
		// TODO Auto-generated method stub
		synchronized(m_queue){
			//constrain the number of elements in the queue
			if(m_queue.size()> LIMIT)
			{
				m_queue.poll();
			}
			m_queue.offer(message);
			m_queue.notifyAll();
		}
	}
	/**
	 * 向日志中写入查询记录
	 * @param time 查询的时间
	 * @param code 查询的编码
	 */
	public void log(String time,String code)
	{
		log(new Message(time,code));
	}

}
