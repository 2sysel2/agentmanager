package cz.muni.fi.pv168.agentmanager.app.gui;

import cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementCreateDialog;
import cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementDetailDialog;
import cz.muni.fi.pv168.agentmanager.app.gui.involvement.InvolvementTableModel;
import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionTableModel;
import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionDetailDialog;
import cz.muni.fi.pv168.agentmanager.app.gui.mission.MissionCreateDialog;
import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentDetailDialog;
import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentCreateDialog;
import cz.muni.fi.pv168.agentmanager.app.gui.agent.AgentTableModel;
import cz.muni.fi.pv168.agentmanager.app.workers.agent.AgentCreateWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.agent.AgentRemoveWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.agent.AgentTableModelWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.agent.AgentUpdateWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.involvement.InvolvementCreateWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.involvement.InvolvementRemoveWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.involvement.InvolvementTableModelWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.involvement.InvolvementUpdateWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionCreateWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionRemoveWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionTableModelWorker;
import cz.muni.fi.pv168.agentmanager.app.workers.mission.MissionUpdateWorker;
import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.AgentManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.Involvement;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManager;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import cz.muni.fi.pv168.gmiterkosys.MissionManagerImpl;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jaromir Sys
 */
public class AgentManagerMain extends javax.swing.JFrame {

    final static Logger log = LoggerFactory.getLogger(AgentManagerMain.class);
    private final ResourceBundle texts = ResourceBundle.getBundle("cz.muni.fi.pv168.agentmanager.app.Texts");
    private MissionTableModel missionTableModel;
    private AgentTableModel agentTableModel;
    private InvolvementTableModel involvementTableModel;
    private AgentManager agentManager;
    private MissionManager missionManager;
    private InvolvementManager involvementManager;

