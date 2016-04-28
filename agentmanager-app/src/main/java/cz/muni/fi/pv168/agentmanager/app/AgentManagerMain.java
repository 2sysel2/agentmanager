package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.Involvement;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.awt.Dialog;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaromir Sys
 */
public class AgentManagerMain extends javax.swing.JFrame {

    private ButtonRenderer buttonRenderer;
    
    /**
     * Creates new form AgentManagerMain
     */
    public AgentManagerMain() {
        buttonRenderer = new ButtonRenderer();
        initComponents();
        
        MissionTableModel missionTableModel = (MissionTableModel) missionTable.getModel();
        Mission tempMission = getTestMission();
        missionTableModel.addMission(tempMission);
        missionTableModel.addMission(tempMission);
        missionTableModel.addMission(tempMission);
        
        AgentTableModel agentTableModel = (AgentTableModel) agentTable.getModel();
        Agent tempAgent = getTestAgent();
        agentTableModel.addAgent(tempAgent);
        agentTableModel.addAgent(tempAgent);
        agentTableModel.addAgent(tempAgent);
        
        InvolvementTableModel involvementTableModel = (InvolvementTableModel) involvementTable.getModel();
        Involvement temp = getTestInvolvement();
        involvementTableModel.addInvolvement(temp);
        involvementTableModel.addInvolvement(temp);
        involvementTableModel.addInvolvement(temp);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Agents = new javax.swing.JPanel();
        createAgentButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        agentTable = new javax.swing.JTable();
        Involvements = new javax.swing.JPanel();
        createInvolvementButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        involvementTable = new javax.swing.JTable();
        Missions = new javax.swing.JPanel();
        createMissionButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        missionTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setLayout(new java.awt.BorderLayout());

        Agents.setLayout(new java.awt.BorderLayout());

        createAgentButton.setText("Create Agent");
        Agents.add(createAgentButton, java.awt.BorderLayout.PAGE_START);

        AgentTableModel agentTableModel = new AgentTableModel();
        agentTable.setModel(agentTableModel);
        agentTable.getColumn("Edit").setCellRenderer(buttonRenderer);
        agentTable.getColumn("Delete").setCellRenderer(buttonRenderer);
        jScrollPane1.setViewportView(agentTable);

        Agents.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Agents", Agents);

        Involvements.setLayout(new java.awt.BorderLayout());

        createInvolvementButton.setText("Create Involvement");
        Involvements.add(createInvolvementButton, java.awt.BorderLayout.PAGE_START);

        InvolvementTableModel involvementTableModel = new InvolvementTableModel();
        involvementTable.setModel(involvementTableModel);
        involvementTable.getColumn("Edit").setCellRenderer(buttonRenderer);
        involvementTable.getColumn("Delete").setCellRenderer(buttonRenderer);
        jScrollPane2.setViewportView(involvementTable);

        Involvements.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Involvements", Involvements);

        Missions.setLayout(new java.awt.BorderLayout());

        createMissionButton.setText("Create Mission");
        Missions.add(createMissionButton, java.awt.BorderLayout.PAGE_START);

        MissionTableModel missionTableModel = new MissionTableModel();
        missionTable.setModel(missionTableModel);
        missionTable.getColumn("Edit").setCellRenderer(buttonRenderer);
        missionTable.getColumn("Delete").setCellRenderer(buttonRenderer);
        jScrollPane3.setViewportView(missionTable);

        Missions.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Missions", Missions);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        fileMenuItem.setText("File");

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 0));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenuItem.add(exitMenuItem);

        jMenuBar1.add(fileMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        jMenuBar1.add(aboutMenuItem);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "");
    }//GEN-LAST:event_aboutMenuItemActionPerformed

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
            java.util.logging.Logger.getLogger(AgentManagerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgentManagerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgentManagerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgentManagerMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AgentManagerMain().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Agents;
    private javax.swing.JPanel Involvements;
    private javax.swing.JPanel Missions;
    private javax.swing.JMenu aboutMenuItem;
    private javax.swing.JTable agentTable;
    private javax.swing.JButton createAgentButton;
    private javax.swing.JButton createInvolvementButton;
    private javax.swing.JButton createMissionButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JTable involvementTable;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable missionTable;
    // End of variables declaration//GEN-END:variables

    private Mission getTestMission() {
        Mission temp = new Mission();
        
        temp.setCode("CODE");
        temp.setLocation("Location");
        temp.setStart(LocalDateTime.MAX);
        temp.setEnd(LocalDateTime.MIN);
        temp.setObjective("Objective");
        temp.setOutcome(Outcome.FAILED);
        
        return temp;
    }
    
    private Agent getTestAgent() {
        Agent temp = new Agent();
        
        temp.setName("Linda Fox");
        temp.setBorn(LocalDate.MIN);
        temp.setDied(LocalDate.MAX);
        temp.setLevel(6);
        
        return temp;
    }
    
    private Involvement getTestInvolvement() {
        Involvement temp = new Involvement();
        
        temp.setMission(getTestMission());
        temp.setAgent(getTestAgent());
        temp.setStart(LocalDateTime.MIN);
        temp.setEnd(LocalDateTime.MAX);
        
        return temp;
    }
}
