/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.sql.SQLException;

/**
 *
 * @author Jaromir Sys
 */
class ServiceFailureException extends Exception {

    public ServiceFailureException(String string) {
        super(string);
    }

    ServiceFailureException(String string, SQLException ex) {
        super(string,ex);
    }
    
}
