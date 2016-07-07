/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.core;

import com.digispherecorp.api.enterprise.mapi.abs.AbstractIMAPMailProfile;
import com.digispherecorp.api.enterprise.mapi.abs.EnvelopeStatus;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.mail.Flags;
import javax.mail.Session;

/**
 *
 * @author Wal
 */
public class MailEngine {

    private Properties properties;
    private AbstractIMAPMailProfile mailProfile;
    private Session session;
    private Flags uflag;
    private BlockingQueue<MailMessage> envelopeQueue = new LinkedBlockingQueue<>();
    private static volatile MailEngine instance = null;

    private MailEngine(AbstractIMAPMailProfile mailProfile) {
        this.mailProfile = mailProfile;
        initEngine();
    }

    private MailEngine() {
        initEngine();
    }

    private void initEngine() {
        this.properties = System.getProperties();
        this.uflag = new Flags();
        properties.put("mail.from", mailProfile.getMailFrom());
        properties.put("mail.smtp.user", mailProfile.getUsername());
        properties.put("mail.smtp.host", mailProfile.getHost());
        properties.put("mail.smtp.port", mailProfile.getPort());
        properties.put("mail.transport.smtp", mailProfile.getTransport());
        properties.put("mail.store.protocol", mailProfile.getStore());
        properties.put("mail.debug", mailProfile.isDebug());
        properties.put("password", mailProfile.getPassword());
        properties.put("mail.imap.user", mailProfile.getImapUser());
        properties.put("mail.imap.host", mailProfile.getImapHost());
        properties.put("mail.imap.port", mailProfile.getImapPort());

        this.session = Session.getInstance(properties, null);
    }

    public static MailEngine newInstance(AbstractIMAPMailProfile profile) {
        synchronized (MailEngine.class) {
            if (instance == null) {
                if (profile != null) {
                    instance = new MailEngine(profile);
                } else {
                    instance = new MailEngine();
                }
            }
        }
        return instance;
    }

    public static MailEngine getInstance() {
        return instance;
    }

    public Envelope sendMailMessage(MailMessage mail) {
        return new EnvelopeSendMailMessage(mailProfile, session, uflag, mail).sendMailMessage();
    }

    public void sendEnvelopes(boolean sameAttachment) {
        envelopeQueue.addAll(mailProfile.sendOutgoingMailMessages(sameAttachment));
    }

    public void sendEnvelope(Envelope e) {
        e.setStatusCode(EnvelopeStatus.Status.QUEUED.getCode());
        envelopeQueue.add(new MailMessage(e));
    }

    public BlockingQueue<MailMessage> getEnvelopeQueue() {
        return envelopeQueue;
    }

    public void setEnvelopeQueue(BlockingQueue<MailMessage> envelopeQueue) {
        this.envelopeQueue = envelopeQueue;
    }

    public AbstractIMAPMailProfile getMailProfile() {
        return mailProfile;
    }

    public void setMailProfile(AbstractIMAPMailProfile mailProfile) {
        this.mailProfile = mailProfile;
    }
}
