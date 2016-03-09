/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contacts.soap;

import javax.xml.ws.WebFault;

/**
 *
 * @author Joan
 */
@WebFault(name="NotFoundFault")
public class NotFoundFault extends Exception{
    public NotFoundFault(String message){
        super(message);
    }
    public String getFaultInfo() { return getMessage(); }
}
