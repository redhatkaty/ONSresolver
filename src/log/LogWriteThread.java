package log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import log.model.Message;

/**
 * @author Administrator
 * 不断记录查询请求的日志线程
 */
public class LogWriteThread extends Thread {

	private static final Log comlog = LogFactory.getLog(LogWriteThread.class);
	private Queue<Message> m_queue;
	private String m_logFilePath;
	public LogWriteThread(Queue<Message> queue,String logFilePath)
	{
		m_queue = queue;
		m_logFilePath = logFilePath;
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{
		//打开文件
		FileOutputStream fout = null;
		FileChannel foc = null;
		
		try {
			//打开文件的方式，追加还是覆盖，要根据日志策略来决定
			fout = new FileOutputStream(m_logFilePath,true);
			foc = fout.getChannel();
		} catch (FileNotFoundException e) {
			comlog.info("Failed to open the log file");
			getNotWrite();
		}
		getAndWrite(foc);
	}
	private void getNotWrite()
	{
		//只取消息，不写入文件
		while(true)
		{
			synchronized(m_queue)
			{
				if(m_queue.isEmpty())
				{
					try {
						m_queue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						comlog.debug("log wirte thread stop");
						return;
					}
				}else{
					m_queue.poll();
				}
			}
		}
	}
	private void getAndWrite(FileChannel foc)
	{
		//取消息且写入日志文件
		while(true)
		{
			synchronized(m_queue)
			{
				if(m_queue.isEmpty())
				{
					try {
						m_queue.wait();
					} catch (Exception e) {
						try {
							foc.close();
						} catch (IOException e1) {
							comlog.info("Failed to close log channel");
						}
						comlog.debug(e.toString());
						comlog.debug("log wirte thread stop");
						return;
					}
				}else{
					Message msg = m_queue.poll();
					StringBuffer sb = new StringBuffer();
					sb.append(msg.getTime());
					sb.append(' ');
					sb.append(msg.getCode());
					sb.append("\r\n");
					CharBuffer cb = CharBuffer.wrap(sb);
					Charset cs = Charset.forName("gbk");
					ByteBuffer bb = cs.encode(cb);
					try {
						foc.write(bb);
					} catch (IOException e) {
						comlog.info("Faied to write into log file");
					}
					
				}
			}
		}
	}
}
