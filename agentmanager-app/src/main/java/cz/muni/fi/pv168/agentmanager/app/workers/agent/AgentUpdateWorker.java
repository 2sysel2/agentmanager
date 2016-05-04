package cz.muni.fi.pv168.agentmanager.app.workers.agent;

import cz.muni.fi.pv168.agentmanager.app.gui.AgentManagerMain;
import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentTableModel;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionComboBoxWorker;
import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.ServiceFailureException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ienze
 */
public class AgentUpdateWorker extends SwingWorker<Boolean, Void> {

    private Logger log = LoggerFactory.getLogger(AgentUpdateWorker.class);

    private Agent agent;
    private AgentManager agentManager;
    private AgentTableModel agentTableModel;
    private AgentManagerMain mainWindow;

    public AgentUpdateWorker(Agent agent, AgentManager agentManager, AgentTableModel agentTableModel, AgentManagerMain mainWindow) {
        this.agent = agent;
        this.agentManager = agentManager;
        this.agentTableModel = agentTableModel;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            agentManager.updateAgent(agent);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while updating agent entry", e);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                agentTableModel.updateAgent(agent);
            } else {
                mainWindow.showErrorMessage("error.agent.update");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
