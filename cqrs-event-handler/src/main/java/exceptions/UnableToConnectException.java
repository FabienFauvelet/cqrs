package exceptions;

public class UnableToConnectException extends Exception{

    public UnableToConnectException(String message)
    {
        super(message);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
