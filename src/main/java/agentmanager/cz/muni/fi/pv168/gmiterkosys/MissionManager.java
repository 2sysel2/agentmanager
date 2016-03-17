package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.util.List;



/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */
public  interface MissionManager 
{
	/**
         * @param mission Mission to be added to the database
	 */
	
	public void createMission(Mission mission);

	/**
         * @param mission Mission to be removed from the database
	 */
	
	public void deleteMission(Mission mission);

	/**
         * @return return List containing all mission retrivied from database
	 */
	
	public List<Mission> findAllMissions();

	/**
     * @param code         
	 */
	
	public Mission getMissionByCode(String code);

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Mission getMissionById(long id) ;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void updateMission(Mission mission) ;


}

