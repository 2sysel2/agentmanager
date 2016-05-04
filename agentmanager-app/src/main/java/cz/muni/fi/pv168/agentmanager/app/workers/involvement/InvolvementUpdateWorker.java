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
public class InvolvementUpdateWorker extends SwingWorker<Boolean, Void> {

    private Logger log = LoggerFactory.getLogger(InvolvementUpdateWorker.class);

    private Involvement involvement;
    private InvolvementManager involvementManager;
    private InvolvementTableModel involvementTableModel;
    private AgentManagerMain mainWindow;

    public InvolvementUpdateWorker(Involvement involvement, InvolvementManager involvementManager, InvolvementTableModel involvementTableModel, AgentManagerMain mainWindow) {
        this.involvement = involvement;
        this.involvementManager = involvementManager;
        this.involvementTableModel = involvementTableModel;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            involvementManager.updateInvolvement(involvement);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while updating involvement entry", e);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                involvementTableModel.updateInvolvement(involvement);
            } else {
                mainWindow.showErrorMessage("error.involvement.update");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
