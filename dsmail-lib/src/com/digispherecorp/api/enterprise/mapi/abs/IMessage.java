/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digispherecorp.api.enterprise.mapi.abs;

import com.digispherecorp.api.enterprise.mapi.core.Envelope;
import java.io.Serializable;
import java.util.List;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author walle
 */
public interface IMessage extends Serializable {

    /**
     *
     * @return Envelope
     */
    Envelope getEnvelope();

    /**
     *
     * @return List
     */
    List<MimeBodyPart> getMimeBodyPartList();

}
