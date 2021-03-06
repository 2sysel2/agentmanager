package cz.muni.fi.pv168.gmiterkosys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MissionManagerImpl implements MissionManager {
    
    private final Logger log = LoggerFactory.getLogger(InvolvementManagerImpl.class);
    
    private final DataSource dataSource;
    
    public MissionManagerImpl(DataSource src) {
        dataSource = src;
    }
    
    @Override
    public void createMission(Mission mission) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO mission (code,location,\"start\",\"end\",objective,outcome) VALUES (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            )
        {
            validate(mission);
            st.setString(1, mission.getCode());
            st.setString(2, mission.getLocation());
            st.setTimestamp(3, Timestamp.valueOf(mission.getStart()));
            st.setTimestamp(4, Timestamp.valueOf(mission.getEnd()));
            st.setString(5, mission.getObjective());
            st.setString(6, mission.getOutcome().toString());
            
            int count = st.executeUpdate();
            if (count != 1) {
                throw new ServiceFailureException("Internal Error: More rows ("
                        + count + ") inserted when trying to insert mission " + mission);
            }

            ResultSet keyRS = st.getGeneratedKeys();
            mission.setId(KeyGrabber.getKey(keyRS, mission));
            
            log.info("Mission created. {}", mission);
                    
        } catch (SQLException ex) {
            throw new ServiceFailureException("failed to insert new mission",ex);
        }
    }
    
    private void validate(Mission m){
        if(m == null){
            throw new IllegalArgumentException("mission is null");
        }
        if(m.getStart()== null){
            throw new IllegalArgumentException("mission start is null");
        }
        if(m.getEnd()== null){
            throw new IllegalArgumentException("mission end is null");
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

    @Override
    public void removeMission(Mission mission) {
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
             int count = st.executeUpdate();
             if(count == 0){
                 throw new ServiceFailureException("no mission with specified id found");
             }
             if(count != 1){
                 throw new ServiceFailureException("delete mission should delete only 1 mission ");
             }
             
             log.info("Mission removed. {}", mission);
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to delete mission",ex);
        }
    }

    @Override
    public List<Mission> findAllMissions() {
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
        temp.setLocation(rs.getString("location"));
        temp.setOutcome(Outcome.valueOf(rs.getString("outcome").toUpperCase()));
        return temp;
        
    }

    @Override
    public Mission getMissionByCode(String code) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT * FROM mission WHERE code=?");
            ){
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                return parseMission(rs);
            }
            return null;
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to find missions",ex);
        }
    }

    @Override
    public Mission getMissionById(long id)  {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "SELECT * FROM mission WHERE id=?");
            ){
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                return parseMission(rs);
            }
            return null;
             
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to find missions",ex);
        }
    }

    @Override
    public void updateMission(Mission mission) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "UPDATE mission SET code=?, location=?, \"start\"=?, \"end\"=?, objective=?, outcome =? WHERE id=?");
            ){
             
            validate(mission);
            st.setString(1, mission.getCode());
            st.setString(2, mission.getLocation());
            st.setTimestamp(3, Timestamp.valueOf(mission.getStart()));
            st.setTimestamp(4, Timestamp.valueOf(mission.getEnd()));
            st.setString(5, mission.getObjective());
            st.setString(6, mission.getOutcome().toString());
            st.setLong(7, mission.getId());
            
            int rows = st.executeUpdate();
            if(rows == 0){
                throw new ServiceFailureException("mission not found in the db");
            }
            if(rows != 1){
                throw new ServiceFailureException("update should change only 1 row");
            }
            
            log.info("Mission updated. {}", mission);
            
         } catch (SQLException ex) {
            throw new ServiceFailureException("failed to update mission",ex);
        }
    }
    
}
