package cz.muni.fi.pv168.agentmanager.app.gui.involvement;

import cz.muni.fi.pv168.gmiterkosys.Involvement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
            case 0:
                return texts.getString("involvement.mission");
            case 1:
                return texts.getString("involvement.agent");
            case 2:
                return texts.getString("involvement.start");
            case 3:
                return texts.getString("involvement.end");
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
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
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.getDefault());
        
        switch (columnIndex) {
            case 0:
                return involvement.getMission().getCode();
            case 1:
                return involvement.getAgent().getName();
            case 2:
                return involvement.getStart() != null ? dateFormatter.format(involvement.getStart()) : "";
            case 3:
                return involvement.getEnd() != null ? dateFormatter.format(involvement.getEnd()) : "";
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

    public void removeInvolvement(Involvement involvement) {
        int row = involvements.indexOf(involvement);
        involvements.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void updateInvolvement(Involvement involvement) {
        int row = involvements.indexOf(involvement);
        fireTableRowsUpdated(row, row);
    }

    public void clear() {
        involvements.clear();
    }
}
