/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import java.util.List;
import javax.mail.Address;

/**
 *
 * @author Wal
 * @param <T>
 */
public interface ISendMailMessage<T> {

    /**
     *
     * @return String
     */
    String getIMAPFolder();

    /**
     *
     * @return
     */
    T sendMailMessage();

    /**
     *
     * @return
     */
    T buildMailMessage();

    /**
     *
     * @param addresses
     * @return List
     */
    List<Address> buildAddreses(String[] addresses);

}
