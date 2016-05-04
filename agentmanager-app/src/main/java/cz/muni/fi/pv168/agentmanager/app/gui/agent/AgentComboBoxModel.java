package cz.muni.fi.pv168.agentmanager.app.gui.agent;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Jaromir Sys
 */
public class AgentComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private List<Agent> agents = new ArrayList<>();

    private Agent selected;

    public AgentComboBoxModel() {
        if (!agents.isEmpty()) {
            selected = agents.get(0);
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selected = (Agent) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    @Override
    public Object getElementAt(int index) {
        return agents.get(index);
    }

    @Override
    public int getSize() {
        return agents.size();
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
        fireIntervalAdded(this, agents.size() - 1, agents.size() - 1);
    }

    public Agent getSelectedAgent() {
        return selected;
    }

    public void clear() {
        agents.clear();
    }
}
