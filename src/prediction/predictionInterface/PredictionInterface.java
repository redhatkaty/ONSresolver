package prediction.predictionInterface;

import java.util.Vector;

public interface PredictionInterface {
	/**
	 * Predict the proper requests after the current request sequence,
	 * the size of the return set depends on the prediction size.
	 * @param curReqSeq	the current request sequence
	 * @return
	 */
	Vector<String> predict(Vector<String> curReqSeq);
	
	/**
	 * Set the prediction size, which determine how many requests to predict
	 * @param size	the prediction size
	 */
	void setPredictSize(int size);
}
