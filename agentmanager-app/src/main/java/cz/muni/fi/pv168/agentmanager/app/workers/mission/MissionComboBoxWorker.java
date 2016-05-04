package cz.muni.fi.pv168.agentmanager.app.workers.mission;

import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionComboBoxModel;
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
public class MissionComboBoxWorker extends SwingWorker<List<Mission>, Void> {

    private Logger log = LoggerFactory.getLogger(MissionComboBoxWorker.class);

    private MissionManager missionManager;
    private MissionComboBoxModel missionComboBoxModel;

    public MissionComboBoxWorker(MissionManager missionManager, MissionComboBoxModel missionComboBoxModel) {
        this.missionManager = missionManager;
        this.missionComboBoxModel = missionComboBoxModel;
    }

    @Override
    protected List<Mission> doInBackground() throws Exception {
        return missionManager.findAllMissions();
    }

    @Override
    protected void done() {
        missionComboBoxModel.clear();
        try {
            get().forEach(m -> missionComboBoxModel.addMission(m));
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error", e);
        }
    }

}
