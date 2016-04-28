package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Dominik Gmiterko
 */
public class AgentCreateDialog extends javax.swing.JDialog {

    private AgentManager agentManager;

    public AgentManager getAgentManager() {
        return agentManager;
    }

    public void setAgentManager(AgentManager agentManager) {
        this.agentManager = agentManager;
    }
    
    /**
     * Creates new form AgentDialog
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
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelAgentButtonActionPerformed

    private void createAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAgentButtonActionPerformed
        Agent agent = new Agent();
        agent.setName(agentPanel1.getAgentName());
        agent.setBorn(agentPanel1.getAgentBorn().toLocalDate());
        agent.setDied(agentPanel1.getAgentDied().toLocalDate());
        agent.setLevel(agentPanel1.getAgentLevel());
        agentManager.createAgent(agent);
        this.setVisible(false);
    }//GEN-LAST:event_createAgentButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AgentCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgentCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgentCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgentCreateDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AgentCreateDialog dialog = new AgentCreateDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel agentPanel;
    private cz.muni.fi.pv168.agentmanager.app.AgentPanel agentPanel1;
    private javax.swing.JButton cancelAgentButton;
    private javax.swing.JButton createAgentButton;
    // End of variables declaration//GEN-END:variables
}
