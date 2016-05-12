package cz.muni.fi.pv168.agentmanager.app.gui.mission;

import cz.muni.fi.pv168.agentmanager.app.gui.AgentManagerMain;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import java.beans.PropertyChangeEvent;

/**
 *
 * @author Jaromir Sys
 */
public class MissionCreateDialog extends javax.swing.JDialog {

    /**
     * Creates new form MissionDialog
     *
     * @param parent
     */
    public MissionCreateDialog(java.awt.Frame parent) {
        super(parent, false);
        initComponents();
        missionPanel1.addPropertyChangeListener("panelValid", (PropertyChangeEvent evt) -> {
            createMissionButton.setEnabled((Boolean) evt.getNewValue());
        });
        missionPanel1.checkPanelValidity();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        missionPanel = new javax.swing.JPanel();
        missionPanel1 = new cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionPanel();
        actionPanel = new javax.swing.JPanel();
        createMissionButton = new javax.swing.JButton();
        cancelMissionButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        missionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        missionPanel.setLayout(new javax.swing.BoxLayout(missionPanel, javax.swing.BoxLayout.Y_AXIS));
        missionPanel.add(missionPanel1);

        actionPanel.setLayout(new java.awt.GridLayout(1, 0));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/agentmanager/app/Texts"); // NOI18N
        createMissionButton.setText(bundle.getString("action.create")); // NOI18N
        createMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMissionButtonActionPerformed(evt);
            }
        });
        actionPanel.add(createMissionButton);

        cancelMissionButton.setText(bundle.getString("action.cancel")); // NOI18N
        cancelMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelMissionButtonActionPerformed(evt);
            }
        });
        actionPanel.add(cancelMissionButton);

        missionPanel.add(actionPanel);

        getContentPane().add(missionPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelMissionButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelMissionButtonActionPerformed

    private void createMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMissionButtonActionPerformed
        Mission mission = new Mission();
        mission.setCode(missionPanel1.getMissionCode());
        mission.setLocation(missionPanel1.getMissionLocation());
        mission.setStart(missionPanel1.getMissionStart());
        mission.setEnd(missionPanel1.getMissionEnd());
        mission.setObjective(missionPanel1.getMissionObjective());
        mission.setOutcome(missionPanel1.getMissionOutcome());

        getMainWindow().createMission(mission);
        close();
    }//GEN-LAST:event_createMissionButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton cancelMissionButton;
    private javax.swing.JButton createMissionButton;
    private javax.swing.JPanel missionPanel;
    private cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionPanel missionPanel1;
    // End of variables declaration//GEN-END:variables

    private AgentManagerMain getMainWindow() {
        return (AgentManagerMain) getParent();
    }

    private void close() {
        this.dispose();
    }
}