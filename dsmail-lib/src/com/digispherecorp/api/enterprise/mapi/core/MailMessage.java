/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.core;

import com.digispherecorp.api.enterprise.mapi.abs.IMessage;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author Wal
 */
public class MailMessage implements IMessage {

    private final List<MimeBodyPart> mimeBodyPartList;
    private File file;
    private final Envelope e;

    public MailMessage(Envelope e) {
        this.mimeBodyPartList = new ArrayList<>();
        this.e = e;
        doMessageAttachment(e.getAttachmentPaths());
    }

    public MailMessage(Envelope e, List<MimeBodyPart> mimeBodyList) {
        this.e = e;
        this.mimeBodyPartList = mimeBodyList;
    }

    private void doMessageAttachment(String[] paths) {
        MimeBodyPart part;
        try {
            for (String path : paths) {
                this.file = new File(path);
                part = new MimeBodyPart();
                part.setDisposition(MimeBodyPart.ATTACHMENT);
                part.attachFile(file);
                this.mimeBodyPartList.add(part);
            }
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(MailMessage.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    @Override
    public List<MimeBodyPart> getMimeBodyPartList() {
        return mimeBodyPartList;
    }

    @Override
    public Envelope getEnvelope() {
        return e;
    }
}
