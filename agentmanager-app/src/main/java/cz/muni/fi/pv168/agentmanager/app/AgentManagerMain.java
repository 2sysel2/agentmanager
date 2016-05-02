package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.gmiterkosys.Agent;
import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.AgentManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.Involvement;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManager;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.Mission;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import cz.muni.fi.pv168.gmiterkosys.MissionManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.ServiceFailureException;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author Jaromir Sys
 */
public class AgentManagerMain extends javax.swing.JFrame {

    private final ResourceBundle texts = ResourceBundle.getBundle("cz.muni.fi.pv168.agentmanager.app.Texts");
    private DataSource dataSource;
    private MissionManager missionManager;
    private AgentManager agentManager;
    private InvolvementManager involvementManager;
    private MissionTableModel missionTableModel;
    private AgentTableModel agentTableModel;
    private InvolvementTableModel involvementTableModel;
    
    private static DataSource prepareDataSource(){
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("./databaseeee");
        ds.setCreateDatabase("create");
        return ds;
    }
    
    public final void setUp(){
		dataSource = prepareDataSource();

		try (Connection connection = dataSource.getConnection()) {
                    connection.prepareStatement("CREATE TABLE agent ("
                        + "id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "\"name\" VARCHAR(255) NOT NULL,"
                        + "born DATE,"
                        + "died DATE,"
                        + "\"level\" SMALLINT"
                        + ")").execute();
			
                    connection.prepareStatement("CREATE TABLE mission ("
                        + "id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "code VARCHAR(255),"
                        + "location VARCHAR(255),"
                        + "\"start\" TIMESTAMP,"
                        + "\"end\" TIMESTAMP,"
                        + "objective VARCHAR(255),"
                        + "outcome VARCHAR(255)"
                        + ")").execute();
			
                    connection.prepareStatement("CREATE TABLE involvement ("
                        + "id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "agent BIGINT,"
                        + "mission BIGINT,"
                        + "\"start\" TIMESTAMP,"
                        + "\"end\" TIMESTAMP,"
                        + "FOREIGN KEY(agent) REFERENCES agent,"
                        + "FOREIGN KEY(mission) REFERENCES mission)").execute();
                }catch(SQLException e){
                    if(!e.getSQLState().equals("X0Y32")){
                        JOptionPane.showMessageDialog(this, "Database error: "+e.getMessage());
                        System.exit(0);
                    }
                }
		agentManager = new AgentManagerImpl(dataSource);
                missionManager = new MissionManagerImpl(dataSource);
                involvementManager = new InvolvementManagerImpl(dataSource, agentManager, missionManager);
	}
    
