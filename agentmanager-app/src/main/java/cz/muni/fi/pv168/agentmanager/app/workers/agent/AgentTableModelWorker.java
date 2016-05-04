/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168.agentmanager.app.workers.agent;

import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentTableModel;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionTableModelWorker;
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
public class AgentTableModelWorker extends SwingWorker<List<Agent>, Void> {

    private final Logger log = LoggerFactory.getLogger(MissionTableModelWorker.class);

    private final AgentManager agentManager;
    private final AgentTableModel agentTableModel;

    public AgentTableModelWorker(AgentManager agentManager, AgentTableModel agentTableModel) {
        this.agentManager = agentManager;
        this.agentTableModel = agentTableModel;
    }

    @Override
    protected List<Agent> doInBackground() throws Exception {
        return agentManager.findAllAgents();
    }

    @Override
    protected void done() {
        agentTableModel.clear();
        try {
            get().forEach(a -> agentTableModel.addAgent(a));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
