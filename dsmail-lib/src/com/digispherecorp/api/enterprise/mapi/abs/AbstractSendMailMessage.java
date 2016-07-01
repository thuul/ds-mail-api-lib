/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import com.digispherecorp.api.enterprise.mapi.core.MailMessage;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Wal
 * @param <T>
 */
public abstract class AbstractSendMailMessage<T> implements ISendMailMessage<T> {

    protected AbstractIMAPMailProfile mailProfile;
    protected Session session;
    protected final Flags uflag;
    protected MailMessage message;
    protected MimeBodyPart bodyPart;
    protected MimeMessage mimeMessage;
    protected MimeMultipart mimeMultiPart;
    protected Store store;
    protected Folder folder;
    protected int messageSent;
    protected T envelope;

    /**
     *
     * @param mailProfile
     * @param session
     * @param uflag
     * @param message
     */
    public AbstractSendMailMessage(AbstractIMAPMailProfile mailProfile, Session session, Flags uflag, MailMessage message) {
        this.mailProfile = mailProfile;
        this.session = session;
        this.uflag = uflag;
        this.message = message;
    }    

    @Override
    public String getIMAPFolder() {
        return "IMAP Store";
    }
}
