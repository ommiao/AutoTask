package cn.ommiao.base.exception;

public class WaitTooLongException extends AbstractWidgetActionException {

    public WaitTooLongException() {
        super();
    }

    public WaitTooLongException(String message) {
        super(message);
    }
}
