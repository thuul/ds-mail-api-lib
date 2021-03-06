/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import com.digispherecorp.api.enterprise.mapi.core.MailMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wal
 */
public class FileBasedEnvelopeProfile extends AbstractIMAPMailProfile {

    private static final long serialVersionUID = -1209921705948272969L;

    private Properties properties;

    public FileBasedEnvelopeProfile(File propFile) {
        super();
        init(propFile);
    }

    private void init(File propFile) {
        properties = new Properties();
        try (Reader reader = new BufferedReader(new FileReader(propFile))) {
            try {
                properties.load(reader);
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileBasedEnvelopeProfile.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
        setMailFrom(properties.getProperty("mailFrom"));
        setSentTo(properties.getProperty("sentTo"));
        setUsername(properties.getProperty("username"));
        setPassword(properties.getProperty("password"));
        setHost(properties.getProperty("host"));
        setPort(Integer.valueOf(properties.getProperty("port")));
        setTransport(properties.getProperty("transport"));
        setStore(properties.getProperty("store"));
        setImapUser(properties.getProperty("imapUser"));
        setImapPassword(properties.getProperty("imapPassword"));
        setImapHost(properties.getProperty("imapHost"));
        setImapPort(Integer.valueOf(properties.getProperty("imapPort")));
        setDebug(Boolean.valueOf(properties.getProperty("debug")));
        setStoreCopy(Boolean.valueOf(properties.getProperty("storeCopy")));
    }

    @Override
    public Queue<MailMessage> sendOutgoingMailMessages() {
        return getMailMessageQueue();
    }

    @Override
    public Queue<MailMessage> sendOutgoingMailMessages(boolean bln) {
        return getMailMessageQueue();
    }

    @Override
    public void updateMailMessageSourceStatus(List<String> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
