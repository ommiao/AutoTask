package cn.ommiao.base.exception;

public abstract class AbstractWidgetActionException extends RuntimeException {

    public AbstractWidgetActionException(){
        super();
    }

    public AbstractWidgetActionException(String message){
        super(message);
    }

}
