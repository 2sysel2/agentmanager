package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;

/**
 *
 * @author Dominik Gmiterko
 */
public class AgentCreateDialog extends javax.swing.JDialog {
    
    private Agent result;

    public Agent getResult() {
        return result;
    }
    
    /**
     * Creates new form AgentDialog
     * @param parent
     * @param modal
     */
    public AgentCreateDialog(java.awt.Frame parent, boolean modal) {
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

        agentPanel = new javax.swing.JPanel();
        agentPanel1 = new cz.muni.fi.pv168.agentmanager.app.AgentPanel();
        actionPanel = new javax.swing.JPanel();
        createAgentButton = new javax.swing.JButton();
        cancelAgentButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        agentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        agentPanel.setLayout(new javax.swing.BoxLayout(agentPanel, javax.swing.BoxLayout.Y_AXIS));
        agentPanel.add(agentPanel1);

        actionPanel.setLayout(new java.awt.GridLayout(1, 0));

        createAgentButton.setText("Create");
        createAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAgentButtonActionPerformed(evt);
            }
        });
        actionPanel.add(createAgentButton);

        cancelAgentButton.setText("Cancel");
        cancelAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAgentButtonActionPerformed(evt);
            }
        });
        actionPanel.add(cancelAgentButton);

        agentPanel.add(actionPanel);

        getContentPane().add(agentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAgentButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelAgentButtonActionPerformed

    private void createAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAgentButtonActionPerformed
        Agent agent = new Agent();
        agent.setName(agentPanel1.getAgentName());
        agent.setBorn(agentPanel1.getAgentBorn());
        agent.setDied(agentPanel1.getAgentDied());
        agent.setLevel(agentPanel1.getAgentLevel());
        result = agent;
        this.setVisible(false);
    }//GEN-LAST:event_createAgentButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JPanel agentPanel;
    private cz.muni.fi.pv168.agentmanager.app.AgentPanel agentPanel1;
    private javax.swing.JButton cancelAgentButton;
    private javax.swing.JButton createAgentButton;
    // End of variables declaration//GEN-END:variables
}
