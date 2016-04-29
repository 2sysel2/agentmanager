package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Involvement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jaromir Sys
 */
public class InvolvementTableModel extends AbstractTableModel {

    private List<Involvement> involvements = new ArrayList<>();
    private ResourceBundle texts;

    public InvolvementTableModel(ResourceBundle texts) {
        this.texts = texts;
    }

    @Override
    public int getRowCount() {
        return involvements.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return texts.getString("involvement.mission");
            case 1: return texts.getString("involvement.agent");
            case 2: return texts.getString("involvement.start");
            case 3: return texts.getString("involvement.end");
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;
            case 1: return String.class;
            case 2: return LocalDateTime.class;
            case 3: return LocalDateTime.class;
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
                return false;
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Involvement involvement = involvements.get(rowIndex);
        switch (columnIndex) {
            case 0: return involvement.getMission().getCode();
            case 1: return involvement.getAgent().getName();
            case 2: return involvement.getStart();
            case 3: return involvement.getEnd();
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    public void addInvolvement(Involvement involvement) {
        involvements.add(involvement);
        fireTableRowsInserted(involvements.size() - 1, involvements.size() - 1);
    }

    public Involvement getInvolvement(int selectedRow) {
        return involvements.get(selectedRow);
    }
    
    public void removeInvolvement(int selectedRow){
        involvements.remove(selectedRow);
        fireTableRowsInserted(involvements.size() - 1, involvements.size() - 1);
    }
}
