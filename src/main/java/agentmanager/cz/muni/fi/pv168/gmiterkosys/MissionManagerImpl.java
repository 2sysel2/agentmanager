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
    public void createMission(Mission mission) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO GRAVE (col,row,capacity,note) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            )
        {
            
                    
        } catch (SQLException ex) {
            Logger.getLogger(MissionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
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
