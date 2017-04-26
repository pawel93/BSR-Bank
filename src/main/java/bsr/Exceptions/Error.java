package bsr.Exceptions;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Paweł on 2017-01-28.
 */
@XmlRootElement
public class Error
{

    @XmlElement(name = "error")
    private String error;
    public Error(){
    }

    public Error(String error)
    {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
