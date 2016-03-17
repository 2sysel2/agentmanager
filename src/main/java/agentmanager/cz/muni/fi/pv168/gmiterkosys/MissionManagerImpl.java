package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            //set generated id to object
            System.out.println("");
                    
        } catch (SQLException ex) {
            throw new ServiceFailureException("",ex);
        }
        
    }
            

    @Override
    public void deleteMission(Mission mission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Mission> findAllMissions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mission getMissionByCode(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mission getMissionById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateMission(Mission mission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
