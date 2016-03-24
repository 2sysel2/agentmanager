package cz.muni.fi.pv168.gmiterkosys;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Dominik Gmiterko
 */
public class AgentManagerImpl implements AgentManager {

    private final DataSource dataSource;

    public AgentManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createAgent(Agent agent) {

        validate(agent);

        if (agent.getId() != null) {
            throw new IllegalArgumentException("Agent id is already set!");
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement st = connection
                        .prepareStatement(
                                "INSERT INTO agent(name,born,died,level) VALUES (?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, agent.getName());
            st.setDate(2, Date.valueOf(agent.getBorn()));
            st.setDate(3, Date.valueOf(agent.getDied()));
            st.setInt(4, agent.getLevel());

            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new AssertionError("Incorrect number of rows inserted!");
            }

            ResultSet keys = st.getGeneratedKeys();
            agent.setId(getKey(keys, agent));

        } catch (SQLException e) {
            throw new ServiceFailureException("SQL Error when creating "
                    + agent, e);
        }
    }

    private Long getKey(ResultSet keyRS, Agent grave)
            throws ServiceFailureException, SQLException {
        if (keyRS.next()) {

            Long result = keyRS.getLong("id");

            if (keyRS.next()) {
                throw new ServiceFailureException(
                        "Create error: More generated keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException(
                    "Create error: No generated key found");
        }
    }

    public Agent getAgentById(Long id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement st = connection
                        .prepareStatement("SELECT id, name, born, died, level FROM agent WHERE id = ?")) {

            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Agent agent = resultAsAgent(rs);

                if (rs.next()) {
                    throw new ServiceFailureException(
                            "More entities with the same id found!");
                }

                return agent;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new ServiceFailureException(
                    "Error when retrieving agent with id " + id, e);
        }
    }

    public List<Agent> findAllAgents() {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement st = connection
                        .prepareStatement("SELECT id, name, born, died, level FROM agent")) {

            ResultSet rs = st.executeQuery();

            List<Agent> result = new ArrayList<>();
            while (rs.next()) {
                result.add(resultAsAgent(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new ServiceFailureException(
                    "Error when retrieving all agents", e);
        }
    }

    private Agent resultAsAgent(ResultSet result) throws SQLException {
        Agent agent = new Agent();
        agent.setId(result.getLong("id"));
        agent.setName(result.getString("name"));
        agent.setBorn(result.getDate("born").toLocalDate());
        agent.setDied(result.getDate("died").toLocalDate());
        agent.setLevel(result.getInt("level"));
        return agent;
    }

    public void updateAgent(Agent agent) {

        if (agent == null) {
            throw new IllegalArgumentException("Agent can't be null.");
        }

        validate(agent);

        if (agent.getId() == null) {
            throw new IllegalArgumentException("Agent id is missing.");
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement st = connection
                        .prepareStatement("UPDATE agent SET name = ?, born = ?, died = ?, level = ? WHERE id = ?")) {

            st.setString(1, agent.getName());
            st.setDate(2, Date.valueOf(agent.getBorn()));
            st.setDate(3, Date.valueOf(agent.getDied()));
            st.setInt(4, agent.getLevel());
            st.setLong(5, agent.getId());

            int count = st.executeUpdate();
            if (count == 0) {
                throw new ServiceFailureException("Agent " + agent
                        + " was not found in database!");
            } else if (count != 1) {
                throw new ServiceFailureException(
                        "Updated invalid count of rows! " + count);
            }
        } catch (SQLException e) {
            throw new ServiceFailureException("Error when updating agent "
                    + agent, e);
        }
    }

    public void deleteAgent(Agent agent) {

        if (agent == null) {
            throw new IllegalArgumentException("Agent can't be null.");
        }

        if (agent.getId() == null) {
            throw new IllegalArgumentException("Agent id is missing.");
        }

        try (Connection connection = dataSource.getConnection();
                PreparedStatement st = connection
                        .prepareStatement("DELETE FROM agent WHERE id = ?")) {

            st.setLong(1, agent.getId());

            int count = st.executeUpdate();
            if (count == 0) {
                throw new ServiceFailureException("Agent " + agent
                        + " was not found in database!");
            } else if (count != 1) {
                throw new ServiceFailureException(
                        "Deleted invalid count of rows! " + count);
            }

        } catch (SQLException e) {
            throw new ServiceFailureException("Error when updating agent "
                    + agent, e);
        }
    }

    private void validate(Agent agent) {
        // name
        if (agent.getName() == null) {
            throw new IllegalArgumentException("Agent can't have null name.");
        }
        if (agent.getName().isEmpty()) {
            throw new IllegalArgumentException("Agent can't be empty.");
        }
        // dates
        if (agent.getBorn() != null && agent.getDied() != null) {
            if (agent.getBorn().isAfter(agent.getDied())) {
                throw new IllegalArgumentException(
                        "Agent can't be born after death.");
            }
        }
        // level
        if (agent.getLevel() < 0 || agent.getLevel() > 10) {
            throw new IllegalArgumentException(
                    "Agent level is out of range (0-10).");
        }
    }
}
