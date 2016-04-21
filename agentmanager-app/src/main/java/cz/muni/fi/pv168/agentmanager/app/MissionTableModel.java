package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableModel;

/**
 *
 * @author Jaromir Sys
 */
public class MissionTableModel extends AbstractTableModel{

    List<Mission> missions = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return missions.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return "Code";
            case 1: return "Location";
            case 2: return "Start";
            case 3: return "End";
            case 4: return "Objective";
            case 5: return "Outcome";
            case 6: return "Edit";
            case 7: return "Delete";
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return String.class;
            case 1: return String.class;
            case 2: return LocalDate.class;
            case 3: return LocalDate.class;
            case 4: return String.class;
            case 5: return Outcome.class;
            case 6: return JButton.class;
            case 7: return JButton.class;
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: return false;
            case 6:
            case 7: return false;
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mission mission = missions.get(rowIndex);
        switch(columnIndex){
            case 0: return mission.getCode();
            case 1: return mission.getLocation();
            case 2: return mission.getStart();
            case 3: return mission.getEnd();
            case 4: return mission.getObjective();
            case 5: return mission.getOutcome();
            case 6: 
                JButton button = new JButton("Edit");
                button.addActionListener((ActionEvent e) -> {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
                return button;
            case 7: return new JButton("Delete");
            default: throw new IllegalArgumentException("invalid index");
        }
    }    
    
    public void addMission(Mission mission){
        missions.add(mission);
    }
}
