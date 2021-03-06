package cz.muni.fi.pv168.agentmanager.app.gui.mission;

import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.time.LocalDate;
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
public class MissionTableModel extends AbstractTableModel {

    private List<Mission> missions = new ArrayList<>();
    private ResourceBundle texts;

    public MissionTableModel(ResourceBundle texts) {
        this.texts = texts;
    }

    @Override
    public int getRowCount() {
        return missions.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return texts.getString("mission.code");
            case 1:
                return texts.getString("mission.location");
            case 2:
                return texts.getString("mission.start");
            case 3:
                return texts.getString("mission.end");
            case 4:
                return texts.getString("mission.objective");
            case 5:
                return texts.getString("mission.outcome");
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
            case 4:
                return String.class;
            case 5:
                return Outcome.class;
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
            case 4:
            case 5:
                return false;
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mission mission = missions.get(rowIndex);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault());
        
        switch (columnIndex) {
            case 0:
                return mission.getCode();
            case 1:
                return mission.getLocation();
            case 2:
                return mission.getStart() != null ? dateFormatter.format(mission.getStart()) : "";
            case 3:
                return mission.getEnd() != null ? dateFormatter.format(mission.getEnd()) : "";
            case 4:
                return mission.getObjective();
            case 5:
                return mission.getOutcome();
            default:
                throw new IllegalArgumentException("invalid index");
        }
    }

    public void addMission(Mission mission) {
        missions.add(mission);
        fireTableRowsInserted(missions.size() - 1, missions.size() - 1);
    }

    public Mission getMission(int selectedRow) {
        return missions.get(selectedRow);
    }

    public void removeMission(Mission mission) {
        int row = missions.indexOf(mission);
        missions.remove(row);
        fireTableRowsInserted(row, row);
    }

    public void updateMission(Mission mission) {
        int row = missions.indexOf(mission);
        fireTableRowsUpdated(row, row);
    }

    public void clear() {
        missions.clear();
    }
}
