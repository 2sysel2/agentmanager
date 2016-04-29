package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jaromir Sys
 */
public class AgentTableModel extends AbstractTableModel{

    private List<Agent> agents = new ArrayList<>();
    private final ResourceBundle texts;

    public AgentTableModel(ResourceBundle texts) {
        this.texts = texts;
    }
    
    @Override
    public int getRowCount() {
        return agents.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return texts.getString("agent.name");
            case 1: return texts.getString("agent.born");
            case 2: return texts.getString("agent.died");
            case 3: return texts.getString("agent.level");
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return String.class;
            case 1: return LocalDate.class;
            case 2: return LocalDate.class;
            case 3: return Integer.class;
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
            case 1:
            case 2:
            case 3: return false;
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Agent agent = agents.get(rowIndex);
        switch(columnIndex){
            case 0: return agent.getName();
            case 1: return agent.getBorn();
            case 2: return agent.getDied();
            case 3: return agent.getLevel();
            default: throw new IllegalArgumentException("invalid index");
        }
    }    
    
    public void addAgent(Agent agent){
        agents.add(agent);
        fireTableRowsInserted(agents.size() - 1, agents.size() - 1);
    }
    
    public Agent getAgent(int rowIndex){
        return agents.get(rowIndex);
    }
    
    public void removeAgent(int rowIndex){
        agents.remove(rowIndex);
        fireTableRowsInserted(agents.size() - 1, agents.size() - 1);
    }
}
