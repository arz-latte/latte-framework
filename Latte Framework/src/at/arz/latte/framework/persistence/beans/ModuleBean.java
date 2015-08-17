package at.arz.latte.framework.persistence.beans;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import at.arz.latte.framework.persistence.models.Module;

import java.util.List;

//@Stateful
@Stateless
public class ModuleBean {

    //@PersistenceContext(unitName = "LatteFrameworkPU", type = PersistenceContextType.EXTENDED)
	@PersistenceContext
    private EntityManager em;

    public void addModule(Module module) throws Exception {
        em.persist(module);
    }
}