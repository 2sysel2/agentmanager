package cz.muni.fi.pv168.gmiterkosys;

public class ServiceFailureException extends RuntimeException {

    public ServiceFailureException(String message) {
        super(message);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}