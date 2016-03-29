package cz.muni.fi.pv168.gmiterkosys;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaromir Sys
 */
public class InvolvementManagerTest {

	private DataSource ds;
	private AgentManager agentManager;
	private MissionManager missionManager;
    private Agent agent;
    private Mission mission;
    private Involvement involvement;
	private InvolvementManager involvementManager;

	@Before
	public void setUp() throws SQLException {
		ds = prepareDataSource();
        agent = newAgent("Bames Jond", 007, LocalDate.of(1980, 1, 1), null);
        mission = newMission(0, "operation b*llsh*t", "testitstan", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 1), "don't f*ck up", Outcome.FAILED);
        mission.setId(null);
        involvement = new Involvement();
        involvement.setAgent(agent);
        involvement.setMission(mission);
        involvement.setStart(mission.getStart());
        involvement.setEnd(mission.getEnd());
                
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
		}

		agentManager = new AgentManagerImpl(ds);
		missionManager = new MissionManagerImpl(ds);
        
        agentManager.createAgent(agent);
        missionManager.createMission(mission);
        
		involvementManager = new InvolvementManagerImpl(ds, agentManager, missionManager);
	}

	@After
	public void tearDown() throws SQLException {
		try (Connection connection = ds.getConnection()) {
			connection.prepareStatement("DROP TABLE involvement").execute();
			connection.prepareStatement("DROP TABLE mission").execute();
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
    public void testCreateInvolvement() throws Exception {

        LocalDateTime start = LocalDateTime.of(2000, 1, 3, 12, 50);
        LocalDateTime end = LocalDateTime.of(2000, 1, 9, 10, 20);

        Involvement involvement = newInvolvement(start, end, mission, agent);

        involvementManager.createInvolvement(involvement);

        Involvement result = involvementManager.getInvolvementById(involvement.getId());

        assertThat(result, is(equalTo(involvement)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvolvementInvalid() {
        involvementManager.createInvolvement(null);
    }

    @Test
    public void testCreateInvolvementInvalidArguments() {

       
        LocalDateTime start = LocalDateTime.of(2000, 1, 3, 12, 50);
        LocalDateTime end = LocalDateTime.of(2000, 1, 9, 10, 20);

        Involvement involvement = newInvolvement(start, end, mission, null);
        try {
            involvementManager.createInvolvement(involvement);
            fail("Involvement agent cannot be null.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        involvement = newInvolvement(start, end, null, agent);
        try {
            involvementManager.createInvolvement(involvement);
            fail("Involvement mission cannot be null.");
        } catch (IllegalArgumentException e) {
            // OK
        }

        involvement = newInvolvement(start, end, mission, agent);
        involvement.setId(1L);
        try {
            involvementManager.createInvolvement(involvement);
            fail("Involvement id can't be set.");
        } catch (IllegalArgumentException e) {
            // OK
        }

    }

    @Test
    public void testCreateInvolvementInvalidDates() {

        LocalDateTime start = LocalDateTime.of(2000, 1, 3, 12, 50);
        LocalDateTime end = LocalDateTime.of(1996, 1, 9, 10, 20); // smaller

        Involvement involvement = newInvolvement(start, end, mission, agent);
        try {
            involvementManager.createInvolvement(involvement);
            fail("Involvement can't end before its end. Dates aren't validated.");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
	
	/**
	 * Test of findInvolvementByAgent method, of class InvolvementManager.
	 */
	@Test
	public void testFindInvolvementByAgent() {

		involvementManager.createInvolvement(involvement);

		List<Involvement> expResult = new ArrayList<>();
		expResult.add(involvement);
		List<Involvement> result = involvementManager.findInvolvementByAgent(agent.getId());
		assertThat("returned involvements don't match insirted involvevement", result, is(expResult));
	}

	/**
	 * Test of findInvolvementByMission method, of class InvolvementManager.
	 */
	@Test
	public void testFindInvolvementByMission() {

		involvementManager.createInvolvement(involvement);

		List<Involvement> expResult = new ArrayList<>();
		expResult.add(involvement);
		List<Involvement> result = involvementManager.findInvolvementByMission(mission.getId());
		assertThat("returned involvements don't match insirted involvevement", result, is(expResult));
	}

	/**
	 * Test of updateInvolvement method, of class InvolvementManager.
	 */
	@Test
	public void testUpdateInvolvement() {
		
                involvementManager.createInvolvement(involvement);
                involvement.setEnd(LocalDateTime.of(2000, 1, 1, 0, 2));
		involvementManager.updateInvolvement(involvement);
                
                assertThat("database involvement does not match updated involvement",involvementManager.getInvolvementById(involvement.getId()), is(involvement));
	}
        
    @Test
    public void testDeleteInvolvement() {
        involvementManager.createInvolvement(involvement);
        involvementManager.deleteInvolvement(involvement);
        assertThat("null should be returned after deleteInvolvement",involvementManager.getInvolvementById(involvement.getId()), nullValue());
    }

	public Involvement newInvolvement(LocalDateTime start, LocalDateTime end, Mission mission, Agent agent) {
		Involvement temp = new Involvement();

		temp.setAgent(agent);
		temp.setStart(start);
		temp.setEnd(end);
		temp.setMission(mission);

		return temp;
	}

	public Agent newAgent(String name, int level, LocalDate born, LocalDate died) {
		Agent temp = new Agent();

		temp.setName(name);
		temp.setBorn(born);
		temp.setDied(died);
		temp.setLevel(level);

		return temp;
	}

	private Mission newMission(long id, String code, String location, LocalDateTime start, LocalDateTime end,
			String objective, Outcome outcome) {
		Mission temp = new Mission();

		temp.setId(id);
		temp.setCode(code);
		temp.setStart(start);
		temp.setEnd(end);
		temp.setLocation(location);
		temp.setOutcome(outcome);
		temp.setObjective(objective);

		return temp;
	}
}