    /**
     * Creates new form AgentManagerMain
     */
    public AgentManagerMain(){
        initComponents();
        setUp();
        
        agentTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            removeAgentButton.setEnabled(true);
            detailsAgentButton.setEnabled(true);
        });
        
        missionTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            removeMissionButton.setEnabled(true);
            detailsMissionButton.setEnabled(true);
        });
        
        involvementTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            removeInvolvementButton.setEnabled(true);
            detailsInvolvementButton.setEnabled(true);
        });
        
        missionTableModel = (MissionTableModel) missionTable.getModel();
        missionManager.findAllMissions().stream().forEach((mission) -> {
            missionTableModel.addMission(mission);
        });
        
        agentTableModel = (AgentTableModel) agentTable.getModel();
        agentManager.findAllAgents().stream().forEach((agent) -> {
            agentTableModel.addAgent(agent);
        });
        
        involvementTableModel = (InvolvementTableModel) involvementTable.getModel();
        involvementManager.findAllInvolvements().stream().forEach((involvement) -> {
            involvementTableModel.addInvolvement(involvement);
        });
    }
    
    private void addInvolvement(Involvement i){
        try{
            involvementManager.createInvolvement(i);
            involvementTableModel.addInvolvement(i);
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    
    private void addMission(Mission m){
        try{
            missionManager.createMission(m);
            missionTableModel.addMission(m);
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    
    private void addAgent(Agent a){
        try{
            agentManager.createAgent(a);
            agentTableModel.addAgent(a);
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }
    
    private void updateAgent(){
        try{
            agentManager.updateAgent(agentTableModel.getAgent(agentTable.getSelectedRow()));
            agentTableModel.updateAgent();
            disableAgentActionButtons();
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "Ops something went wrong");
        }
    }
    
    private void removeAgent() throws HeadlessException {
        try{
            agentManager.removeAgent(agentTableModel.getAgent(agentTable.getSelectedRow()));
            agentTableModel.removeAgent(agentTable.getSelectedRow());
            disableAgentActionButtons();
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "You can't remove agent who is assigned to an involvement");
        }
    }
    
    private void updateMission(){
        try{
            missionManager.updateMission(missionTableModel.getMission(missionTable.getSelectedRow()));
            missionTableModel.updateMission();
            disableMissionActionButtons();
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "Ops something went wrong");
        }
    }
    
    private void removeMission(){
        try{
            missionManager.removeMission(missionTableModel.getMission(missionTable.getSelectedRow()));
            missionTableModel.removeMission(missionTable.getSelectedRow());
            disableMissionActionButtons();
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "Ops");
        }
    }
    
    private void updateInvolvement(){
        try{
            involvementManager.updateInvolvement(involvementTableModel.getInvolvement(involvementTable.getSelectedRow()));
            involvementTableModel.updateInvolvement();
            disableInvolvementActionButtons();
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "Ops something went wrong");
        }
    }
    
    private void removeInvolvement() {
        try{
            involvementManager.removeInvolvement(involvementTableModel.getInvolvement(involvementTable.getSelectedRow()));
            involvementTableModel.removeInvolvement(involvementTable.getSelectedRow());
            disableInvolvementActionButtons();
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "Ops");
        }
    }
    
    private void disableAgentActionButtons() {
        detailsAgentButton.setEnabled(false);
        removeAgentButton.setEnabled(false);
        agentTable.clearSelection();
    }
    
    private void disableMissionActionButtons() {
        detailsMissionButton.setEnabled(false);
        removeMissionButton.setEnabled(false);
        missionTable.clearSelection();
    }
    
    private void disableInvolvementActionButtons(){
        detailsInvolvementButton.setEnabled(false);
        removeInvolvementButton.setEnabled(false);
        involvementTable.clearSelection();
    }
    
    private void showCreateAgentDialog() {
        AgentCreateDialog agentCreateDialog = new AgentCreateDialog(this, true);
        agentCreateDialog.setVisible(true);
        Agent result = agentCreateDialog.getResult();
        if(result != null){
            addAgent(result);
        }
    }

    private void showCreateInvolvementDialog() {
        InvolvementCreateDialog involvementCreateDialog = new InvolvementCreateDialog(this,true,agentManager.findAllAgents(),missionManager.findAllMissions());
        involvementCreateDialog.setVisible(true);
        Involvement result = involvementCreateDialog.getResult();
        if(result != null){
            addInvolvement(result);
        }
    }

    private void showCreateMissionDialog() {
        MissionCreateDialog missionCreateDialog = new MissionCreateDialog(this, true);
        missionCreateDialog.setVisible(true);
        Mission result = missionCreateDialog.getResult();
        if(result != null){
            addMission(result);
        }
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
        removeAgent();
    }//GEN-LAST:event_removeAgentButtonActionPerformed

    private void removeMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMissionButtonActionPerformed
        try{
            missionManager.removeMission(missionTableModel.getMission(missionTable.getSelectedRow()));
            missionTableModel.removeMission(missionTable.getSelectedRow());
            
        }catch(ServiceFailureException e){
            JOptionPane.showMessageDialog(this, "You can't remove mission which has assigned agents in an involvement");
        }
    }//GEN-LAST:event_removeMissionButtonActionPerformed

    private void removeInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeInvolvementButtonActionPerformed
        removeInvolvement();
        
    }//GEN-LAST:event_removeInvolvementButtonActionPerformed

    private void detailsAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsAgentButtonActionPerformed
        AgentDetailDialog agentDetailDialog = new AgentDetailDialog(this,true);
        agentDetailDialog.setAgent(agentTableModel.getAgent(agentTable.getSelectedRow()));
        agentDetailDialog.setInvolvements(involvementManager.findInvolvementByAgent(agentTableModel.getAgent(agentTable.getSelectedRow()).getId()));
        agentDetailDialog.setVisible(true);
        
        switch(agentDetailDialog.getResult()){
            case CANCEL:break;
            case REMOVE: this.removeAgent();break;
            case UPDATE: this.updateAgent();break;
        }
    }//GEN-LAST:event_detailsAgentButtonActionPerformed

    private void detailsInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsInvolvementButtonActionPerformed
        InvolvementDetailDialog involvementDetailDialog = new InvolvementDetailDialog(this,true,agentManager.findAllAgents(),missionManager.findAllMissions());
        involvementDetailDialog.setInvolvement(involvementTableModel.getInvolvement(involvementTable.getSelectedRow()));
        involvementDetailDialog.setVisible(true);
        switch(involvementDetailDialog.getResult()){
            case CANCEL:break;
            case REMOVE: this.removeInvolvement();break;
            case UPDATE: this.updateInvolvement();break;
        }
    }//GEN-LAST:event_detailsInvolvementButtonActionPerformed

    private void detailsMissionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsMissionButtonActionPerformed
        MissionDetailDialog missionDetailDialog = new MissionDetailDialog(this, true);
        missionDetailDialog.setMission(missionTableModel.getMission(missionTable.getSelectedRow()));
        missionDetailDialog.setInvolvements(involvementManager.findInvolvementByMission(missionTableModel.getMission(missionTable.getSelectedRow()).getId()));
        missionDetailDialog.setVisible(true);
        switch(missionDetailDialog.getResult()){
            case CANCEL:break;
            case REMOVE: this.removeMission();break;
            case UPDATE: this.updateMission();break;
        }
    }//GEN-LAST:event_detailsMissionButtonActionPerformed


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

    
}