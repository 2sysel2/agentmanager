package cz.muni.fi.pv168.agentmanager.app.gui.mission;

import cz.muni.fi.pv168.gmiterkosys.Mission;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Jaromir Sys
 */
public class MissionComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private List<Mission> missions = new ArrayList<>();

    private Mission selected;

    public MissionComboBoxModel() {
        if (!missions.isEmpty()) {
            selected = missions.get(0);
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selected = (Mission) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    @Override
    public int getSize() {
        return missions.size();
    }

    @Override
    public Object getElementAt(int index) {
        return missions.get(index);
    }

    public void addMission(Mission mission) {
        missions.add(mission);
        fireIntervalAdded(this, missions.size() - 1, missions.size() - 1);
    }

    public Mission getSelectedAgent() {
        return selected;
    }

    public void clear() {
        missions.clear();
    }
}
