package vn.com.phuclocbao.exception;
public class BusinessException extends Exception {
	private static final long serialVersionUID = -761847296176135602L;

	public BusinessException(int errorCode) {
		super(String.valueOf(errorCode));
		this.errorCode = errorCode;

	}
	public BusinessException(String errorCode) {
		super(String.valueOf(errorCode));
	}
	private int errorCode;
	private StackTraceElement[] stackTrace;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace.clone();
	}
}
