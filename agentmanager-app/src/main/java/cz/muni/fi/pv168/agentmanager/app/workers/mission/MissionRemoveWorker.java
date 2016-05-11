package cz.muni.fi.pv168.agentmanager.app.workers.mission;

import cz.muni.fi.pv168.agentmanager.app.gui.AgentManagerMain;
import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionTableModel;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import cz.muni.fi.pv168.gmiterkosys.ServiceFailureException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ienze
 */
public class MissionRemoveWorker extends SwingWorker<Boolean, Void> {

    private Logger log = LoggerFactory.getLogger(MissionRemoveWorker.class);

    private Mission mission;
    private MissionManager missionManager;
    private MissionTableModel missionTableModel;
    private AgentManagerMain mainWindow;

    public MissionRemoveWorker(Mission mission, MissionManager missionManager, MissionTableModel missionTableModel, AgentManagerMain mainWindow) {
        this.mission = mission;
        this.missionManager = missionManager;
        this.missionTableModel = missionTableModel;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            missionManager.removeMission(mission);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while removing mission entry", e);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                missionTableModel.removeMission(mission);
            } else {
                mainWindow.showErrorMessage("error.mission.remove");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
