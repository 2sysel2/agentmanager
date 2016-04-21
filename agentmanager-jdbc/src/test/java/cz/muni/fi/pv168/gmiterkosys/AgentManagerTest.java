package cz.muni.fi.pv168.gmiterkosys;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dominik Gmiterko
 */
public class AgentManagerTest {

	private DataSource ds;
	private AgentManager agentManager;

	@Before
	public void setUp() throws SQLException {
		ds = prepareDataSource();

		try (Connection connection = ds.getConnection()) {
			connection.prepareStatement("CREATE TABLE agent ("
                    + "id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "\"name\" VARCHAR(255) NOT NULL,"
                    + "born DATE,"
                    + "died DATE,"
                    + "\"level\" SMALLINT"
                    + ")").execute();
			}

		agentManager = new AgentManagerImpl(ds);
	}

	@After
	public void tearDown() throws SQLException {
		try (Connection connection = ds.getConnection()) {
			connection.prepareStatement("DROP TABLE agent").execute();
		}
	}

	private static DataSource prepareDataSource() throws SQLException {
		EmbeddedDataSource ds = new EmbeddedDataSource();
		ds.setDatabaseName("memory:mission");
		ds.setCreateDatabase("create");
		return ds;
	}

    @Test
    public void testCreateAgent() throws Exception {

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent = newAgent("Linda Fox", bornDate, 1);

        agentManager.createAgent(agent);

        Agent result = agentManager.getAgentById(agent.getId());

        assertThat(result, is(equalTo(agent)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAgentInvalid() {
        agentManager.createAgent(null);
    }

    @Test
    public void testCreateAgentInvalidArguments() {

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent = newAgent("Linda Fox", bornDate, 1);
        agent.setId(1L);

        try {
            agentManager.createAgent(agent);
            fail("Agent id can't be set.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        agent = newAgent(null, bornDate, 1);
        try {
            agentManager.createAgent(agent);
            fail("Agent name isn't validated correctly.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        agent = newAgent("", bornDate, 1);
        try {
            agentManager.createAgent(agent);
            fail("Agent name isn't validated correctly.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        agent = newAgent("Linda Fox", bornDate, 0);
        try {
            agentManager.createAgent(agent);
            fail("Agent's level isn't validated correctly.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        agent = newAgent("Linda Fox", bornDate, 1);
        agentManager.createAgent(agent);

        agent = newAgent("Linda Fox", bornDate, 10);
        agentManager.createAgent(agent);

        agent = newAgent("Linda Fox", bornDate, 11);
        try {
            agentManager.createAgent(agent);
            fail("Agent's level isn't validated correctly.");
        } catch (IllegalArgumentException e) {
            // OK
        }

    }

    @Test
    public void testCreateAgentInvalidLocalDates() {

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        LocalDate diedDateIncorrect = LocalDate.of(1996, 1, 1); // smaller

        Agent agent = newAgent("Linda Fox", bornDate, 1);
        agent.setDied(diedDateIncorrect);
        try {
            agentManager.createAgent(agent);
            fail("Agent can't die before born. LocalDates aren't validated.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testFindAllAgents() throws Exception {

        assertTrue(agentManager.findAllAgents().isEmpty());

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent1 = newAgent("Linda Fox", bornDate, 6);
        Agent agent2 = newAgent("Nick Cool", bornDate, 5);

        agentManager.createAgent(agent1);
        agentManager.createAgent(agent2);

        Set<Agent> expected = new HashSet<Agent>();
        expected.add(agent1);
        expected.add(agent2);

        Set<Agent> actual = new HashSet<Agent>();
        actual.addAll(agentManager.findAllAgents());

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testUpdateAgent() throws Exception {

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent1 = newAgent("Linda Fox", bornDate, 6);
        Agent agent2 = newAgent("Nick Cool", bornDate, 5);

        agentManager.createAgent(agent1);
        agentManager.createAgent(agent2);

        LocalDate diedDate = LocalDate.of(2002, 2, 3);
        agent1.setDied(diedDate);

        agentManager.updateAgent(agent1);

        Agent actual1 = agentManager.getAgentById(agent1.getId());
        Agent actual2 = agentManager.getAgentById(agent2.getId());

        assertThat(actual1, is(equalTo(agent1)));
        assertThat(actual2, is(equalTo(agent2)));
    }

    @Test
    public void testupdateAgentInvalid() throws Exception {

        try {
            agentManager.updateAgent(null);
            fail("Agent can't be null. Agent isn't validated.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent = newAgent("Linda Fox", bornDate, 1);
        try {
            agentManager.updateAgent(agent);
            fail("Updating agent without id. Agent isn't validated.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testDeleteAgent() throws Exception {

        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent1 = newAgent("Linda Fox", bornDate, 6);
        Agent agent2 = newAgent("Nick Cool", bornDate, 5);

        agentManager.createAgent(agent1);
        agentManager.createAgent(agent2);

        agentManager.deleteAgent(agent1);

        Agent actual1 = agentManager.getAgentById(agent1.getId());
        Agent actual2 = agentManager.getAgentById(agent2.getId());

        assertThat(actual1, is(equalTo(null)));
        assertThat(actual2, is(equalTo(agent2)));

    }

    @Test
    public void testDeleteAgentInvalid() throws Exception {

        try {
            agentManager.deleteAgent(null);
            fail("Agent can't be null. Agent isn't validated.");
        } catch (IllegalArgumentException e) {
            // OK
        }
        LocalDate bornDate = LocalDate.of(2000, 1, 1);
        Agent agent = newAgent("Linda Fox", bornDate, 1);
        try {
            agentManager.deleteAgent(agent);
            fail("Deleting agent without id. Agent isn't validated.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    private Agent newAgent(String name, LocalDate born, int level) {
        Agent agent = new Agent();

        agent.setName(name);
        agent.setBorn(born);
        agent.setLevel(level);

        return agent;
    }
}
