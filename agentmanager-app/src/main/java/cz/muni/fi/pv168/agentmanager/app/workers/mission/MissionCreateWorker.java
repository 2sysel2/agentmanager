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
public class MissionCreateWorker extends SwingWorker<Boolean, Void> {

    private Logger log = LoggerFactory.getLogger(MissionCreateWorker.class);

    private Mission mission;
    private MissionManager missionManager;
    private MissionTableModel missionTableModel;
    private AgentManagerMain mainWindow;

    public MissionCreateWorker(Mission mission, MissionManager missionManager, MissionTableModel missionTableModel, AgentManagerMain mainWindow) {
        this.mission = mission;
        this.missionManager = missionManager;
        this.missionTableModel = missionTableModel;
        this.mainWindow = mainWindow;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            missionManager.createMission(mission);
            return true;
        } catch (ServiceFailureException e) {
            log.error("Porblem while creating mission entry", e);
        }
        return false;
    }

    @Override
    protected void done() {
        try {
            if (get()) {
                missionTableModel.addMission(mission);
            } else {
                mainWindow.showErrorMessage("error.mission.create");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
