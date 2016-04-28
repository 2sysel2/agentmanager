package cz.muni.fi.pv168.agentmanager.app;

/**
 *
 * @author Jaromir Sys
 */
public class InvolvementDialog extends javax.swing.JDialog {

    /**
     * Creates new form InvolvementDialog
     */
    public InvolvementDialog(java.awt.Frame parent, boolean modal) {
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

        involvementPanel = new javax.swing.JPanel();
        involvementPanel1 = new cz.muni.fi.pv168.agentmanager.app.InvolvementPanel();
        actionPanel = new javax.swing.JPanel();
        createInvolvementButton = new javax.swing.JButton();
        cancelInvolvementButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        involvementPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        involvementPanel.setLayout(new javax.swing.BoxLayout(involvementPanel, javax.swing.BoxLayout.PAGE_AXIS));
        involvementPanel.add(involvementPanel1);

        actionPanel.setLayout(new java.awt.GridLayout(1, 0));

        createInvolvementButton.setText("Create");
        createInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createInvolvementButtonActionPerformed(evt);
            }
        });
        actionPanel.add(createInvolvementButton);

        cancelInvolvementButton.setText("Cancel");
        actionPanel.add(cancelInvolvementButton);

        involvementPanel.add(actionPanel);

        getContentPane().add(involvementPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createInvolvementButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createInvolvementButtonActionPerformed

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
            java.util.logging.Logger.getLogger(InvolvementDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvolvementDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvolvementDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvolvementDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InvolvementDialog dialog = new InvolvementDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton cancelInvolvementButton;
    private javax.swing.JButton createInvolvementButton;
    private javax.swing.JPanel involvementPanel;
    private cz.muni.fi.pv168.agentmanager.app.InvolvementPanel involvementPanel1;
    // End of variables declaration//GEN-END:variables
}
