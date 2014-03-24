package log.logInterface;

import log.model.Message;

public interface LogInterface {
	/**
	 * Log the record and the result that the user request
	 * @param message	the information of the record
	 */
	void log(Message message);
}
