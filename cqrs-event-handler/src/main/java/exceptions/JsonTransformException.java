package exceptions;

public class JsonTransformException extends Exception{

    public JsonTransformException()
    {
        super();
    }

    public JsonTransformException(String msg)
    {
        super(msg);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }
}
