package cz.muni.fi.pv168.gmiterkosys;

import java.util.List;

/**
 * {@code AgentManager cz.muni.fi.pv168.gmiterkosys.AgentManager} is used to
 * store and load {@code Agents cz.muni.fi.pv168.gmiterkosys.Agent}.
 * 
 * @author Dominik Gmiterko
 */
public interface AgentManager {

	/**
	 * Save new agent
	 * 
	 * @param agent
	 *            Agent to create
	 */
	void createAgent(Agent agent);

	/**
	 * Load agent by id
	 * 
	 * @param id
	 *            Agent identifier
	 */
	Agent getAgentById(Long id);

	/**
	 * Load all saved agents
	 * 
	 * @return List of agents
	 */
	List<Agent> findAllAgents();

	/**
	 * Save changed agent
	 * 
	 * @param agent
	 *            Agent to update
	 */
	void updateAgent(Agent agent);

	/**
	 * Delete agent
	 * 
	 * @param agent
	 *            Agent to delete
	 */
	void removeAgent(Agent agent);

}
