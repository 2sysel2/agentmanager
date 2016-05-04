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
public class AgentCreateWorker extends SwingWorker<Boolean, Void> {

    private Logger log = LoggerFactory.getLogger(AgentCreateWorker.class);

    private Agent agent;
    private AgentManager agentManager;
    private AgentTableModel agentTableModel;
    private AgentManagerMain mainWindow;

    public AgentCreateWorker(Agent agent, AgentManager agentManager, AgentTableModel agentTableModel, AgentManagerMain mainWindow) {
        this.agent = agent;
        this.agentManager = agentManager;
        this.agentTableModel = agentTableModel;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            agentManager.createAgent(agent);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while creating agent entry", e);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                agentTableModel.addAgent(agent);
            } else {
                mainWindow.showErrorMessage("error.agent.create");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
