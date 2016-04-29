package cz.muni.fi.pv168.gmiterkosys;

import java.util.List;

public interface MissionManager {

	void createMission(Mission mission);

	void removeMission(Mission mission);

	List<Mission> findAllMissions();

	Mission getMissionByCode(String code);

	Mission getMissionById(long id);

	void updateMission(Mission mission);

}
