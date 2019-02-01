package lwjgladapter.sound.exceptions;

import lwjgladapter.datatypes.LWJGLAdapterException;

public class AudioNotInMemoryException extends LWJGLAdapterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4091654931620177767L;

	public AudioNotInMemoryException(String message) {
		super(message);
	}

	public AudioNotInMemoryException(Throwable cause) {
		super(cause);
	}

	public AudioNotInMemoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public AudioNotInMemoryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
