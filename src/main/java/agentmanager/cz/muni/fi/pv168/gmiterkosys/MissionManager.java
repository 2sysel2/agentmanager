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
	
	public void createMission(Mission mission)throws ServiceFailureException;

	/**
         * @param mission Mission to be removed from the database
	 */
	
	public void deleteMission(Mission mission)throws ServiceFailureException;

	/**
         * @return return List containing all mission retrivied from database
	 */
	
	public List<Mission> findAllMissions()throws ServiceFailureException;

	/**
     * @param code         
	 */
	
	public Mission getMissionByCode(String code)throws ServiceFailureException;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public Mission getMissionById(long id) throws ServiceFailureException;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void updateMission(Mission mission)throws ServiceFailureException ;


}

