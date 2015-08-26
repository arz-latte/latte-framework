package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Entity implementation class for Entity: MenuRoot, represents a single top menu
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Entity
@Table(name = "menu")
public class MenuRoot extends AbstractEntity implements Serializable, Comparable<MenuRoot> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "Menu.ID")
	@TableGenerator(name = "Menu.ID", table = "latte_seq", pkColumnName = "KEY", valueColumnName = "VALUE", pkColumnValue="Menu.ID")
	private Long id;

	@Embedded
	private MenuEntry entry;

	@ElementCollection
	private List<MenuLeaf> submenu;

	/**
	 * JPA constructor
	 */
	public MenuRoot() {
		super();
		this.submenu = new ArrayList<>();
	}

	public MenuRoot(MenuEntry entry, List<MenuLeaf> submenu) {
		super();
		this.entry = entry;
		this.submenu = submenu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MenuEntry getEntry() {
		return entry;
	}

	public void setEntry(MenuEntry entry) {
		this.entry = entry;
	}

	public List<MenuLeaf> getSubmenu() {
		return submenu;
	}

	public void setSubmenu(List<MenuLeaf> submenu) {
		this.submenu = submenu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		result = prime * result + ((submenu == null) ? 0 : submenu.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuRoot other = (MenuRoot) obj;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (submenu == null) {
			if (other.submenu != null)
				return false;
		} else if (!submenu.equals(other.submenu))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MenuRoot [id=" + id + ", entry=" + entry + ", submenu=" + submenu + "]";
	}

	public int compareTo(MenuRoot menuRoot) {
		return entry.getPosition() - menuRoot.getEntry().getPosition();
	}

}
