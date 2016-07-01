/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import com.digispherecorp.api.enterprise.mapi.core.MailMessage;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Ikram
 */
public abstract class AbstractMailProfile implements IMailProfile{

    private Long id;
    private String mailFrom = "dummy";
    private String sentTo = "dummy";
    private String username = "dummy";
    private String password = "dummy";
    private String host = "dummy";
    private int port = 0;
    private String transport = "transport";
    private String store = "store";
    private boolean debug = false;
    private Queue<MailMessage> mailMessageQueue = new ConcurrentLinkedQueue<>();

    /**
     *
     */
    public AbstractMailProfile() {
        super();
    }

    /**
     *
     * @param mailFrom
     * @param sentTo
     * @param username
     * @param password
     * @param host
     * @param port
     * @param transport
     * @param store
     * @param debug
     */
    public AbstractMailProfile(String mailFrom, String sentTo, String username, String password, String host, int port, String transport, String store, boolean debug) {
        this.mailFrom = mailFrom;
        this.sentTo = sentTo;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.transport = transport;
        this.store = store;
        this.debug = debug;
    }

        
    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getMailFrom() {
        return mailFrom;
    }

    @Override
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Override
    public String getSentTo() {
        return sentTo;
    }

    @Override
    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String getStore() {
        return store;
    }

    @Override
    public void setStore(String store) {
        this.store = store;
    }

    @Override
    public String getTransport() {
        return transport;
    }

    @Override
    public void setTransport(String transport) {
        this.transport = transport;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setMailMessageQueue(Queue<MailMessage> mailMessageQueue) {
        this.mailMessageQueue = mailMessageQueue;
    }

    @Override
    public Queue<MailMessage> getMailMessageQueue() {
        return mailMessageQueue;
    }
}
