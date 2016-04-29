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
				PreparedStatement st = connection.prepareStatement(
						"INSERT INTO agent(\"name\",born,died,\"level\") VALUES (?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS)) {

			st.setString(1, agent.getName());
			if (agent.getBorn() != null) {
				st.setDate(2, Date.valueOf(agent.getBorn()));
			} else {
				st.setDate(2, null);
			}
			if (agent.getDied() != null) {
				st.setDate(3, Date.valueOf(agent.getDied()));
			} else {
				st.setDate(3, null);
			}
			st.setInt(4, agent.getLevel());

			int addedRows = st.executeUpdate();
			if (addedRows != 1) {
				throw new AssertionError("Incorrect number of rows inserted!");
			}

			ResultSet keys = st.getGeneratedKeys();
			agent.setId(KeyGrabber.getKey(keys, agent));

		} catch (SQLException e) {
			throw new ServiceFailureException("SQL Error when creating " + agent, e);
		}
	}

	public Agent getAgentById(Long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement st = connection
						.prepareStatement("SELECT * FROM agent WHERE id = ?")) {

			st.setLong(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Agent agent = parseAgent(rs);

				if (rs.next()) {
					throw new ServiceFailureException("More entities with the same id found!");
				}

				return agent;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new ServiceFailureException("Error when retrieving agent with id " + id, e);
		}
	}

	public List<Agent> findAllAgents() {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement st = connection
						.prepareStatement("SELECT * FROM agent")) {

			ResultSet rs = st.executeQuery();

			List<Agent> result = new ArrayList<>();
			while (rs.next()) {
				result.add(parseAgent(rs));
			}
			return result;

		} catch (SQLException e) {
			throw new ServiceFailureException("Error when retrieving all agents", e);
		}
	}

	private Agent parseAgent(ResultSet result) throws SQLException {
		Agent agent = new Agent();
		agent.setId(result.getLong("id"));
		agent.setName(result.getString("name"));
		if (result.getDate("born") != null) {
			agent.setBorn(result.getDate("born").toLocalDate());
		}
		if (result.getDate("died") != null) {
			agent.setDied(result.getDate("died").toLocalDate());
		}
		agent.setLevel(result.getInt("level"));
		return agent;
	}

	public void updateAgent(Agent agent) {

		validate(agent);

		if (agent.getId() == null) {
			throw new IllegalArgumentException("Agent id is missing.");
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement st = connection
						.prepareStatement("UPDATE agent SET \"name\" = ?, born = ?, died = ?, \"level\" = ? WHERE id = ?")) {

			st.setString(1, agent.getName());
			st.setDate(2, Date.valueOf(agent.getBorn()));
			st.setDate(3, Date.valueOf(agent.getDied()));
			st.setInt(4, agent.getLevel());
			st.setLong(5, agent.getId());

			int count = st.executeUpdate();
			if (count == 0) {
				throw new ServiceFailureException("Agent " + agent + " was not found in database!");
			} else if (count != 1) {
				throw new ServiceFailureException("Updated invalid count of rows! " + count);
			}
		} catch (SQLException e) {
			throw new ServiceFailureException("Error when updating agent " + agent, e);
		}
	}

	public void removeAgent(Agent agent) {

		if (agent == null) {
			throw new IllegalArgumentException("Agent can't be null.");
		}

		if (agent.getId() == null) {
			throw new IllegalArgumentException("Agent id is missing.");
		}

		try (Connection connection = dataSource.getConnection();
				PreparedStatement st = connection.prepareStatement("DELETE FROM agent WHERE id = ?")) {

			st.setLong(1, agent.getId());

			int count = st.executeUpdate();
			if (count == 0) {
				throw new ServiceFailureException("Agent " + agent + " was not found in database!");
			} else if (count != 1) {
				throw new ServiceFailureException("Deleted invalid count of rows! " + count);
			}

		} catch (SQLException e) {
			throw new ServiceFailureException("Error when updating agent " + agent, e);
		}
	}

	private void validate(Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException("Agent can't be null.");
		}
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
				throw new IllegalArgumentException("Agent can't be born after death.");
			}
		}
		// level
		if (agent.getLevel() < Agent.MIN_LEVEL || agent.getLevel() > Agent.MAX_LEVEL) {
			throw new IllegalArgumentException("Agent level is out of range ("+Agent.MIN_LEVEL+"-"+Agent.MAX_LEVEL+").");
		}
	}
}
