package cn.ommiao.base.exception;

public abstract class AbstractGlobalActionException extends RuntimeException {

    public AbstractGlobalActionException(){
        super();
    }

    public AbstractGlobalActionException(String message){
        super(message);
    }

}
