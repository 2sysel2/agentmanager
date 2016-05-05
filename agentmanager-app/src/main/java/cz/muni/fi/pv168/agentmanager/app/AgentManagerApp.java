package cz.muni.fi.pv168.agentmanager.app;

import cz.muni.fi.pv168.agentmanager.app.gui.AgentManagerMain;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dominik Gmiterko
 */
public class AgentManagerApp {

    final static Logger log = LoggerFactory.getLogger(AgentManagerApp.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        DataSource dataSource = prepareDataSource();

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException e) {
            log.error("Cannot set look and feel", e);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AgentManagerMain(dataSource).setVisible(true);
        });
    }

    private static DataSource prepareDataSource() {
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("./database");
        dataSource.setCreateDatabase("create");

        setUpDatabase(dataSource);

        return dataSource;
    }

    private static void setUpDatabase(DataSource dataSource) {

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
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) {
                log.error("Problem with initializing database.", e);
                System.exit(1);
            }
        }
    }
}
