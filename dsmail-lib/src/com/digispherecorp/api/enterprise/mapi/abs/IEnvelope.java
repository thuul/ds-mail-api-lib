/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author walle
 * @param <T>
 */
public interface IEnvelope<T> extends Serializable {

    /**
     *
     * @return String[]
     */
    String[] getAddresses();

    /**
     *
     * @return String[]
     */
    String[] getAttachmentPaths();

    /**
     *
     * @return String
     */
    String getMessage();

    /**
     *
     * @return Timestamp
     */
    Timestamp getSentdate();

    /**
     *
     * @return String
     */
    String getStatusCode();

    /**
     *
     * @return String
     */
    String getStoreid();

    /**
     *
     * @return String
     */
    String getSubject();

    /**
     *
     * @param addresses
     */
    void setAddresses(String[] addresses);

    /**
     *
     * @param attachmentPaths
     */
    void setAttachmentPaths(String[] attachmentPaths);

    /**
     *
     * @param message
     */
    void setMessage(String message);

    /**
     *
     * @param sentdate
     */
    void setSentdate(Timestamp sentdate);

    /**
     *
     * @param statusCode
     */
    void setStatusCode(String statusCode);

    /**
     *
     * @param storeid
     */
    void setStoreid(String storeid);

    /**
     *
     * @param subject
     */
    void setSubject(String subject);

    /**
     *
     * @param t
     */
    void setT(T t);

    /**
     *
     * @return T
     */
    T getT();

}
