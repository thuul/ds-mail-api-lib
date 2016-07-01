/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.util;

import com.digispherecorp.api.enterprise.mapi.core.MailMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author Wal
 */
public class MailMessageUtil {

    public static List<MimeBodyPart> doMessageAttachment(String[] paths) {
        List<MimeBodyPart> mimeBodyPartList = new ArrayList<>();
        MimeBodyPart part;
        File file;
        try {
            for (String path : paths) {
                file = new File(path);
                part = new MimeBodyPart();
                part.setDisposition(MimeBodyPart.ATTACHMENT);
                part.attachFile(file);
                mimeBodyPartList.add(part);
            }
        } catch (MessagingException | IOException ex) {
            Logger.getLogger(MailMessage.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return mimeBodyPartList;
    }

}
