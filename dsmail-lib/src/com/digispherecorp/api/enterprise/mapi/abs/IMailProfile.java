/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import com.digispherecorp.api.enterprise.mapi.core.MailMessage;
import java.io.Serializable;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author walle
 */
public interface IMailProfile extends Serializable {

    /**
     *
     * @return String
     */
    String getHost();

    /**
     *
     * @return Long
     */
    Long getId();

    /**
     *
     * @return String
     */
    String getMailFrom();

    /**
     *
     * @return Queue
     */
    Queue<MailMessage> getMailMessageQueue();

    /**
     *
     * @return String
     */
    String getPassword();

    /**
     *
     * @return int
     */
    int getPort();

    /**
     *
     * @return String
     */
    String getSentTo();

    /**
     *
     * @return String
     */
    String getStore();

    /**
     *
     * @return String
     */
    String getTransport();

    /**
     *
     * @return String
     */
    String getUsername();

    /**
     *
     * @return boolean
     */
    boolean isDebug();

    /**
     *
     * @return Queue
     */
    Queue<MailMessage> sendOutgoingMailMessages();

    /**
     *
     * @param sameAttachment
     * @return Queue
     */
    Queue<MailMessage> sendOutgoingMailMessages(boolean sameAttachment);

    /**
     *
     * @param debug
     */
    void setDebug(boolean debug);

    /**
     *
     * @param host
     */
    void setHost(String host);

    /**
     *
     * @param id
     */
    void setId(Long id);

    /**
     *
     * @param mailFrom
     */
    void setMailFrom(String mailFrom);

    /**
     *
     * @param mailMessageQueue
     */
    void setMailMessageQueue(Queue<MailMessage> mailMessageQueue);

    /**
     *
     * @param password
     */
    void setPassword(String password);

    /**
     *
     * @param port
     */
    void setPort(int port);

    /**
     *
     * @param sentTo
     */
    void setSentTo(String sentTo);

    /**
     *
     * @param store
     */
    void setStore(String store);

    /**
     *
     * @param transport
     */
    void setTransport(String transport);

    /**
     *
     * @param username
     */
    void setUsername(String username);

    /**
     *
     * @param list
     */
    void updateMailMessageSourceStatus(List<String> list);
    
}
