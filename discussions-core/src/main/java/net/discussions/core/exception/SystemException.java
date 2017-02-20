package net.discussions.core.exception;

public class SystemException extends Throwable {

    private static final long serialVersionUID = 1L;

    public SystemException() {
    }

    public SystemException(String arg0) {
        super(arg0);
    }

    public SystemException(Throwable arg0) {
        super(arg0);
    }

    public SystemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
