package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Jaromir Sys
 */
public class AgentComboBoxModel extends AbstractListModel implements ComboBoxModel{

    private List<Agent> agents;
    
    private Agent selected;
    
    public AgentComboBoxModel(List<Agent> agents){
        this.agents = agents;  
        if(agents.size()!=0){
            selected = agents.get(0);
        }
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        selected = (Agent)anItem;
    }

    @Override
    public Object getSelectedItem() {
        return (Agent)selected;
    }

    @Override
    public int getSize() {
        return agents.size();
    }

    @Override
    public Object getElementAt(int index) {
        return agents.get(index);
    }    
}
