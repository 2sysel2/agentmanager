package agentmanager.cz.muni.fi.pv168.gmiterkosys;

import java.time.LocalDate;
import java.util.Objects;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Agent
{	
    private Long id;
    private String name;
    private LocalDate born;
    private LocalDate died;
    private int level;

    public Agent(){
            super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        this.born = born;
    }

    public LocalDate getDied() {
        return died;
    }

    public void setDied(LocalDate died) {
        this.died = died;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.born);
        hash = 67 * hash + Objects.hashCode(this.died);
        hash = 67 * hash + this.level;
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
        final Agent other = (Agent) obj;
        if (this.level != other.level) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.born, other.born)) {
            return false;
        }
        if (!Objects.equals(this.died, other.died)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agent{" + "id=" + id + ", name=" + name + ", born=" + born + ", died=" + died + ", level=" + level + '}';
    }
}
    
    

