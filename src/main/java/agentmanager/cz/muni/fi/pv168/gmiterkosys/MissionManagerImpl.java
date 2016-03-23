package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;


public class MissionManagerImpl implements MissionManager {

    private final DataSource dataSource;
    
    public MissionManagerImpl(){
        dataSource = null;
    }
    
    public MissionManagerImpl(DataSource src) {
        dataSource = src;
    }
    
    @Override
    public void createMission(Mission mission) throws ServiceFailureException{
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO MISSION (CODE,LOCATION,START,END,OBJECTIVE,OUTCOME) VALUES (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            )
        {
            validate(mission);
            st.setString(1, mission.getCode());
            st.setString(2, mission.getLocation());
            st.setTimestamp(3, Timestamp.valueOf(mission.getStart()));
            st.setTimestamp(4, Timestamp.valueOf(mission.getEnd()));
            st.setString(5, mission.getObjective());
            st.setString(6, mission.getOutcome().toString());
            
            int addedRows = st.executeUpdate();
            if (addedRows != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + addedRows + ") inserted when trying to insert mission " + mission);
            }

            ResultSet keyRS = st.getGeneratedKeys();
            mission.setId(getKey(keyRS, mission));
                    
        } catch (SQLException ex) {
            throw new ServiceFailureException("failed to create nex mission",ex);
        }
    }
    
    private void validate(Mission m){
        if(m == null){
            throw new IllegalArgumentException("mission is null");
        }
        if(m.getStart().isAfter(m.getEnd())){
            throw new IllegalArgumentException("mission cannot end before it started");
        }
        if(m.getLocation() == null){
            throw new IllegalArgumentException("mission location is null");
        }
        if(m.getCode() == null){
            throw new IllegalArgumentException("mission code is null");
        }
        if(m.getObjective() == null){
            throw new IllegalArgumentException("mission objective is null");
        }
    }
         
    private Long getKey(ResultSet keyRS, Mission m) throws ServiceFailureException, SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert mission " + m
                        + " - wrong key fields count: " + keyRS.getMetaData().getColumnCount());
            }
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: Generated key"
                        + "retriving failed when trying to insert mission " + m
                        + " - more keys found");
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: Generated key"
                    + "retriving failed when trying to insert mission " + m
                    + " - no key found");
        }
    }

    @Override
    public void deleteMission(Mission mission)throws ServiceFailureException{
         try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "DELETE FROM mission WHERE id = ?");
            ){
             
             if(mission == null){
                 throw new IllegalArgumentException("mission is null");
             }
             if(mission.getId()== null){
                 throw new IllegalArgumentException("misison id is null");
             }
             
             st.setLong(1, mission.getId());
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to delete mission",ex);
        }
    }

    @Override
    public List<Mission> findAllMissions() throws ServiceFailureException{
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT * FROM mission");
            ){
            
            ResultSet rs = st.executeQuery();
            
            List<Mission> temp = new ArrayList<>();
            
            while(rs.next()){
                temp.add(parseMission(rs));
            }
            
            return temp;
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to find missions",ex);
        }
    }
    
    private Mission parseMission(ResultSet rs) throws SQLException{
        Mission temp = new Mission();
        temp.setId(rs.getLong("id"));
        temp.setCode(rs.getString("code"));
        temp.setEnd(rs.getTimestamp("end").toLocalDateTime());
        temp.setStart(rs.getTimestamp("start").toLocalDateTime());
        temp.setObjective(rs.getString("objective"));
        switch(rs.getString("outcome")){
            case "FAILED":temp.setOutcome(Outcome.FAILED);
            case "IN_PROGRES":temp.setOutcome(Outcome.IN_PROGRES);
            case "SUCCESSFUL":temp.setOutcome(Outcome.SUCCESSFUL);
        }
        return temp;
        
    }

    @Override
    public Mission getMissionByCode(String code) throws ServiceFailureException{
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT * FROM mission WHERE code=?");
            ){
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            
            rs.next();
            return parseMission(rs);
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to find missions",ex);
        }
    }

    @Override
    public Mission getMissionById(long id) throws ServiceFailureException {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT * FROM mission WHERE id=?");
            ){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            
            rs.next();
            return parseMission(rs);
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to find missions",ex);
        }
    }

    @Override
    public void updateMission(Mission mission) throws ServiceFailureException {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "UPDATE Mission SET code=?, location=?, start=?, end=?, objective=?, outcome =? WHERE id=?");
            ){
             
            validate(mission);
            st.setString(1, mission.getCode());
            st.setString(2, mission.getLocation());
            st.setTimestamp(3, Timestamp.valueOf(mission.getStart()));
            st.setTimestamp(4, Timestamp.valueOf(mission.getEnd()));
            st.setString(5, mission.getObjective());
            st.setString(6, mission.getOutcome().toString());
            
            int rows = st.executeUpdate();
            if(rows == 0){
                throw new ServiceFailureException("mission not found in the db");
            }
            if(rows != 1){
                throw new ServiceFailureException("update should change only 1 row");
            }
            
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to update mission",ex);
        }
    }
    
}
