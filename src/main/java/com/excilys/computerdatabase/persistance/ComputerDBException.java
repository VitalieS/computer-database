package main.java.com.excilys.computerdatabase.persistance;

public class ComputerDBException extends RuntimeException {

    /**
     */
    private static final long serialVersionUID = -1L;

    /**
     */
    public ComputerDBException() {
        super();
    }

    /**
     * @param message message
     */
    public ComputerDBException(String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause cause
     */
    public ComputerDBException(String message, Throwable cause) {
        super(message, cause);
        System.out.println(message);
    }

    /**
     * @param cause cause
     */
    public ComputerDBException(Throwable cause) {
        super(cause);
    }

}
