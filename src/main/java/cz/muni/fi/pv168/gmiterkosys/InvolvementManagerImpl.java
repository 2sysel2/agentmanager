package cz.muni.fi.pv168.gmiterkosys;

import java.util.List;

import javax.sql.DataSource;


public class InvolvementManagerImpl implements InvolvementManager {

    public InvolvementManagerImpl(DataSource ds) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void createInvolvement(Involvement involvement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteInvolvement(Involvement involvement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Involvement> findAllInvolvements() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Involvement> findInvolvementByAgent(long agentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Involvement> findInvolvementByMission(long missionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Involvement getInvolvementById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateInvolvement(Involvement involvement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
