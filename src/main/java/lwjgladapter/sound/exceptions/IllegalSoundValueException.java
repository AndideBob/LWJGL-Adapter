package lwjgladapter.sound.exceptions;

import lwjgladapter.datatypes.LWJGLAdapterException;

public class IllegalSoundValueException extends LWJGLAdapterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8560221129639686093L;

	public IllegalSoundValueException(String message) {
		super(message);
	}

	public IllegalSoundValueException(Throwable cause) {
		super(cause);
	}

	public IllegalSoundValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalSoundValueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
