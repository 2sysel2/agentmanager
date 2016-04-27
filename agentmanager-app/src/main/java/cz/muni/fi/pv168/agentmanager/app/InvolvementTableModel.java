package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Involvement;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableModel;

/**
 *
 * @author Jaromir Sys
 */
public class InvolvementTableModel extends AbstractTableModel{

    List<Involvement> involvements = new ArrayList<>();
    
    @Override
    public int getRowCount() {
        return involvements.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return "Mission";
            case 1: return "Agent";
            case 2: return "Start";
            case 3: return "End";
            case 4: return "Edit";
            case 5: return "Delete";
            default: throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return JButton.class;
            case 1: return JButton.class;
            case 2: return LocalDateTime.class;
            case 3: return LocalDateTime.class;
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
        Involvement involvement = involvements.get(rowIndex);
        switch(columnIndex){
            case 0: return new JButton(involvement.getMission().getCode());
            case 1: return new JButton(involvement.getAgent().getName());
            case 2: return involvement.getStart();
            case 3: return involvement.getEnd();
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
    
    public void addInvolvement(Involvement involvement){
        involvements.add(involvement);
    }
}
