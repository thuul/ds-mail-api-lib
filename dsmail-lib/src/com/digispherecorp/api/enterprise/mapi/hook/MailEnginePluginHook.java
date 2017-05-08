/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.hook;

import com.digispherecorp.api.enterprise.mapi.abs.AbstractIMAPMailProfile;
import com.digispherecorp.api.enterprise.mapi.schedules.MailEnvelopeTaskScheduler;

/**
 *
 * @author walle
 */
public class MailEnginePluginHook {

    private static final MailEnginePluginHook instance = new MailEnginePluginHook();

    private MailEnginePluginHook() {
    }

    public static MailEnginePluginHook getInstance() {
        return instance;
    }

    public void addPluginHook(AbstractIMAPMailProfile profile) {
        MailEnvelopeTaskScheduler.getInstance(profile).scheduleRunnableTask();
    }

    public void releasePluginHook() {
        MailEnvelopeTaskScheduler.getInstance(null).cancelRunningTask();
    }

}
