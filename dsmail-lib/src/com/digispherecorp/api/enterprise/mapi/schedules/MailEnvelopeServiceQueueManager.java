/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.schedules;

import com.digispherecorp.api.enterprise.mapi.core.MailEngine;
import com.digispherecorp.api.enterprise.mapi.core.MailMessage;
import com.digispherecorp.api.reusable.pool.queue.IQueueManager;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wal
 */
public class MailEnvelopeServiceQueueManager implements IQueueManager {

    private boolean keepAlive;
    private BlockingQueue<MailMessage> envelopeQueue;
    private final MailEngine mails;
    private static volatile MailEnvelopeServiceQueueManager instance;

    private MailEnvelopeServiceQueueManager() {
        super();
        keepAlive = true;
        mails = MailEngine.getInstance();
        envelopeQueue = mails.getEnvelopeQueue();
    }

    public static MailEnvelopeServiceQueueManager newInstance() {
        synchronized (MailEnvelopeServiceQueueManager.class) {
            if (instance == null) {
                instance = new MailEnvelopeServiceQueueManager();
            }
        }
        return instance;
    }

    public static MailEnvelopeServiceQueueManager getInstance() {
        return instance;
    }

    @Override
    public void consumeService() {
        Logger.getLogger(MailEnvelopeServiceQueueManager.class.getName()).info("Mail Envelope Queue listening....");
        while (keepAlive) {
            MailMessage message;
            try {
                message = envelopeQueue.take();
                mails.sendMailMessage(message);
            } catch (InterruptedException ex) {
                Logger.getLogger(MailEnvelopeServiceQueueManager.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            }
        }
    }

    @Override
    public void shutdown() {
        synchronized (MailEnvelopeServiceQueueManager.class) {
            if (keepAlive) {
                keepAlive = false;
            }
            envelopeQueue.clear();
            envelopeQueue = null;
        }
    }
}
