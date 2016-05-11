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
public class AgentRemoveWorker extends SwingWorker<Boolean, Void> {

    private Logger log = LoggerFactory.getLogger(AgentRemoveWorker.class);

    private Agent agent;
    private AgentManager agentManager;
    private AgentTableModel agentTableModel;
    private AgentManagerMain mainWindow;

    public AgentRemoveWorker(Agent agent, AgentManager agentManager, AgentTableModel agentTableModel, AgentManagerMain mainWindow) {
        this.agent = agent;
        this.agentManager = agentManager;
        this.agentTableModel = agentTableModel;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            agentManager.removeAgent(agent);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while removing agent entry", e);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                agentTableModel.removeAgent(agent);
            } else {
                mainWindow.showErrorMessage("error.agent.remove");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
