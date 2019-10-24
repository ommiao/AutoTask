package cn.ommiao.base.exception;

public class ShellCommandException extends AbstractGlobalActionException {

    public ShellCommandException(){
        super();
    }

    public ShellCommandException(String message){
        super(message);
    }

}
