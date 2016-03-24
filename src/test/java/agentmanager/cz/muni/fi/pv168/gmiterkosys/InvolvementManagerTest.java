package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.time.LocalDate;
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
public class InvolvementManagerTest {
    
    private InvolvementManagerImpl instance;
    
    public InvolvementManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp(){
        
        instance = new InvolvementManagerImpl();
    }
    
    @After
    public void tearDown() {
    }    
    
    /**
     * Test of findInvolvementByAgent method, of class InvolvementManager.
     */
    @Test
    public void testFindInvolvementByAgent() {
        System.out.println("findInvolvementByAgent");
        
        Agent agent = newAgent(1,"Bames Jond",007,LocalDate.MIN,LocalDate.MAX);
        Mission mission = newMission(0, "testMission", "testistan", LocalDateTime.MAX, LocalDateTime.MIN, "failMission", Outcome.FAILED);
        Involvement involvement = newInvolvement(0, LocalDateTime.MAX, LocalDateTime.MIN, mission, agent);
        
        instance = new InvolvementManagerImpl();
        
        instance.createInvolvement(involvement);
        
        List<Involvement> expResult = new ArrayList<>();
        expResult.add(involvement);
        List<Involvement> result = instance.findInvolvementByAgent(agent.getId());
        assertThat("returned involvements don't match insirted involvevement",result, is(expResult));
    }

    /**
     * Test of findInvolvementByMission method, of class InvolvementManager.
     */
    @Test
    public void testFindInvolvementByMission() {
        System.out.println("findInvolvementByMission");
        long missionId = 0L;
        instance = new InvolvementManagerImpl();
        List<Involvement> expResult = null;
        List<Involvement> result = instance.findInvolvementByMission(missionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInvolvementById method, of class InvolvementManager.
     */
    @Test
    public void testGetInvolvementById() {
        System.out.println("getInvolvementById");
        long id = 0L;
        instance = new InvolvementManagerImpl();
        Involvement expResult = null;
        Involvement result = instance.getInvolvementById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateInvolvement method, of class InvolvementManager.
     */
    @Test
    public void testUpdateInvolvement() {
        System.out.println("updateInvolvement");
        Involvement involvement = null;
        instance = new InvolvementManagerImpl();
        instance.updateInvolvement(involvement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    
    
    public Involvement newInvolvement(long id,LocalDateTime start,LocalDateTime end,Mission mission,Agent agent){
        Involvement temp = new Involvement();
        
        temp.setAgent(agent);
        temp.setStart(start);
        temp.setEnd(end);
        temp.setId(id);
        temp.setMission(mission);
        
        return temp;
    }
    
    public Agent newAgent(long id,String name,int level,LocalDate born,LocalDate died){
        Agent temp = new Agent();
        
        temp.setId(id);
        temp.setName(name);
        temp.setBorn(born);
        temp.setDied(died);
        temp.setLevel(level);
        
        return temp;
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
