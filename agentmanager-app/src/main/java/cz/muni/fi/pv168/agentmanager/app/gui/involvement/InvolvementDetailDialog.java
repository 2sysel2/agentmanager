package cz.muni.fi.pv168.agentmanager.app.gui.involvement;

import cz.muni.fi.pv168.agentmanager.app.gui.AgentManagerMain;
import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentComboBoxModel;
import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionComboBoxModel;
import cz.muni.fi.pv168.agentmanager.app.workers.agent.AgentComboBoxWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionComboBoxWorker;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.Involvement;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Jaromir Sys
 */
public class InvolvementDetailDialog extends javax.swing.JDialog {

    /**
     * Creates new form InvolvementDialog
     */
    private Involvement involvement;

    public InvolvementDetailDialog(java.awt.Frame parent, Involvement involvement, AgentManager agentManager, MissionManager missionManager) {
        super(parent, false);
        initComponents();
        setInvolvement(involvement);
        
        AgentComboBoxModel agentComboBoxModel = new AgentComboBoxModel();
        MissionComboBoxModel missionComboBoxModel = new MissionComboBoxModel();
        
        involvementPanel1.getAgentComboBox().setModel(agentComboBoxModel);
        involvementPanel1.getMissionComboBox().setModel(missionComboBoxModel);
        
        //load data
        new AgentComboBoxWorker(agentManager, agentComboBoxModel).execute();
        new MissionComboBoxWorker(missionManager, missionComboBoxModel).execute();
        
        involvementPanel1.getAgentComboBox().getModel().addListDataListener(new ListDataListener() {

            @Override
            public void intervalAdded(ListDataEvent e) {
                involvementPanel1.getAgentComboBox().setSelectedItem(involvement.getAgent());
            }

            @Override
            public void intervalRemoved(ListDataEvent e) { }

            @Override
            public void contentsChanged(ListDataEvent e) { }
        });
        
        involvementPanel1.getMissionComboBox().getModel().addListDataListener(new ListDataListener() {

            @Override
            public void intervalAdded(ListDataEvent e) {
                involvementPanel1.getMissionComboBox().setSelectedItem(involvement.getMission());
            }

            @Override
            public void intervalRemoved(ListDataEvent e) { }

            @Override
            public void contentsChanged(ListDataEvent e) { }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        involvementPanel = new javax.swing.JPanel();
        involvementPanel1 = new cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementPanel();
        actionPanel = new javax.swing.JPanel();
        updateInvolvementButton = new javax.swing.JButton();
        removeInvolvementButton = new javax.swing.JButton();
        cancelInvolvementButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        involvementPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        involvementPanel.setLayout(new javax.swing.BoxLayout(involvementPanel, javax.swing.BoxLayout.PAGE_AXIS));
        involvementPanel.add(involvementPanel1);

        actionPanel.setLayout(new java.awt.GridLayout(1, 0));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/agentmanager/app/Texts"); // NOI18N
        updateInvolvementButton.setText(bundle.getString("action.update")); // NOI18N
        updateInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateInvolvementButtonActionPerformed(evt);
            }
        });
        actionPanel.add(updateInvolvementButton);

        removeInvolvementButton.setText(bundle.getString("action.remove")); // NOI18N
        removeInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeInvolvementButtonActionPerformed(evt);
            }
        });
        actionPanel.add(removeInvolvementButton);

        cancelInvolvementButton.setText(bundle.getString("action.cancel")); // NOI18N
        cancelInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelInvolvementButtonActionPerformed(evt);
            }
        });
        actionPanel.add(cancelInvolvementButton);

        involvementPanel.add(actionPanel);

        getContentPane().add(involvementPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateInvolvementButtonActionPerformed
        involvement.setAgent(involvementPanel1.getInvolvementAgent());
        involvement.setMission(involvementPanel1.getInvolvementMission());
        involvement.setStart(involvementPanel1.getInvolvementStart());
        involvement.setEnd(involvementPanel1.getInvolvementEnd());
        
        getMainWindow().updateInvolvement(involvement);
        close();
    }//GEN-LAST:event_updateInvolvementButtonActionPerformed

    private void removeInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeInvolvementButtonActionPerformed
        getMainWindow().removeInvolvement(involvement);
        close();
    }//GEN-LAST:event_removeInvolvementButtonActionPerformed

    private void cancelInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelInvolvementButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelInvolvementButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton cancelInvolvementButton;
    private javax.swing.JPanel involvementPanel;
    private cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementPanel involvementPanel1;
    private javax.swing.JButton removeInvolvementButton;
    private javax.swing.JButton updateInvolvementButton;
    // End of variables declaration//GEN-END:variables

    private void setInvolvement(Involvement involvement) {
        this.involvement = involvement;
        involvementPanel1.setInvolvement(involvement);
    }
    
    private AgentManagerMain getMainWindow() {
        return (AgentManagerMain) getParent();
    }

    private void close() {
        this.dispose();
    }
}
