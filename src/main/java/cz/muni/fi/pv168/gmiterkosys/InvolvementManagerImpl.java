package cz.muni.fi.pv168.gmiterkosys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;


public class InvolvementManagerImpl implements InvolvementManager {

    private DataSource dataSource;
    
    public InvolvementManagerImpl(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public void createInvolvement(Involvement involvement) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO involvement (agent,mission,\"start\",\"end\") VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            ){
            validate(involvement);
            st.setLong(1, involvement.getAgent().getId());
            st.setLong(2, involvement.getMission().getId());
            st.setTimestamp(3, Timestamp.valueOf(involvement.getStart()));
            st.setTimestamp(4, Timestamp.valueOf(involvement.getEnd()));
            
            int count = st.executeUpdate();
            if (count != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + count + ") inserted when trying to insert involvement " + involvement);
            }

            ResultSet keyRS = st.getGeneratedKeys();
            involvement.setId(KeyGrabber.getKey(keyRS));
        }
           catch (SQLException ex) {
            throw new ServiceFailureException("failed to insert new involvement",ex);
        }
    }
    
    private void validate(Involvement involvement){
        if(involvement == null){
            throw new IllegalArgumentException("involvement is null");
        }
        if(involvement.getAgent() == null){
            throw new IllegalArgumentException("involvement agent is null");
        }
        if(involvement.getMission() == null){
            throw new IllegalArgumentException("involvement mission is null");
        }
        if(involvement.getStart()!= null
                &&involvement.getEnd()!= null
                &&involvement.getStart().isAfter(involvement.getEnd())){
            throw new IllegalArgumentException("involvemnt can't end before it started");
        }
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
