package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    
    public MissionManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
        MissionManager instance = new MissionManagerImpl();
        instance.createMission(mission);
        assertThat("mission was not added",instance.getMissionById(mission.getId()), is(mission));
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateMissionNull() {
        System.out.println("createMission");
        Mission mission = null;
        MissionManager instance = new MissionManagerImpl();
        instance.createMission(mission);
    }

    /**
     * Test of deleteMission method, of class MissionManager.
     */
    @Test
    public void testDeleteMission() {
        System.out.println("deleteMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        MissionManager instance = new MissionManagerImpl();
        instance.createMission(mission);
        instance.deleteMission(mission);
        assertThat("returned value is not null after mission deletion",instance.getMissionById(mission.getId()), nullValue());
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeleteMissionNull(){
        MissionManager instance = new MissionManagerImpl();
        instance.deleteMission(null);
    }

    /**
     * Test of findAllMissions method, of class MissionManager.
     */
    @Test
    public void testFindAllMissions() {
        System.out.println("findAllMissions");
        MissionManager instance = new MissionManagerImpl();
        
        List<Mission> expResult = new ArrayList<>();
        Mission mission1 = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        Mission mission2 = newMission(1, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "succesMission", Outcome.SUCCESSFUL);
        expResult.add(mission1);
        expResult.add(mission2);
        
        instance.createMission(mission1);
        instance.createMission(mission2);
        
        List<Mission> result = instance.findAllMissions();
        
        assertThat("returned list doesn't match list of all missions",result, is(expResult));
    }

    /**
     * Test of getMissionByCode method, of class MissionManager.
     */
    @Test
    public void testGetMissionByCode() {
        System.out.println("getMissionByCode");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        MissionManager instance = new MissionManagerImpl();
        
        instance.createMission(mission);
        
        assertThat("mission returned by getMissionByCode doesn't match mission created", instance.getMissionByCode(mission.getCode()), is(mission));
    }

    /**
     * Test of getMissionById method, of class MissionManager.
     */
    @Test
    public void testGetMissionById() {
        System.out.println("getMissionById");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        MissionManager instance = new MissionManagerImpl();
        
        instance.createMission(mission);
        
        assertThat("mission returned by getMissionById doesn't match mission created", instance.getMissionById(mission.getId()), is(mission));
    }

    /**
     * Test of updateMission method, of class MissionManager.
     */
    @Test
    public void testUpdateMission() {
        System.out.println("updateMission");
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        MissionManager instance = new MissionManagerImpl();
        
        instance.createMission(mission);
        
        Mission tempMission = instance.getMissionById(mission.getId());
        tempMission.setOutcome(Outcome.SUCCESSFUL);
        
        instance.updateMission(tempMission);
        
        assertThat("updated mission doesn't equal mission parameter",instance.getMissionById(tempMission.getId()),is(tempMission));
        
        
        instance.updateMission(mission);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
