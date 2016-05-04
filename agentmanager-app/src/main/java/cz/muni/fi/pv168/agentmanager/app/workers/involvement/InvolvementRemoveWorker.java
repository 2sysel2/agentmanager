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
public class InvolvementRemoveWorker extends SwingWorker<Boolean, Void> {
    
    private Logger log = LoggerFactory.getLogger(InvolvementRemoveWorker.class);
    
    private Involvement involvement;
    private InvolvementManager involvementManager;
    private InvolvementTableModel involvementTableModel;
    private AgentManagerMain mainWindow;
    
    public InvolvementRemoveWorker(Involvement involvement, InvolvementManager involvementManager, InvolvementTableModel involvementTableModel, AgentManagerMain mainWindow) {
        this.involvement = involvement;
        this.involvementManager = involvementManager;
        this.involvementTableModel = involvementTableModel;
        this.mainWindow = mainWindow;
    }
    
    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            involvementManager.removeInvolvement(involvement);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while removing involvement entry", e);
        }
        return false;
    }
    
    @Override
    protected void done() {
        try {
            if (get()) {
                involvementTableModel.removeInvolvement(involvement);
            } else {
                mainWindow.showErrorMessage("error.involvement.remove");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }
    
}
