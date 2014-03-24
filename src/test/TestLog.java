package test;
import log.LogImp;
import log.model.Message;

public class TestLog extends Thread {

	public void run()
	{
		LogImp  logger = LogImp.getLogger();
		for(int i=0;i<100;i++)
		{
			Message m=new Message(String.valueOf(System.currentTimeMillis()),String.valueOf(i));
			logger.log(m); 
		}
	}
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//String logProperPath = "log4j.properties";
		//PropertyConfigurator.configure(logProperPath);
		Thread t1= new TestLog();
		Thread t2= new TestLog();
		Thread.sleep(5000);
		t1.start();
		t2.start();
	}

}
