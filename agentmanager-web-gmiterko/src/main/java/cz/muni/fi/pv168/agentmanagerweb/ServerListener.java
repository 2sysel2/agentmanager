package cz.muni.fi.pv168.agentmanagerweb;

import cz.muni.fi.pv168.gmiterkosys.AgentManager;
import cz.muni.fi.pv168.gmiterkosys.AgentManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManager;
import cz.muni.fi.pv168.gmiterkosys.InvolvementManagerImpl;
import cz.muni.fi.pv168.gmiterkosys.MissionManager;
import cz.muni.fi.pv168.gmiterkosys.MissionManagerImpl;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author Dominik Gmiterko
 */
@WebListener
public class ServerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent ev) {
        System.out.println("app starting..");
        ServletContext servletContext = ev.getServletContext();

        DataSource dataSource = getDataSource();

        AgentManager agentManager = new AgentManagerImpl(dataSource);
        MissionManager missionManager = new MissionManagerImpl(dataSource);
        InvolvementManager involvementManager = new InvolvementManagerImpl(dataSource, agentManager, missionManager);

        servletContext.setAttribute("agentManager", agentManager);
        servletContext.setAttribute("missionManager", missionManager);
        servletContext.setAttribute("involvementManager", involvementManager);

    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
        System.out.println("app stopping..");
    }

    private DataSource getDataSource() {

        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:mission");
        ds.setCreateDatabase("create");

        try (Connection connection = ds.getConnection()) {
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

        }

        return ds;
    }
}
