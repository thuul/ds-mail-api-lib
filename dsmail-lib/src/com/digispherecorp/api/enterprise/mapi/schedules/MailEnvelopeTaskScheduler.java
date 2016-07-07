/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.schedules;

import com.digispherecorp.api.base.schedules.IRunnableTaskSchedulerService;
import com.digispherecorp.api.core.thread.SimpleDeamonThreadFactory;
import com.digispherecorp.api.enterprise.mapi.abs.AbstractIMAPMailProfile;
import com.digispherecorp.api.enterprise.mapi.core.MailEngine;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author walle
 */
public class MailEnvelopeTaskScheduler implements IRunnableTaskSchedulerService {

    private static final long serialVersionUID = -218042083480639926L;

    private static volatile MailEnvelopeTaskScheduler instance;
    private ScheduledExecutorService scheduler;
    private final ScheduledExecutorService schedulerMon;
    private ScheduledFuture<?> runnableScheduledFuture;
    private ScheduledFuture<?> monitorRunnableScheduledFuture;
    private Runnable runnableProcess;
    private long delay;

    private MailEnvelopeTaskScheduler() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(new SimpleDeamonThreadFactory());
        this.schedulerMon = Executors.newSingleThreadScheduledExecutor(new SimpleDeamonThreadFactory());
    }

    private MailEnvelopeTaskScheduler(AbstractIMAPMailProfile profile) {
        this();
        MailEngine.newInstance(profile);
        MailEnvelopeServiceQueueManager.newInstance();
    }

    public static MailEnvelopeTaskScheduler getInstance(AbstractIMAPMailProfile profile) {
        synchronized (MailEnvelopeTaskScheduler.class) {
            if (instance == null) {
                if (profile != null) {
                    instance = new MailEnvelopeTaskScheduler(profile);
                } else {
                    instance = new MailEnvelopeTaskScheduler();
                }
            }
        }
        return instance;
    }

    @Override
    public void scheduleRunnableTask() {
        runnableScheduledFuture = this.scheduler.schedule(() -> {

            Logger.getLogger(MailEnvelopeTaskScheduler.class.getName()).info("starting mail envelope scheduler and queue....");
            MailEnvelopeServiceQueueManager.getInstance().consumeService();

        }, 10, TimeUnit.SECONDS);

        this.schedulerMon.scheduleWithFixedDelay(() -> {
            if (!checkIfTaskRunning()) {
                runnableScheduledFuture = scheduler.schedule(() -> {

                    Logger.getLogger(MailEnvelopeTaskScheduler.class.getName()).info("envelope scheduler stopped, restarting scheduler....");
                    MailEnvelopeServiceQueueManager.getInstance().consumeService();

                }, 10, TimeUnit.SECONDS);
            }
        }, 15, 300, TimeUnit.SECONDS);
    }

    @Override
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    @Override
    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public ScheduledFuture<?> getRunnableScheduledFuture() {
        return runnableScheduledFuture;
    }

    @Override
    public ScheduledFuture<?> getMonitorRunnableScheduledFuture() {
        return monitorRunnableScheduledFuture;
    }

    @Override
    public Runnable getRunnableProcess() {
        return runnableProcess;
    }

    @Override
    public void setRunnableProcess(Runnable runnableProcess) {
        this.runnableProcess = runnableProcess;
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public boolean checkIfTaskRunning() {
        return !runnableScheduledFuture.isDone() || !runnableScheduledFuture.isCancelled();
    }

    @Override
    public boolean cancelRunningTask() {
        if (checkIfTaskRunning()) {

            MailEnvelopeServiceQueueManager.getInstance().shutdown();
            runnableScheduledFuture.cancel(true);

        }
        schedulerMon.shutdown();
        try {
            if (schedulerMon.awaitTermination(60, TimeUnit.SECONDS)) {
                schedulerMon.shutdownNow();
                if (!schedulerMon.awaitTermination(60, TimeUnit.SECONDS)) {
                    Logger.getLogger(MailEnvelopeTaskScheduler.class.getName()).info("Executor Service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MailEnvelopeTaskScheduler.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            schedulerMon.shutdownNow();
            Thread.currentThread().interrupt();
        }

        scheduler.shutdown();
        try {
            if (scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    Logger.getLogger(MailEnvelopeTaskScheduler.class.getName()).info("Executor Service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MailEnvelopeTaskScheduler.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        return true;
    }
}
