package lwjgladapter.datatypes;

public class LWJGLAdapterException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2997552882429336593L;


    public LWJGLAdapterException(String message) {
        super(message);
    }

    public LWJGLAdapterException(Throwable cause) {
        super(cause);
    }

    public LWJGLAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public LWJGLAdapterException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
