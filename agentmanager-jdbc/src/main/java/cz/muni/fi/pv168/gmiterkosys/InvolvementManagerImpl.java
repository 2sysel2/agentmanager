package cz.muni.fi.pv168.gmiterkosys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class InvolvementManagerImpl implements InvolvementManager {

    private DataSource dataSource;
    
    private AgentManager agentManager;
    private MissionManager missionManager;
    
    public InvolvementManagerImpl(DataSource ds, AgentManager agentManager, MissionManager missionManager) {
        dataSource = ds;
        this.agentManager = agentManager;
        this.missionManager = missionManager;
    }

    @Override
    public void createInvolvement(Involvement involvement) {
    	
    	validate(involvement);
    	
    	if (involvement.getId() != null) {
			throw new IllegalArgumentException("Involvement id is already set!");
		}
    	
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO involvement (agent,mission,\"start\",\"end\") VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            ){
            
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
            involvement.setId(KeyGrabber.getKey(keyRS, involvement));
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
    public void removeInvolvement(Involvement involvement) {
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
    	try (Connection connection = dataSource.getConnection();
				PreparedStatement st = connection
						.prepareStatement("SELECT * FROM involvement")) {

			ResultSet rs = st.executeQuery();

			List<Involvement> result = new ArrayList<>();
			while (rs.next()) {
				result.add(parseInvolvement(rs));
			}
			return result;

		} catch (SQLException e) {
			throw new ServiceFailureException("Error when retrieving all agents", e);
		}
    }

    @Override
    public List<Involvement> findInvolvementByAgent(long agentId) {
    	Agent agent = agentManager.getAgentById(agentId);
    	if(agent != null) {
    		try (Connection connection = dataSource.getConnection();
    				PreparedStatement st = connection
    						.prepareStatement("SELECT * FROM involvement WHERE agent=?")) {

                st.setLong(1, agentId);
                
    			ResultSet rs = st.executeQuery();

    			List<Involvement> result = new ArrayList<>();
    			while (rs.next()) {
    				result.add(parseInvolvement(rs, agent));
    			}
    			return result;

    		} catch (SQLException e) {
    			throw new ServiceFailureException("Error when retrieving all agents", e);
    		}
    	} else {
    		throw new ServiceFailureException("Agent with this id doesnt exist.");
    	}
    }

    @Override
    public List<Involvement> findInvolvementByMission(long missionId) {
    
    	Mission mission = missionManager.getMissionById(missionId);
    	if(mission != null) {
    		try (Connection connection = dataSource.getConnection();
    				PreparedStatement st = connection
    						.prepareStatement("SELECT * FROM involvement WHERE mission=?")) {

                st.setLong(1, missionId);
                
    			ResultSet rs = st.executeQuery();

    			List<Involvement> result = new ArrayList<>();
    			while (rs.next()) {
    				result.add(parseInvolvement(rs, mission));
    			}
    			return result;

    		} catch (SQLException e) {
    			throw new ServiceFailureException("Error when retrieving all agents", e);
    		}
    	} else {
    		throw new ServiceFailureException("Mission with this id doesnt exist.");
    	}
    	
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
    	return parseInvolvement(rs, null, null);
    }
    
    private Involvement parseInvolvement(ResultSet rs, Mission mission) throws SQLException {
    	return parseInvolvement(rs, mission, null);
    }
    
    private Involvement parseInvolvement(ResultSet rs, Agent agent) throws SQLException {
    	return parseInvolvement(rs, null, agent);
    }
    
    private Involvement parseInvolvement(ResultSet rs, Mission mission, Agent agent) throws SQLException {
        Involvement involvement = new Involvement();
        
        involvement.setId(rs.getLong("id"));
        involvement.setStart(rs.getTimestamp("start").toLocalDateTime());
        involvement.setEnd(rs.getTimestamp("end").toLocalDateTime());
        
        if(mission != null && mission.getId() == rs.getLong("mission")) {
        	involvement.setMission(mission);
        } else {
        involvement.setMission(missionManager.getMissionById(rs.getLong("mission")));
        }
        
        if(agent != null && agent.getId() == rs.getLong("agent")) {
        	involvement.setAgent(agent);
        } else {
        involvement.setAgent(agentManager.getAgentById(rs.getLong("agent")));
        }
        
        return involvement;
    }
    
}
