package cz.muni.fi.pv168.agentmanager.app.workers.agent;

import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentComboBoxModel;
import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ienze
 */
public class AgentComboBoxWorker extends SwingWorker<List<Agent>, Void> {

    private final Logger log = LoggerFactory.getLogger(AgentComboBoxWorker.class);

    private final AgentManager agentManager;
    private final AgentComboBoxModel agentComboBoxModel;

    public AgentComboBoxWorker(AgentManager agentManager, AgentComboBoxModel agentComboBoxModel) {
        this.agentManager = agentManager;
        this.agentComboBoxModel = agentComboBoxModel;
    }

    @Override
    protected List<Agent> doInBackground() throws Exception {
        return agentManager.findAllAgents();
    }

    @Override
    protected void done() {
        agentComboBoxModel.clear();
        try {
            get().forEach(a -> agentComboBoxModel.addAgent(a));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
