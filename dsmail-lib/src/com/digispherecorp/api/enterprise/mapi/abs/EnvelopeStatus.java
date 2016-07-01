package com.digispherecorp.api.enterprise.mapi.abs;

import com.digispherecorp.api.enterprise.mapi.core.Envelope;




public class EnvelopeStatus {

    /**
     *
     */
    public static enum Status {

        NEW,
        QUEUED,
        SENT,
        FAIL,
        EXPIRED,
        BUSY;

        public String getCode() {
            return name();
        }
    }

    private EnvelopeStatus() {
    }

    /**
     *
     * @param e
     */
    public static void queueEnvelope(Envelope e) {
        if (!e.getStatusCode().equals(Status.BUSY.getCode())) {
            e.setStatusCode(Status.QUEUED.getCode());
        }

    }
}
