package cz.muni.fi.pv168.gmiterkosys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jaromir Sys
 */
public class KeyGrabber {
    public static Long getKey(ResultSet keyRS, Object object) throws SQLException {
        if (keyRS.next()) {
            if (keyRS.getMetaData().getColumnCount() != 1) {
                throw new ServiceFailureException("Internal Error: wrong key fields count (" + keyRS.getMetaData().getColumnCount()+") for "+object);
            }
            
            Long result = keyRS.getLong(1);
            if (keyRS.next()) {
                throw new ServiceFailureException("Internal Error: more keys found for "+object);
            }
            return result;
        } else {
            throw new ServiceFailureException("Internal Error: no key found for "+object);
        }
    }
}
