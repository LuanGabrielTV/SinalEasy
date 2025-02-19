package com.sinalez.sinaleasy_back.domains;

public enum SignalStatus {
    PENDING(0),
    STARTED(1),
    PAUSED(2),
    FINISHED(3);

    private final int code;

    SignalStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SignalStatus fromCode(int code) {
        for (SignalStatus status : SignalStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
