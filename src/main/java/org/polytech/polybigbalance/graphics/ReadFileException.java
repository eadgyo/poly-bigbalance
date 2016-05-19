package org.polytech.polybigbalance.graphics;

public class ReadFileException extends Exception
{
    private String fileName;

    public ReadFileException(Throwable cause, String fileName)
    {
        super(cause);
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return this.fileName;
    }

    @Override
    public String getMessage()
    {
        return this.getCause().getMessage();
    }
}
