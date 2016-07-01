/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.digispherecorp.api.enterprise.mapi.core;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author ikram
 */

public class MailSupport implements Serializable {
    
    /**
     *
     */
    public MailSupport(){
    }

    /**
     *
     * @param lists
     * @param file
     * @throws IOException
     * @throws MessagingException
     */
    public void constructAttachment(List<MimeBodyPart> lists, File file) throws IOException,MessagingException{
        MimeBodyPart part;
        try {            
            part = new MimeBodyPart();
            part.setDisposition(MimeBodyPart.ATTACHMENT);
            part.attachFile(file);
            lists.add(part);
        } catch (MessagingException ex) {
            Logger.getLogger(MailSupport.class.getName()).log(Level.SEVERE, ex.getMessage(), ex); throw new MessagingException(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(MailSupport.class.getName()).log(Level.SEVERE, ex.getMessage(), ex); throw new IOException(ex.getMessage());
        }
    }

    /**
     *
     * @param lista
     * @param to
     * @param bcc
     * @param cc
     * @throws AddressException
     */
    public void constructAddresses(List<InternetAddress[]> lista, String to, String bcc, String cc) throws AddressException{
        try{
            String[] toadd = to.split(";");
            InternetAddress[] toaddr = new InternetAddress[toadd.length];
            for (int i = 0; i < toadd.length; i++){
                if (!toadd[i].isEmpty() || toadd[i] != null){
                    toaddr[i] = new InternetAddress(toadd[i]);
                }
            }
            lista.add(toaddr);
            if (!cc.isEmpty()){
                String[] ccadd = cc.split(";");
                InternetAddress[] ccaddr = new InternetAddress[ccadd.length];
                for (int i = 0; i < ccadd.length; i++){
                    if (!ccadd[i].isEmpty() || toadd[i] != null){
                        ccaddr[i] = new InternetAddress(ccadd[i]);
                    }
                }
                lista.add(ccaddr);
            }else {lista.add(null);}
        }catch (AddressException ex) {
            Logger.getLogger(MailSupport.class.getName()).log(Level.SEVERE, ex.getMessage(), ex); throw new AddressException(ex.getMessage());
        }
    }

    /**
     *
     * @param lista
     * @param to
     * @throws AddressException
     */
    public void constructAddresses(List<InternetAddress[]> lista, String to) throws AddressException{
        try{
            lista.clear();
            String[] toadd = to.split(";");
            InternetAddress[] toaddr = new InternetAddress[toadd.length];
            for (int i = 0; i < toadd.length; i++){
                if (!toadd[i].isEmpty() || toadd[i] != null){
                    toaddr[i] = new InternetAddress(toadd[i]);
                }
            }
            lista.add(toaddr);
        }catch (AddressException ex) {
            Logger.getLogger(MailSupport.class.getName()).log(Level.SEVERE, ex.getMessage(), ex); throw new AddressException(ex.getMessage());
        }
    }

}


