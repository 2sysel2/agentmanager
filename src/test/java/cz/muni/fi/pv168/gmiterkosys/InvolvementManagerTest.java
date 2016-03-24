package cz.muni.fi.pv168.gmiterkosys;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.*;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

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
	private InvolvementManager involvementManager;

	@Before
	public void setUp() throws SQLException {
		ds = prepareDataSource();
                agent = newAgent(0, "Bames Jond", 007, LocalDate.of(1980, 1, 1), null);
                mission = newMission(0, "operation b*llsh*t", "testitstan", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 1), "don't f*ck up", Outcome.FAILED);
                

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
                    + "agent BIGINT REFERENCES AGENT(\"id\"),"
                    + "mission BIGINT REFERENCES MISSION(\"id\"),"
                    + "\"start\" TIMESTAMP,"
                    + "\"end\" TIMESTAMP"
                    + ")").execute();
		}

		agentManager = new AgentManagerImpl(ds);
		missionManager = new MissionManagerImpl(ds);
		involvementManager = new InvolvementManagerImpl(ds);
	}

	@After
	public void tearDown() throws SQLException {
		try (Connection connection = ds.getConnection()) {
			connection.prepareStatement("DROP TABLE involvment").execute();
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

	/**
	 * Test of findInvolvementByAgent method, of class InvolvementManager.
	 */
	@Test
	public void testFindInvolvementByAgent() {
		System.out.println("findInvolvementByAgent");

		Agent agent = newAgent(1, "Bames Jond", 007, LocalDate.MIN, LocalDate.MAX);
		Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN,
				"failMission", Outcome.FAILED);
		Involvement involvement = newInvolvement(0, LocalDateTime.MAX, LocalDateTime.MIN, mission, agent);

		involvementManager.createInvolvement(involvement);

		List<Involvement> expResult = new ArrayList<>();
		expResult.add(involvement);
		List<Involvement> result = involvementManager.findInvolvementByAgent(agent.getId());
		assertThat("returned involvements don't match insirted involvevement", result, is(expResult));
	}
        
        @Test
        /**
         * test of createInvolvement
         */
        public void testCreateInvolvement(){
            System.out.println("creatteInvolvemnt");
            
            
        }

	/**
	 * Test of findInvolvementByMission method, of class InvolvementManager.
	 */
	@Test
	public void testFindInvolvementByMission() {
		System.out.println("findInvolvementByMission");
		long missionId = 0L;
		List<Involvement> expResult = null;
		List<Involvement> result = involvementManager.findInvolvementByMission(missionId);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getInvolvementById method, of class InvolvementManager.
	 */
	@Test
	public void testGetInvolvementById() {
		System.out.println("getInvolvementById");
		long id = 0L;
		Involvement expResult = null;
		Involvement result = involvementManager.getInvolvementById(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of updateInvolvement method, of class InvolvementManager.
	 */
	@Test
	public void testUpdateInvolvement() {
		System.out.println("updateInvolvement");
		Involvement involvement = null;
		involvementManager.updateInvolvement(involvement);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	public Involvement newInvolvement(long id, LocalDateTime start, LocalDateTime end, Mission mission, Agent agent) {
		Involvement temp = new Involvement();

		temp.setAgent(agent);
		temp.setStart(start);
		temp.setEnd(end);
		temp.setId(id);
		temp.setMission(mission);

		return temp;
	}

	public Agent newAgent(long id, String name, int level, LocalDate born, LocalDate died) {
		Agent temp = new Agent();

		temp.setId(id);
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
