/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

/**
 *
 * @author walle
 */
public interface IEnvelopeServiceQueueManager {

    /**
     *
     */
    void consumeService();

    /**
     *
     * @return boolean
     */
    boolean shutdown();

}
