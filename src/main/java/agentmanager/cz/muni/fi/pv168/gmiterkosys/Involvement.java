package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.time.LocalDateTime;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Involvement
{
	private long id;
	private Agent agent;
	private Mission mission;
	private LocalDateTime start;
	private LocalDateTime end;

	public Involvement(){
		super();
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Involvement other = (Involvement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Involvement{" + "id=" + id + ", agent=" + agent + ", mission=" + mission + ", start=" + start + ", end=" + end + '}';
    }
        
    

}

