package com.adjuster.reportmodule.Exception;


public class ReportModuleException extends RuntimeException {
    public ReportModuleException() {
        super();
    }

    public ReportModuleException(String message) {
        super(message);
    }

    public ReportModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportModuleException(Throwable cause) {
        super(cause);
    }

    protected ReportModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
