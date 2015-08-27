package at.arz.latte.framework.modules.models;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import at.arz.latte.framework.modules.dta.ModuleData;
import at.arz.latte.framework.modules.models.validator.CheckUrl;

@Entity
@Table(name = "teste")
public class TestE implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TestE.ID")
	@TableGenerator(name = "TestE.ID", table = "latte_seq", pkColumnName = "KEY", pkColumnValue = "TestE.ID", valueColumnName = "VALUE")
	private Long id;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "teste_id")
	private List<TestE> list;

	public TestE() {
		super();
		this.list = new ArrayList<>();
	}

	public TestE(String name) {
		this();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TestE> getList() {
		return list;
	}

	public void setList(List<TestE> list) {
		this.list = list;
	}
	
	public void add(TestE e) {
		this.list.add(e);
	}

}
