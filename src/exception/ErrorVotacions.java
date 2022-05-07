package exception;

public class ErrorVotacions extends Exception{
	private String code;

	  public ErrorVotacions(String code) {
	        this.code = code;
	    }

	    public ErrorVotacions(String message, String code) {
	        super(code + ": " + message);
	        this.code = code;
	    }

	    public ErrorVotacions(String message, Throwable cause, String code) {
	        super(code + ": " + message, cause);
	        this.code = code;
	    }

	    public ErrorVotacions(Throwable cause, String code) {
	        super(cause);
	        this.code = code;
	    }

	    public ErrorVotacions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
	        super(code + ": " + message, cause, enableSuppression, writableStackTrace);
	        this.code = code;
	    }
}
