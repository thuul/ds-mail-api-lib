/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.core;

import com.digispherecorp.api.enterprise.mapi.abs.IEnvelope;
import java.sql.*;

/**
 *
 * @author Wal
 * @param <T>
 */
public class Envelope<T> implements IEnvelope<T> {

    private String storeid;
    private String message;
    private String[] addresses;
    private String subject;
    private Timestamp sentdate;
    private String[] attachmentPaths;
    private String statusCode;
    private long size;

    private T t;

    public Envelope() {
    }

    public Envelope(String storeid, String message, String[] addresses, String subject, String statusCode, Timestamp sentdate) {
        this.storeid = storeid;
        this.message = message;
        this.addresses = addresses;
        this.subject = subject;
        this.statusCode = statusCode;
        this.sentdate = sentdate;
    }

    public Envelope(String storeid, String message, String[] addresses, String subject, String statusCode, String[] attachpaths, Timestamp sentdate) {
        this.storeid = storeid;
        this.message = message;
        this.addresses = addresses;
        this.subject = subject;
        this.statusCode = statusCode;
        this.attachmentPaths = attachpaths;
        this.sentdate = sentdate;
    }

    public Envelope(T t) {
        this.t = t;
    }

    @Override
    public String[] getAddresses() {
        return addresses;
    }

    @Override
    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getStoreid() {
        return storeid;
    }

    @Override
    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String[] getAttachmentPaths() {
        return attachmentPaths;
    }

    @Override
    public void setAttachmentPaths(String[] attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }

    @Override
    public Timestamp getSentdate() {
        return sentdate;
    }

    @Override
    public void setSentdate(Timestamp sentdate) {
        this.sentdate = sentdate;
    }

    @Override
    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public void setT(T t) {
        this.t = t;
    }

    @Override
    public T getT() {
        return t;
    }

}