    /**
     * Creates new form AgentManagerMain
     */
    public AgentManagerMain(DataSource dataSource) {

        agentManager = new AgentManagerImpl(dataSource);
        missionManager = new MissionManagerImpl(dataSource);
        involvementManager = new InvolvementManagerImpl(dataSource, agentManager, missionManager);

        initComponents();

        agentTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (agentTable.getSelectedRow() != -1) {
                removeAgentButton.setEnabled(true);
                detailsAgentButton.setEnabled(true);
            }
        });

        missionTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (missionTable.getSelectedRow() != -1) {
                removeMissionButton.setEnabled(true);
                detailsMissionButton.setEnabled(true);
            }
        });

        involvementTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (involvementTable.getSelectedRow() != -1) {
                removeInvolvementButton.setEnabled(true);
                detailsInvolvementButton.setEnabled(true);
            }
        });

        missionTableModel = (MissionTableModel) missionTable.getModel();
        new MissionTableModelWorker(missionManager, missionTableModel).execute();

        agentTableModel = (AgentTableModel) agentTable.getModel();
        new AgentTableModelWorker(agentManager, agentTableModel).execute();

        involvementTableModel = (InvolvementTableModel) involvementTable.getModel();
        new InvolvementTableModelWorker(involvementManager, involvementTableModel).execute();
    }

    public void createMission(Mission mission) {
        new MissionCreateWorker(mission, missionManager, missionTableModel, this).execute();
    }

    public void updateMission(Mission mission) {
        new MissionUpdateWorker(mission, missionManager, missionTableModel, this).execute();
    }

    public void removeMission(Mission mission) {
        new MissionRemoveWorker(mission, missionManager, missionTableModel, this).execute();
        disableMissionActionButtons();
    }

    public void createAgent(Agent agent) {
        new AgentCreateWorker(agent, agentManager, agentTableModel, this).execute();
    }

    public void updateAgent(Agent agent) {
        new AgentUpdateWorker(agent, agentManager, agentTableModel, this).execute();
    }

    public void removeAgent(Agent agent) {
        new AgentRemoveWorker(agent, agentManager, agentTableModel, this).execute();
        disableAgentActionButtons();
    }

    public void createInvolvement(Involvement involvement) {
        new InvolvementCreateWorker(involvement, involvementManager, involvementTableModel, this).execute();
    }

    public void updateInvolvement(Involvement involvement) {
        new InvolvementUpdateWorker(involvement, involvementManager, involvementTableModel, this).execute();
    }

    public void removeInvolvement(Involvement involvement) {
        new InvolvementRemoveWorker(involvement, involvementManager, involvementTableModel, this).execute();
        disableInvolvementActionButtons();
    }

    private void disableAgentActionButtons() {
        agentTable.clearSelection();
        detailsAgentButton.setEnabled(false);
        removeAgentButton.setEnabled(false);
    }

    private void disableMissionActionButtons() {
        missionTable.clearSelection();
        detailsMissionButton.setEnabled(false);
        removeMissionButton.setEnabled(false);
    }

    private void disableInvolvementActionButtons() {
        involvementTable.clearSelection();
        detailsInvolvementButton.setEnabled(false);
        removeInvolvementButton.setEnabled(false);
    }

    private void showCreateAgentDialog() {
        AgentCreateDialog agentCreateDialog = new AgentCreateDialog(this);
        agentCreateDialog.setVisible(true);
    }

    private void showCreateInvolvementDialog() {
        InvolvementCreateDialog involvementCreateDialog = new InvolvementCreateDialog(this, agentManager, missionManager);
        involvementCreateDialog.setVisible(true);
    }

    private void showCreateMissionDialog() {
        MissionCreateDialog missionCreateDialog = new MissionCreateDialog(this);
        missionCreateDialog.setVisible(true);
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
        agentActionPanel = new javax.swing.JPanel();
        createAgentButton = new javax.swing.JButton();
        detailsAgentButton = new javax.swing.JButton();
        removeAgentButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        agentTable = new javax.swing.JTable();
        Involvements = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        involvementTable = new javax.swing.JTable();
        involvementActionPanel = new javax.swing.JPanel();
        createInvolvementButton = new javax.swing.JButton();
        detailsInvolvementButton = new javax.swing.JButton();
        removeInvolvementButton = new javax.swing.JButton();
        Missions = new javax.swing.JPanel();
        missionActionPanel = new javax.swing.JPanel();
        createMissionButton = new javax.swing.JButton();
        detailsMissionButton = new javax.swing.JButton();
        removeMissionButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        missionTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenuItem = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenu();
        createAgentMenuItem = new javax.swing.JMenuItem();
        createInvolvementMenuItem = new javax.swing.JMenuItem();
        createMissionMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setLayout(new java.awt.BorderLayout());

        Agents.setLayout(new java.awt.BorderLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cz/muni/fi/pv168/agentmanager/app/Texts"); // NOI18N
        createAgentButton.setText(bundle.getString("action.create.agent")); // NOI18N
        createAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAgentButtonActionPerformed(evt);
            }
        });
        agentActionPanel.add(createAgentButton);

        detailsAgentButton.setText(bundle.getString("action.details")); // NOI18N
        detailsAgentButton.setEnabled(false);
        detailsAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsAgentButtonActionPerformed(evt);
            }
        });
        agentActionPanel.add(detailsAgentButton);

        removeAgentButton.setText(bundle.getString("action.remove")); // NOI18N
        removeAgentButton.setEnabled(false);
        removeAgentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAgentButtonActionPerformed(evt);
            }
        });
        agentActionPanel.add(removeAgentButton);

        Agents.add(agentActionPanel, java.awt.BorderLayout.PAGE_START);

        agentTable.setModel(new AgentTableModel(texts));
        agentTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(agentTable);

        Agents.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("tab.agents"), Agents); // NOI18N

        Involvements.setLayout(new java.awt.BorderLayout());

        involvementTable.setModel(new InvolvementTableModel(texts));
        jScrollPane2.setViewportView(involvementTable);

        Involvements.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        createInvolvementButton.setText(bundle.getString("action.create.involvement")); // NOI18N
        createInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createInvolvementButtonActionPerformed(evt);
            }
        });
        involvementActionPanel.add(createInvolvementButton);

        detailsInvolvementButton.setText(bundle.getString("action.details")); // NOI18N
        detailsInvolvementButton.setEnabled(false);
        detailsInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsInvolvementButtonActionPerformed(evt);
            }
        });
        involvementActionPanel.add(detailsInvolvementButton);

        removeInvolvementButton.setText(bundle.getString("action.remove")); // NOI18N
        removeInvolvementButton.setEnabled(false);
        removeInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeInvolvementButtonActionPerformed(evt);
            }
        });
        involvementActionPanel.add(removeInvolvementButton);

        Involvements.add(involvementActionPanel, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab(bundle.getString("tab.involvements"), Involvements); // NOI18N

        Missions.setLayout(new java.awt.BorderLayout());

        createMissionButton.setText(bundle.getString("action.create.mission")); // NOI18N
        createMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMissionButtonActionPerformed(evt);
            }
        });
        missionActionPanel.add(createMissionButton);

        detailsMissionButton.setText(bundle.getString("action.details")); // NOI18N
        detailsMissionButton.setEnabled(false);
        detailsMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsMissionButtonActionPerformed(evt);
            }
        });
        missionActionPanel.add(detailsMissionButton);

        removeMissionButton.setText(bundle.getString("action.remove")); // NOI18N
        removeMissionButton.setEnabled(false);
        removeMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMissionButtonActionPerformed(evt);
            }
        });
        missionActionPanel.add(removeMissionButton);

        Missions.add(missionActionPanel, java.awt.BorderLayout.PAGE_START);

        missionTable.setModel(new MissionTableModel(texts));
        missionTable.getColumn(missionTable.getColumnName(5)).setCellRenderer(new MissionOutcomeCellRenderer(bundle));
        jScrollPane3.setViewportView(missionTable);

        Missions.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("tab.missions"), Missions); // NOI18N

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("");
        jTabbedPane1.getAccessibleContext().setAccessibleDescription("");

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        fileMenuItem.setText(bundle.getString("menu.file")); // NOI18N

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 0));
        exitMenuItem.setText(bundle.getString("menu.file.exit")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenuItem.add(exitMenuItem);

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
        aboutMenuItem.setText(bundle.getString("menu.file.about")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        fileMenuItem.add(aboutMenuItem);

        jMenuBar1.add(fileMenuItem);

        editMenuItem.setText(bundle.getString("menu.edit")); // NOI18N

        createAgentMenuItem.setText(bundle.getString("action.create.agent")); // NOI18N
        createAgentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAgentMenuItemActionPerformed(evt);
            }
        });
        editMenuItem.add(createAgentMenuItem);

        createInvolvementMenuItem.setText(bundle.getString("action.create.involvement")); // NOI18N
        createInvolvementMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createInvolvementMenuItemActionPerformed(evt);
            }
        });
        editMenuItem.add(createInvolvementMenuItem);

        createMissionMenuItem.setText(bundle.getString("action.create.mission")); // NOI18N
        createMissionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMissionMenuItemActionPerformed(evt);
            }
        });
        editMenuItem.add(createMissionMenuItem);

        jMenuBar1.add(editMenuItem);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, texts.getString("about.text"));
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void createMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMissionButtonActionPerformed
        showCreateMissionDialog();
    }//GEN-LAST:event_createMissionButtonActionPerformed

    private void createAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAgentButtonActionPerformed
        showCreateAgentDialog();
    }//GEN-LAST:event_createAgentButtonActionPerformed

    private void createInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createInvolvementButtonActionPerformed
        showCreateInvolvementDialog();
    }//GEN-LAST:event_createInvolvementButtonActionPerformed

    private void createAgentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAgentMenuItemActionPerformed
        showCreateAgentDialog();
    }//GEN-LAST:event_createAgentMenuItemActionPerformed

    private void createInvolvementMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createInvolvementMenuItemActionPerformed
        showCreateInvolvementDialog();
    }//GEN-LAST:event_createInvolvementMenuItemActionPerformed

    private void createMissionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMissionMenuItemActionPerformed
        showCreateMissionDialog();
    }//GEN-LAST:event_createMissionMenuItemActionPerformed

    private void removeAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAgentButtonActionPerformed
        Agent agent = agentTableModel.getAgent(agentTable.getSelectedRow());
        removeAgent(agent);
    }//GEN-LAST:event_removeAgentButtonActionPerformed

    private void removeMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMissionButtonActionPerformed
        Mission mission = missionTableModel.getMission(missionTable.getSelectedRow());
        removeMission(mission);
    }//GEN-LAST:event_removeMissionButtonActionPerformed

    private void removeInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeInvolvementButtonActionPerformed
        Involvement involvement = involvementTableModel.getInvolvement(involvementTable.getSelectedRow());
        removeInvolvement(involvement);
    }//GEN-LAST:event_removeInvolvementButtonActionPerformed

    private void detailsAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsAgentButtonActionPerformed
        Agent agent = agentTableModel.getAgent(agentTable.getSelectedRow());
        AgentDetailDialog agentDetailDialog = new AgentDetailDialog(this, agent, involvementManager);
        agentDetailDialog.setVisible(true);
    }//GEN-LAST:event_detailsAgentButtonActionPerformed

    private void detailsInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsInvolvementButtonActionPerformed
        Involvement involvement = involvementTableModel.getInvolvement(involvementTable.getSelectedRow());
        InvolvementDetailDialog involvementDetailDialog = new InvolvementDetailDialog(this, involvement, agentManager, missionManager);
        involvementDetailDialog.setVisible(true);

    }//GEN-LAST:event_detailsInvolvementButtonActionPerformed

    private void detailsMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsMissionButtonActionPerformed
        Mission mission = missionTableModel.getMission(missionTable.getSelectedRow());
        MissionDetailDialog missionDetailDialog = new MissionDetailDialog(this, mission, involvementManager);
        missionDetailDialog.setVisible(true);
    }//GEN-LAST:event_detailsMissionButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Agents;
    private javax.swing.JPanel Involvements;
    private javax.swing.JPanel Missions;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JPanel agentActionPanel;
    private javax.swing.JTable agentTable;
    private javax.swing.JButton createAgentButton;
    private javax.swing.JMenuItem createAgentMenuItem;
    private javax.swing.JButton createInvolvementButton;
    private javax.swing.JMenuItem createInvolvementMenuItem;
    private javax.swing.JButton createMissionButton;
    private javax.swing.JMenuItem createMissionMenuItem;
    private javax.swing.JButton detailsAgentButton;
    private javax.swing.JButton detailsInvolvementButton;
    private javax.swing.JButton detailsMissionButton;
    private javax.swing.JMenu editMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenuItem;
    private javax.swing.JPanel involvementActionPanel;
    private javax.swing.JTable involvementTable;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel missionActionPanel;
    private javax.swing.JTable missionTable;
    private javax.swing.JButton removeAgentButton;
    private javax.swing.JButton removeInvolvementButton;
    private javax.swing.JButton removeMissionButton;
    // End of variables declaration//GEN-END:variables

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, texts.getString(message));
    }

}
