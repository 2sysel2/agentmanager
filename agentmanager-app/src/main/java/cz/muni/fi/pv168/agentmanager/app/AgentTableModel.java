package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jaromir Sys
 */
public class AgentTableModel extends AbstractTableModel{

    private List<Agent> agents = new ArrayList<>();
    private final ResourceBundle texts;
    
    @Override
    public int getRowCount() {
        return agents.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return "Name";
            case 1: return "Born";
            case 2: return "Died";
            case 3: return "Level";
            case 4: return "Edit";
            case 5: return "Delete";
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
            case 4: return JButton.class;
            case 5: return JButton.class;
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
            case 4:
            case 5: return false;
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
            case 4: 
                JButton button = new JButton("Edit");
                button.addActionListener((ActionEvent e) -> {
                    throw new UnsupportedOperationException("Not supported yet.");
                });
                return button;
            case 5: return new JButton("Delete");
            default: throw new IllegalArgumentException("invalid index");
        }
    }    
    
    public void addAgent(Agent agent){
        agents.add(agent);
        fireTableRowsInserted(agents.size(), agents.size());
    }
}
