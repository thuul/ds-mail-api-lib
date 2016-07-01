/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.core;

import com.digispherecorp.api.enterprise.mapi.abs.AbstractIMAPMailProfile;
import com.digispherecorp.api.enterprise.mapi.abs.AbstractSendMailMessage;
import com.digispherecorp.api.enterprise.mapi.abs.EnvelopeStatus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.FolderAdapter;
import javax.mail.event.FolderEvent;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.TransportAdapter;
import javax.mail.event.TransportEvent;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Wal
 */
public class EnvelopeSendMailMessage extends AbstractSendMailMessage<Envelope> {

    public EnvelopeSendMailMessage(AbstractIMAPMailProfile mailProfile, Session session, Flags uflag, MailMessage message) {
        super(mailProfile, session, uflag, message);
    }

    @Override
    public Envelope sendMailMessage() {
        Envelope e = null;
        try {
            bodyPart = new MimeBodyPart();
            bodyPart.setText(((Envelope) message.getEnvelope()).getMessage());
            e = buildMailMessage();
        } catch (MessagingException ex) {
            Logger.getLogger(EnvelopeSendMailMessage.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        return e;
    }

    @Override
    public Envelope buildMailMessage() {
        final Envelope env = message.getEnvelope();
        try {
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom();
            mimeMessage.setRecipients(Message.RecipientType.TO, buildAddreses(env.getAddresses()).toArray(new Address[env.getAddresses().length]));

            mimeMessage.setSubject(env.getSubject());
            mimeMultiPart = new MimeMultipart();
            bodyPart.setDisposition(MimeBodyPart.INLINE);
            mimeMultiPart.addBodyPart(bodyPart);
            if (!message.getMimeBodyPartList().isEmpty()) {
                Iterator<MimeBodyPart> iterator = message.getMimeBodyPartList().iterator();
                while (iterator.hasNext()) {
                    mimeMultiPart.addBodyPart(iterator.next());
                }
            }
            mimeMessage.setContent(mimeMultiPart);
            mimeMessage.setFlags(uflag, true);
            mimeMessage.setFlag(Flags.Flag.USER, true);

            if (mailProfile.isStoreCopy()) {
                store = session.getStore();
                if ((mailProfile.getImapUser() != null && !mailProfile.getImapUser().isEmpty())
                        && (mailProfile.getImapPassword() != null && !mailProfile.getImapPassword().isEmpty())
                        && (mailProfile.getImapHost() != null && !mailProfile.getImapHost().isEmpty())) {
                    Logger.getLogger(EnvelopeSendMailMessage.class.getName()).info(mailProfile.getImapUser());
                    Logger.getLogger(EnvelopeSendMailMessage.class.getName()).info(mailProfile.getImapPassword());
                    Logger.getLogger(EnvelopeSendMailMessage.class.getName()).info(mailProfile.getImapHost());
                    Logger.getLogger(EnvelopeSendMailMessage.class.getName()).info(String.valueOf(mailProfile.getImapPort()));
                    store.connect(mailProfile.getImapHost(), mailProfile.getImapPort(), mailProfile.getImapUser(), mailProfile.getImapPassword());
                } else {
                    store.connect(mailProfile.getHost(), mailProfile.getUsername(), mailProfile.getPassword());
                }

                folder = store.getFolder(getIMAPFolder());
                if (!folder.exists()) {
                    folder.create(Folder.HOLDS_MESSAGES);
                }

                folder.addFolderListener(new FolderAdapter() {

                    @Override
                    public void folderCreated(FolderEvent e) {
                        super.folderCreated(e);
                        if (e.getType() == FolderEvent.CREATED) {
                            System.out.println(e.getFolder().getFullName());
                        }
                    }

                });

                folder.addMessageCountListener(new MessageCountAdapter() {
                    @Override
                    public void messagesAdded(MessageCountEvent e) {
                        super.messagesAdded(e);
                        if (e.getType() == MessageCountEvent.ADDED) {
                            messageSent++;
                            Logger.getLogger(EnvelopeSendMailMessage.class.getName()).info(String.valueOf(messageSent).concat(" number of messages added to store folder"));
                        }
                    }
                });
            }

            Transport trans = session.getTransport(mailProfile.getTransport());
            trans.addTransportListener(new TransportAdapter() {
                @Override
                public void messageDelivered(TransportEvent e) {
                    super.messageDelivered(e);
                    if (e.getType() == TransportEvent.MESSAGE_DELIVERED) {
                        env.setStatusCode(EnvelopeStatus.Status.SENT.getCode());
                        env.setSentdate(new Timestamp(System.currentTimeMillis()));
                        try {
                            env.setSize(mimeMessage.getSize());
                        } catch (MessagingException ex) {
                            Logger.getLogger(EnvelopeSendMailMessage.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                        }
                        if (mailProfile.isStoreCopy()) {
                            try {
                                folder.appendMessages(new Message[]{mimeMessage});
                            } catch (MessagingException ex) {
                                Logger.getLogger(EnvelopeSendMailMessage.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                            }
                        }
                    }
                }

                @Override
                public void messageNotDelivered(TransportEvent e) {
                    super.messageNotDelivered(e);
                    if (e.getType() == TransportEvent.MESSAGE_NOT_DELIVERED) {
                        env.setStatusCode(EnvelopeStatus.Status.FAIL.getCode());
                    }
                }
            });
            trans.connect(mailProfile.getHost(), mailProfile.getUsername(), mailProfile.getPassword());
            mimeMessage.saveChanges();
            trans.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            trans.close();
        } catch (MessagingException ex) {
            Logger.getLogger(EnvelopeSendMailMessage.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            Exception ex1;
            if ((ex1 = ex.getNextException()) != null) {
                Logger.getLogger(EnvelopeSendMailMessage.class.getName()).log(Level.SEVERE, ex1.getLocalizedMessage(), ex1);
            }
        }
        return env;
    }

    @Override
    public List<Address> buildAddreses(String[] addresses) {
        List<Address> addList = new ArrayList<>();
        for (String address : addresses) {
            try {
                addList.add(new InternetAddress(address));
            } catch (AddressException ex) {
                Logger.getLogger(EnvelopeSendMailMessage.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        }
        return addList;
    }
}
