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
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "DELETE FROM involvement WHERE id = ?");
            ){
             
             if(involvement == null){
                 throw new IllegalArgumentException("involvement is null");
             }
             if(involvement.getId()== null){
                 throw new IllegalArgumentException("involvement id is null");
             }
             st.setLong(1, involvement.getId());
             int count = st.executeUpdate();
             if(count == 0){
                 throw new ServiceFailureException("no involvement with specified id found");
             }
             if(count != 1){
                 throw new ServiceFailureException("delete involvement should delete only 1 mission ");
             }
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to delete involvement with id : "+involvement.getId(),ex);
        }
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
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT * FROM involvement WHERE id=?");
            ){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                return parseInvolvement(rs);
            }
            return null;
            
        }
           catch (SQLException ex) {
            throw new ServiceFailureException("failed to get involvement with id: "+id,ex);
        }
    }

    @Override
    public void updateInvolvement(Involvement involvement) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "UPDATE involvement SET agent=?, mission=?, \"start\"=?, \"end\"=? WHERE id=?");
            ){
             
            validate(involvement);
            st.setLong(1, involvement.getAgent().getId());
            st.setLong(2, involvement.getMission().getId());
            st.setTimestamp(3, Timestamp.valueOf(involvement.getStart()));
            st.setTimestamp(4, Timestamp.valueOf(involvement.getEnd()));
            st.setLong(5, involvement.getId());
            
            int rows = st.executeUpdate();
            if(rows == 0){
                throw new ServiceFailureException("involvement not found in the db");
            }
            if(rows != 1){
                throw new ServiceFailureException("update should change only 1 row");
            }
            
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to update involvement",ex);
        }
    }

    private Involvement parseInvolvement(ResultSet rs) throws SQLException {
        Involvement temp = new Involvement();
        MissionManager mm = new MissionManagerImpl(dataSource);
        AgentManager am = new AgentManagerImpl(dataSource);
        
        temp.setId(rs.getLong("id"));
        temp.setStart(rs.getTimestamp("start").toLocalDateTime());
        temp.setEnd(rs.getTimestamp("end").toLocalDateTime());
        temp.setMission(mm.getMissionById(rs.getLong("mission")));
        temp.setAgent(am.getAgentById(rs.getLong("agent")));
        
        return temp;
    }
    
}
