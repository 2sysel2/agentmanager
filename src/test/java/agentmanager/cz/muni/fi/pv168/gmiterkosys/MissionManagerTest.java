package agentmanager.cz.muni.fi.pv168.gmiterkosys;

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
    private MissionManager instance;
    private DataSource ds;
    //sadas
    public MissionManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        ds = prepareDataSource();
        try (Connection connection = ds.getConnection()){
            connection.prepareStatement("CREATE TABLE MISSION ("
                    + "id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                    + "code VARCHAR(255),"
                    + "location VARCHAR(255),"
                    + "start TIMESTAMP,"
                    + "end TIMESTAMP,"
                    + "objective VARCHAR(255),"
                    + "outcome VARCHAR(255)"
                    + "));").executeUpdate();
        }
        instance = new MissionManagerImpl(ds);
    }
    
    private static DataSource prepareDataSource() throws SQLException {
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("memory:mission");
        ds.setUser("sa");
        ds.setPassword("sa");
        ds.setCreateDatabase("create");
        return ds;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createMission method, of class MissionManager.
     */
    @Test
    public void testCreateMission() {
        System.out.println("createMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        try {
            instance.createMission(mission);
            assertThat("mission was not added",instance.getMissionById(mission.getId()), is(mission));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateMissionNull() {
        System.out.println("createMission");
        Mission mission = null;
        try {
            instance.createMission(mission);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of deleteMission method, of class MissionManager.
     */
    @Test
    public void testDeleteMission() {
        System.out.println("deleteMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        try {
            instance.createMission(mission);
            instance.deleteMission(mission);
            assertThat("returned value is not null after mission deletion",instance.getMissionById(mission.getId()), nullValue());
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeleteMissionNull(){
        try {
            instance.deleteMission(null);
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of findAllMissions method, of class MissionManager.
     */
    @Test
    public void testFindAllMissions() {
        System.out.println("findAllMissions");        
        List<Mission> expResult = new ArrayList<>();
        Mission mission1 = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        Mission mission2 = newMission(1, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "succesMission", Outcome.SUCCESSFUL);
        expResult.add(mission1);
        expResult.add(mission2);
        
        try {
            instance.createMission(mission1);
            instance.createMission(mission2);
            List<Mission> result = instance.findAllMissions();
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
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);        
        try {
            instance.createMission(mission);
            assertThat("mission returned by getMissionByCode doesn't match mission created", instance.getMissionByCode(mission.getCode()), is(mission));
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
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        try {
            instance.createMission(mission);
            assertThat("mission returned by getMissionById doesn't match mission created", instance.getMissionById(mission.getId()), is(mission));
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
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);        
        try {
            instance.createMission(mission);
            Mission tempMission = instance.getMissionById(mission.getId());
            tempMission.setOutcome(Outcome.SUCCESSFUL);

            instance.updateMission(tempMission);

            assertThat("updated mission doesn't equal mission parameter",instance.getMissionById(tempMission.getId()),is(tempMission));


            instance.updateMission(mission);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (ServiceFailureException ex) {
            Logger.getLogger(MissionManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testStartAndEndDate(){
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        fail("mission cannot end sooner then it started");
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
