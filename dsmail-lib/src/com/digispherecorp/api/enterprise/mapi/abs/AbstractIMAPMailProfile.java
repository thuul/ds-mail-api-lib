/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

/**
 *
 * @author Wal
 */
public abstract class AbstractIMAPMailProfile extends AbstractMailProfile {

    private String imapUser = "dummy";
    private String imapPassword = "dummy";
    private String imapHost = "dummy";
    private int imapPort = 143;
    private boolean storeCopy = false;

    /**
     *
     */
    public AbstractIMAPMailProfile() {
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
     * @param imapUser
     * @param imapPassword
     * @param imapHost
     * @param imapPort
     * @param debug
     * @param storeCopy
     */
    public AbstractIMAPMailProfile(String mailFrom, String sentTo, String username, String password, String host, int port, String transport, String store, String imapUser, String imapPassword, String imapHost, int imapPort, boolean debug, boolean storeCopy) {
        super(mailFrom, sentTo, username, password, host, port, transport, store, debug);
        this.imapUser = imapUser;
        this.imapPassword = imapPassword;
        this.imapHost = imapHost;
        this.imapPort = imapPort;
        this.storeCopy = storeCopy;
    }

    /**
     *
     * @return String
     */
    public String getImapUser() {
        return imapUser;
    }

    /**
     *
     * @param imapUser
     */
    public void setImapUser(String imapUser) {
        this.imapUser = imapUser;
    }

    /**
     *
     * @return String
     */
    public String getImapPassword() {
        return imapPassword;
    }

    /**
     *
     * @param imapPassword
     */
    public void setImapPassword(String imapPassword) {
        this.imapPassword = imapPassword;
    }

    /**
     *
     * @return String
     */
    public String getImapHost() {
        return imapHost;
    }

    /**
     *
     * @param imapHost
     */
    public void setImapHost(String imapHost) {
        this.imapHost = imapHost;
    }

    /**
     *
     * @return int
     */
    public int getImapPort() {
        return imapPort;
    }

    /**
     *
     * @param imapPort
     */
    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

    /**
     *
     * @return boolean
     */
    public boolean isStoreCopy() {
        return storeCopy;
    }

    /**
     *
     * @param storeCopy
     */
    public void setStoreCopy(boolean storeCopy) {
        this.storeCopy = storeCopy;
    }
}
