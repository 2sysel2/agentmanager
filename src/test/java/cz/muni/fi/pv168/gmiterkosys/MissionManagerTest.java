package cz.muni.fi.pv168.gmiterkosys;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import static org.hamcrest.CoreMatchers.*;
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
public class MissionManagerTest {
    
	private MissionManager missionManager;
    private DataSource ds;
    
    
    @Before
    public void setUp() throws SQLException {
        ds = prepareDataSource();
        try (Connection connection = ds.getConnection()){
        	
            connection.prepareStatement("CREATE TABLE mission ("
                    + "id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "code VARCHAR(255),"
                    + "location VARCHAR(255),"
                    + "\"start\" TIMESTAMP,"
                    + "\"end\" TIMESTAMP,"
                    + "objective VARCHAR(255),"
                    + "outcome VARCHAR(255)"
                    + ")").executeUpdate();
        }
        missionManager = new MissionManagerImpl(ds);
    }
    
    @After
    public void tearDown() throws SQLException {
        try (Connection connection = ds.getConnection()) {
            connection.prepareStatement("DROP TABLE mission").executeUpdate();
        }
    }
    
    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:mission");
        ds.setCreateDatabase("create");
        return ds;
    }
    

    /**
     * Test of createMission method, of class MissionManager.
     */
    @Test
    public void testCreateMission() {
        System.out.println("createMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "failMission", Outcome.FAILED);
        try {
            missionManager.createMission(mission);
            assertThat("mission was not added",missionManager.getMissionById(mission.getId()), is(mission));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateMissionNull() throws ServiceFailureException {
        System.out.println("createMission");
        Mission mission = null;

        missionManager.createMission(mission);

    }

    /**
     * Test of deleteMission method, of class MissionManager.
     */
    @Test
    public void testDeleteMission() {
        System.out.println("deleteMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "failMission", Outcome.FAILED);
        try {
            missionManager.createMission(mission);
            missionManager.deleteMission(mission);
            assertThat("returned value is not null after mission deletion",missionManager.getMissionById(mission.getId()), nullValue());
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteMissionNull() throws ServiceFailureException{
        
            missionManager.deleteMission(null);
        
    }

    /**
     * Test of findAllMissions method, of class MissionManager.
     */
    @Test
    public void testFindAllMissions() {
        System.out.println("findAllMissions");        
        List<Mission> expResult = new ArrayList<>();
        Mission mission1 = newMission(0, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "failMission", Outcome.FAILED);
        Mission mission2 = newMission(1, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "succesMission", Outcome.SUCCESSFUL);
        expResult.add(mission1);
        expResult.add(mission2);
        
        try {
            missionManager.createMission(mission1);
            missionManager.createMission(mission2);
            List<Mission> result = missionManager.findAllMissions();
            assertThat("returned list doesn't match list of all missions",result, is(expResult));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getMissionByCode method, of class MissionManager.
     */
    @Test
    public void testGetMissionByCode() {
        System.out.println("getMissionByCode");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "failMission", Outcome.FAILED);        
        try {
            missionManager.createMission(mission);
            assertThat("mission returned by getMissionByCode doesn't match mission created", missionManager.getMissionByCode(mission.getCode()), is(mission));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * Test of getMissionById method, of class MissionManager.
     */
    @Test
    public void testGetMissionById() {
        System.out.println("getMissionById");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "failMission", Outcome.FAILED);
        try {
            missionManager.createMission(mission);
            assertThat("mission returned by getMissionById doesn't match mission created", missionManager.getMissionById(mission.getId()), is(mission));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    /**
     * Test of updateMission method, of class MissionManager.
     */
    @Test
    public void testUpdateMission() {
        System.out.println("updateMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MIN, LocalDateTime.MAX, "failMission", Outcome.FAILED);        
        try {
            missionManager.createMission(mission);
            Mission tempMission = missionManager.getMissionById(mission.getId());
            tempMission.setOutcome(Outcome.SUCCESSFUL);

            missionManager.updateMission(tempMission);

            assertThat("updated mission doesn't equal mission parameter",missionManager.getMissionById(tempMission.getId()),is(tempMission));


            missionManager.updateMission(mission);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testStartAndEndDate() throws ServiceFailureException{
        Mission mission = newMission(0, "testMission", "testistan",  LocalDateTime.of(2001, 1, 1, 1, 1),LocalDateTime.of(2000, 3, 1, 0, 0), "failMission", Outcome.FAILED);
        missionManager.createMission(mission);
    }
    
    private Mission newMission(long id,String code,String location,LocalDateTime start,LocalDateTime end,String objective,Outcome outcome){
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
