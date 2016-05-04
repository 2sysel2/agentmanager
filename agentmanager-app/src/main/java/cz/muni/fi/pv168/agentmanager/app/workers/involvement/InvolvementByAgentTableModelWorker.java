package cz.muni.fi.pv168.agentmanager.app.workers.involvement;

import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionTableModelWorker;
import cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementTableModel;
import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.Involvement;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManager;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ienze
 */
public class InvolvementByAgentTableModelWorker extends SwingWorker<List<Involvement>, Void> {

    private Logger log = LoggerFactory.getLogger(InvolvementByAgentTableModelWorker.class);

    private Agent agent;
    private InvolvementManager involvementManager;
    private InvolvementTableModel involvementTableModel;

    public InvolvementByAgentTableModelWorker(Agent agent, InvolvementManager involvementManager, InvolvementTableModel involvementTableModel) {
        this.agent = agent;
        this.involvementManager = involvementManager;
        this.involvementTableModel = involvementTableModel;
    }

    @Override
    protected List<Involvement> doInBackground() throws Exception {
        return this.involvementManager.findInvolvementByAgent(agent.getId());
    }

    @Override
    protected void done() {
        involvementTableModel.clear();
        try {
            get().forEach(i -> involvementTableModel.addInvolvement(i));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
