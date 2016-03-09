/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contacts;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import org.contacts.soap.CreateContactResponse;

/**
 *
 * @author Joan
 */
public class CreateContactHandler implements AsyncHandler<CreateContactResponse>{

    @Override
    public void handleResponse(Response<CreateContactResponse> res) {
        try {
            System.out.println("Async handler returns " + res.get().getId());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(CreateContactHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
