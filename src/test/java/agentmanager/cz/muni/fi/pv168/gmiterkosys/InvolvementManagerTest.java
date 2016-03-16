package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.util.List;
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
    
    public InvolvementManagerTest() {
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
     * Test of createInvolvement method, of class InvolvementManager.
     */
    @Test
    public void testCreateInvolvement() {
        System.out.println("createInvolvement");
        Involvement involvement = null;
        InvolvementManager instance = new InvolvementManagerImpl();
        instance.createInvolvement(involvement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteInvolvement method, of class InvolvementManager.
     */
    @Test
    public void testDeleteInvolvement() {
        System.out.println("deleteInvolvement");
        Involvement involvement = null;
        InvolvementManager instance = new InvolvementManagerImpl();
        instance.deleteInvolvement(involvement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllInvolvements method, of class InvolvementManager.
     */
    @Test
    public void testFindAllInvolvements() {
        System.out.println("findAllInvolvements");
        InvolvementManager instance = new InvolvementManagerImpl();
        List<Involvement> expResult = null;
        List<Involvement> result = instance.findAllInvolvements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findInvolvementByAgent method, of class InvolvementManager.
     */
    @Test
    public void testFindInvolvementByAgent() {
        System.out.println("findInvolvementByAgent");
        long agentId = 0L;
        InvolvementManager instance = new InvolvementManagerImpl();
        Involvement expResult = null;
        Involvement result = instance.findInvolvementByAgent(agentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findInvolvementByMission method, of class InvolvementManager.
     */
    @Test
    public void testFindInvolvementByMission() {
        System.out.println("findInvolvementByMission");
        long missionId = 0L;
        InvolvementManager instance = new InvolvementManagerImpl();
        Involvement expResult = null;
        Involvement result = instance.findInvolvementByMission(missionId);
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
        InvolvementManager instance = new InvolvementManagerImpl();
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
        InvolvementManager instance = new InvolvementManagerImpl();
        instance.updateInvolvement(involvement);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    
}
