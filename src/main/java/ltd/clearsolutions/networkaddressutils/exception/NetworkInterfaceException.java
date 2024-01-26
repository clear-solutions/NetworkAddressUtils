package ltd.clearsolutions.networkaddressutils.exception;

public class NetworkInterfaceException extends RuntimeException {

    public NetworkInterfaceException(String message) {
        super(message);
    }

    public NetworkInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
