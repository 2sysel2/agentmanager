package cz.muni.fi.pv168.agentmanager.app.gui.mission;

import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.awt.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author xgmiterk
 */
public class MissionPanel extends javax.swing.JPanel {

    public void setMission(Mission mission){
        this.codeTextField.setText(mission.getCode());
        this.locationTextField.setText(mission.getLocation());
        this.startSpinner.setValue(Date.from(mission.getStart().atZone(ZoneId.systemDefault()).toInstant()));
        this.endSpinner.setValue(Date.from(mission.getEnd().atZone(ZoneId.systemDefault()).toInstant()));
        this.objectiveTextArea.setText(mission.getObjective());
        this.outcomeComboBox.setSelectedItem(mission.getOutcome());
    }
    
    /**
     * Creates new form MissionPanel
     */
    public MissionPanel() {
        initComponents();
        outcomeComboBox.setModel(new DefaultComboBoxModel(Outcome.values()));
        outcomeComboBox.setRenderer(new ListCellRenderer<String>() {

            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
                
            }
        });
    }
    
    public String getMissionCode(){
        return codeTextField.getText();
    }
    
    public String getMissionLocation(){
        return locationTextField.getText();
    }
    
    public LocalDateTime getMissionStart(){
        Date date = (Date)startSpinner.getValue();
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    
    public LocalDateTime getMissionEnd(){
        Date date = (Date)endSpinner.getValue();
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    
    public String getMissionObjective(){
        return objectiveTextArea.getText();
    }
    
    public Outcome getMissionOutcome(){
        return (Outcome)outcomeComboBox.getSelectedItem();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codePanel = new javax.swing.JPanel();
        codeLabel = new javax.swing.JLabel();
        codeTextField = new javax.swing.JTextField();
        locationPanel = new javax.swing.JPanel();
        locationLabel = new javax.swing.JLabel();
        locationTextField = new javax.swing.JTextField();
        startPanel = new javax.swing.JPanel();
        startLabel = new javax.swing.JLabel();
        startSpinner = new javax.swing.JSpinner();
        endPanel = new javax.swing.JPanel();
        endLabel = new javax.swing.JLabel();
        endSpinner = new javax.swing.JSpinner();
        objectivePanel = new javax.swing.JPanel();
        objectiveLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        objectiveTextArea = new javax.swing.JTextArea();
        outcomePanel = new javax.swing.JPanel();
        outcomeLabel = new javax.swing.JLabel();
        outcomeComboBox = new javax.swing.JComboBox<String>();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        codePanel.setLayout(new java.awt.GridLayout(1, 0));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/agentmanager/app/Texts"); // NOI18N
        codeLabel.setText(bundle.getString("mission.code")); // NOI18N
        codePanel.add(codeLabel);

        codeTextField.setText("jTextField1");
        codePanel.add(codeTextField);

        add(codePanel);

        locationPanel.setLayout(new java.awt.GridLayout(1, 0));

        locationLabel.setText(bundle.getString("mission.location")); // NOI18N
        locationPanel.add(locationLabel);

        locationTextField.setText("jTextField2");
        locationPanel.add(locationTextField);

        add(locationPanel);

        startPanel.setLayout(new java.awt.GridLayout(1, 0));

        startLabel.setText(bundle.getString("mission.start")); // NOI18N
        startPanel.add(startLabel);

        startSpinner.setModel(new javax.swing.SpinnerDateModel());
        startSpinner.setToolTipText("");
        startPanel.add(startSpinner);

        add(startPanel);

        endPanel.setLayout(new java.awt.GridLayout(1, 0));

        endLabel.setText(bundle.getString("mission.end")); // NOI18N
        endPanel.add(endLabel);

        endSpinner.setModel(new javax.swing.SpinnerDateModel());
        endPanel.add(endSpinner);

        add(endPanel);

        objectivePanel.setLayout(new java.awt.GridLayout(1, 0));

        objectiveLabel.setText(bundle.getString("mission.objective")); // NOI18N
        objectivePanel.add(objectiveLabel);

        objectiveTextArea.setColumns(20);
        objectiveTextArea.setRows(5);
        jScrollPane1.setViewportView(objectiveTextArea);

        objectivePanel.add(jScrollPane1);

        add(objectivePanel);

        outcomePanel.setLayout(new java.awt.GridLayout(1, 0));

        outcomeLabel.setText(bundle.getString("mission.outcome")); // NOI18N
        outcomePanel.add(outcomeLabel);
        outcomePanel.add(outcomeComboBox);

        add(outcomePanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel codeLabel;
    private javax.swing.JPanel codePanel;
    private javax.swing.JTextField codeTextField;
    private javax.swing.JLabel endLabel;
    private javax.swing.JPanel endPanel;
    private javax.swing.JSpinner endSpinner;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JPanel locationPanel;
    private javax.swing.JTextField locationTextField;
    private javax.swing.JLabel objectiveLabel;
    private javax.swing.JPanel objectivePanel;
    private javax.swing.JTextArea objectiveTextArea;
    private javax.swing.JComboBox<String> outcomeComboBox;
    private javax.swing.JLabel outcomeLabel;
    private javax.swing.JPanel outcomePanel;
    private javax.swing.JLabel startLabel;
    private javax.swing.JPanel startPanel;
    private javax.swing.JSpinner startSpinner;
    // End of variables declaration//GEN-END:variables
}
