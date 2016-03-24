package cz.muni.fi.pv168.gmiterkosys;

import java.util.List;

/**
 * @author Dominik Gmiterko
 */
public interface AgentManager {

	void createAgent(Agent agent);

	Agent getAgentById(Long id);

	List<Agent> findAllAgents();

	void updateAgent(Agent agent);

	void deleteAgent(Agent agent);

}
