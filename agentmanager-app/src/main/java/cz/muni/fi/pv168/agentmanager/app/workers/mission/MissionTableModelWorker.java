package cz.muni.fi.pv168.agentmanager.app.workers.mission;

import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionTableModel;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ienze
 */
public class MissionTableModelWorker extends SwingWorker<List<Mission>, Void> {

    private Logger log = LoggerFactory.getLogger(MissionTableModelWorker.class);

    private MissionManager missionManager;
    private MissionTableModel missionTableModel;

    public MissionTableModelWorker(MissionManager missionManager, MissionTableModel missionTableModel) {
        this.missionManager = missionManager;
        this.missionTableModel = missionTableModel;
    }

    @Override
    protected List<Mission> doInBackground() throws Exception {
        return missionManager.findAllMissions();
    }

    @Override
    protected void done() {
        missionTableModel.clear();
        try {
            get().forEach(m -> missionTableModel.addMission(m));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
