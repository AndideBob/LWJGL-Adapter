package lwjgladapter.physics.collision.exceptions;

import lwjgladapter.datatypes.LWJGLAdapterException;

public class CollisionNotSupportedException extends LWJGLAdapterException {

	private static final long serialVersionUID = -3605452121525092877L;

	public CollisionNotSupportedException(String message) {
		super(message);
	}

	public CollisionNotSupportedException(Throwable cause) {
		super(cause);
	}

	public CollisionNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CollisionNotSupportedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
