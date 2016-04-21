package cz.muni.fi.pv168.gmiterkosys;

import java.util.List;

public interface InvolvementManager {

	void createInvolvement(Involvement involvement);

	void deleteInvolvement(Involvement involvement);

	List<Involvement> findAllInvolvements();

	List<Involvement> findInvolvementByAgent(long agentId);

	List<Involvement> findInvolvementByMission(long missionId);

	Involvement getInvolvementById(long id);

	void updateInvolvement(Involvement involvement);

}
