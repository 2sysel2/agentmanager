package cz.muni.fi.pv168.agentmanager.app.workers.involvement;

import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionComboBoxWorker;
import cz.muni.fi.pv168.agentmanager.app.gui.AgentManagerMain;
import cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementTableModel;
import cz.muni.fi.pv168.gmiterkosys.Involvement;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManager;
import cz.muni.fi.pv168.gmiterkosys.ServiceFailureException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ienze
 */
public class InvolvementCreateWorker extends SwingWorker<Boolean, Void> {
    
    private Logger log = LoggerFactory.getLogger(InvolvementCreateWorker.class);
    
    private Involvement involvement;
    private InvolvementManager involvementManager;
    private InvolvementTableModel involvementTableModel;
    private AgentManagerMain mainWindow;
    
    public InvolvementCreateWorker(Involvement involvement, InvolvementManager involvementManager, InvolvementTableModel involvementTableModel, AgentManagerMain mainWindow) {
        this.involvement = involvement;
        this.involvementManager = involvementManager;
        this.involvementTableModel = involvementTableModel;
        this.mainWindow = mainWindow;
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            involvementManager.createInvolvement(involvement);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while creating involvement entry", e);
        }
        return false;
    }
    
    @Override
    protected void done() {
        try {
            if (get()) {
                involvementTableModel.addInvolvement(involvement);
            } else {
                mainWindow.showErrorMessage("error.involvement.create");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }
    
}
