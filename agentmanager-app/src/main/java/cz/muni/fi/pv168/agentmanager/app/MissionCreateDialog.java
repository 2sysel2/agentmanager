package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Jaromir Sys
 */
public class MissionCreateDialog extends javax.swing.JDialog {

    
    private MissionManager missionManager;

    public MissionManager getMissionManager() {
        return missionManager;
    }

    public void setMissionManager(MissionManager missionManager) {
        this.missionManager = missionManager;
    }
    
    /**
     * Creates new form MissionDialog
     */
    public MissionCreateDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
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
        missionPanel1 = new cz.muni.fi.pv168.agentmanager.app.MissionPanel();
        actionPanel = new javax.swing.JPanel();
        createMissionButton = new javax.swing.JButton();
        cancelMissionButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        missionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        missionPanel.setLayout(new javax.swing.BoxLayout(missionPanel, javax.swing.BoxLayout.Y_AXIS));
        missionPanel.add(missionPanel1);

        actionPanel.setLayout(new java.awt.GridLayout(1, 0));

        createMissionButton.setText("Create");
        createMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMissionButtonActionPerformed(evt);
            }
        });
        actionPanel.add(createMissionButton);

        cancelMissionButton.setText("Cancel");
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
        this.setVisible(false);
    }//GEN-LAST:event_cancelMissionButtonActionPerformed

    private void createMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMissionButtonActionPerformed
        Mission mission = new Mission();
        mission.setCode(missionPanel1.getMissionCode());
        mission.setLocation(missionPanel1.getMissionLocation());
        mission.setStart(missionPanel1.getMissionStart());
        mission.setEnd(missionPanel1.getMissionEnd());
        mission.setObjective(missionPanel1.getMissionObjective());
        mission.setOutcome(missionPanel1.getMissionOutcome());
        
        missionManager.createMission(mission);
        this.setVisible(false);
    }//GEN-LAST:event_createMissionButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MissionCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MissionCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MissionCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MissionCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MissionCreateDialog dialog = new MissionCreateDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton cancelMissionButton;
    private javax.swing.JButton createMissionButton;
    private javax.swing.JPanel missionPanel;
    private cz.muni.fi.pv168.agentmanager.app.MissionPanel missionPanel1;
    // End of variables declaration//GEN-END:variables
}
