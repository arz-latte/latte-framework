package at.arz.latte.framework.persistence;

import java.io.File;

import javax.persistence.EntityManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.modules.dta.ModuleData;
import at.arz.latte.framework.modules.models.Module;
import at.arz.latte.framework.modules.models.TestE;
import at.arz.latte.framework.persistence.JpaPersistenceSetup;

public class TestETest  {

	private EntityManager em;

	@Before
	public void setup() {
		em = JpaPersistenceSetup.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
	}
	
	@Test
	public void masdf() throws JAXBException {
		ModuleData m = new ModuleData();
		m.setName("asdf");
		m.setProvider("asdf");
		/*
		ServletConfig scfg= getServletConfig();
		ServletContext scxt = scfg.getServletContext();
		String webInfPath = sxct.getRealPath("WEB-INF");
		 File file = new File(webInfPath+ "/build.xml");*/
		
		File file = new File("administration-service-config.xml");
		 
		 JAXBContext jaxbContext = JAXBContext.newInstance(ModuleData.class);
		 Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		 jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);		 
		 jaxbMarshaller.marshal(m, file);
		 
		 Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		 //File file = new File("input.xml");
		 //Address address = (Address) unmarshaller.unmarshal(file);
	}

	@Test
	public void moduleIsPersistent() {
		em.getTransaction().begin();

		TestE t1 = new TestE("top1");
		
		TestE b1 = new TestE("bottom1");
		TestE b2 = new TestE("bottom2");
		
		TestE bb1 = new TestE("bottombottom1");
		TestE bb2 = new TestE("bottombottom2");
		
		b1.add(bb1);
		b1.add(bb2);
		
		
		t1.add(b1);
		t1.add(b2);
		
		em.persist(t1);

		em.getTransaction().commit();
	}

}
