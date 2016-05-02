package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Mission;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Jaromir Sys
 */
public class MissionComboBoxModel extends AbstractListModel implements ComboBoxModel{
    private List<Mission> missions;
    
    private Mission selected;
    
    public MissionComboBoxModel(List<Mission> missions){
        this.missions = missions;     
        if(missions.size()!=0){
            selected = missions.get(0);
        }
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        selected = (Mission)anItem;
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
}
