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
import cz.muni.fi.pv168.gmiterkosys.Outcome;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
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
        
        Involvement temp = getTestInvolvement();
        involvementTableModel.addInvolvement(temp);
        involvementTableModel.addInvolvement(temp);
        involvementTableModel.addInvolvement(temp);
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
        Agents.add(createAgentButton, java.awt.BorderLayout.PAGE_START);

        agentTable.setModel(new AgentTableModel(texts));
        agentTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(agentTable);

        Agents.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("tab.agents"), Agents); // NOI18N

        Involvements.setLayout(new java.awt.BorderLayout());

        createInvolvementButton.setText(bundle.getString("action.create.involvement")); // NOI18N
        createInvolvementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createInvolvementButtonActionPerformed(evt);
            }
        });
        Involvements.add(createInvolvementButton, java.awt.BorderLayout.PAGE_START);

        involvementTable.setModel(new InvolvementTableModel(texts));
        jScrollPane2.setViewportView(involvementTable);

        Involvements.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(bundle.getString("tab.involvements"), Involvements); // NOI18N

        Missions.setLayout(new java.awt.BorderLayout());

        createMissionButton.setText(bundle.getString("action.create.mission")); // NOI18N
        createMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createMissionButtonActionPerformed(evt);
            }
        });
        Missions.add(createMissionButton, java.awt.BorderLayout.PAGE_START);

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
        MissionCreateDialog missionCreateDialog = new MissionCreateDialog(this, true);
        missionCreateDialog.setVisible(true);
        Mission result = missionCreateDialog.getResult();
        if(result != null){
            addMission(result);
        } 
    }//GEN-LAST:event_createMissionButtonActionPerformed

    private void createAgentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAgentButtonActionPerformed
        AgentCreateDialog agentCreateDialog = new AgentCreateDialog(this, true);
        agentCreateDialog.setVisible(true);
        Agent result = agentCreateDialog.getResult();
        if(result != null){
            addAgent(result);
        }
        
    }//GEN-LAST:event_createAgentButtonActionPerformed

    private void createInvolvementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createInvolvementButtonActionPerformed
        InvolvementCreateDialog involvementCreateDialog = new InvolvementCreateDialog(this,true,agentManager,missionManager,involvementManager);
        involvementCreateDialog.setVisible(true);
        Involvement result = involvementCreateDialog.getResult();
        if(result != null){
            addInvolvement(result);
        }
    }//GEN-LAST:event_createInvolvementButtonActionPerformed

    private void createAgentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createAgentMenuItemActionPerformed
        AgentCreateDialog agentCreateDialog = new AgentCreateDialog(this, true);
        agentCreateDialog.setVisible(true);
        Agent result = agentCreateDialog.getResult();
        if(result != null){
            addAgent(result);
        }
    }//GEN-LAST:event_createAgentMenuItemActionPerformed

    private void createInvolvementMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createInvolvementMenuItemActionPerformed
        InvolvementCreateDialog involvementCreateDialog = new InvolvementCreateDialog(this,true,agentManager,missionManager,involvementManager);
        involvementCreateDialog.setVisible(true);
        Involvement result = involvementCreateDialog.getResult();
        if(result != null){
            addInvolvement(result);
        }
    }//GEN-LAST:event_createInvolvementMenuItemActionPerformed

    private void createMissionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createMissionMenuItemActionPerformed
        MissionCreateDialog missionCreateDialog = new MissionCreateDialog(this, true);
        missionCreateDialog.setVisible(true);
        Mission result = missionCreateDialog.getResult();
        if(result != null){
            addMission(result);
        }
    }//GEN-LAST:event_createMissionMenuItemActionPerformed

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
    private javax.swing.JTable agentTable;
    private javax.swing.JButton createAgentButton;
    private javax.swing.JMenuItem createAgentMenuItem;
    private javax.swing.JButton createInvolvementButton;
    private javax.swing.JMenuItem createInvolvementMenuItem;
    private javax.swing.JButton createMissionButton;
    private javax.swing.JMenuItem createMissionMenuItem;
    private javax.swing.JMenu editMenuItem;
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
        temp.setStart(LocalDateTime.of(2000, 3, 1, 0, 0));
        temp.setEnd(LocalDateTime.of(2001, 1, 1, 1, 1));
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
