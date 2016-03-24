package cz.muni.fi.pv168.gmiterkosys;

import java.time.LocalDate;

/**
 * @author Dominik Gmiterko
 */
public class Agent {

	private Long id;
	private String name;
	private LocalDate born;
	private LocalDate died;
	private int level;

	public Agent() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the birth date
	 */
	public LocalDate getBorn() {
		return born;
	}

	/**
	 * @param born
	 *            the birth date to set
	 */
	public void setBorn(LocalDate born) {
		this.born = born;
	}

	/**
	 * @return the date of death
	 */
	public LocalDate getDied() {
		return died;
	}

	/**
	 * @param died
	 *            the date of death to set
	 */
	public void setDied(LocalDate died) {
		this.died = died;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((born == null) ? 0 : born.hashCode());
		result = prime * result + ((died == null) ? 0 : died.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + level;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agent other = (Agent) obj;
		if (born == null) {
			if (other.born != null)
				return false;
		} else if (!born.equals(other.born))
			return false;
		if (died == null) {
			if (other.died != null)
				return false;
		} else if (!died.equals(other.died))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Agent [id=" + id + ", name=" + name + ", born=" + born + ", died=" + died + ", level=" + level + "]";
	}

}
