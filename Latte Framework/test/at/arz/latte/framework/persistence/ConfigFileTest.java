package at.arz.latte.framework.persistence;

import java.io.File;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.arz.latte.framework.modules.dta.MenuData;
import at.arz.latte.framework.modules.dta.ModuleData;
import at.arz.latte.framework.modules.dta.SubMenuData;
import at.arz.latte.framework.modules.dta.SubMenusData;
import at.arz.latte.framework.modules.models.TestE;

public class ConfigFileTest {

	@Test
	public void createConfig() throws JAXBException {
		MenuData menu = new MenuData("Administration", "http://localhost:8080/latte/index.html", 20);
		
		SubMenuData sub1 = new SubMenuData("Module", "http://localhost:8080/latte/module.html", "admin"); 
		SubMenuData sub2 = new SubMenuData("Benutzer", "#", "admin"); 
		SubMenuData sub3 = new SubMenuData("Rollen", "#", "admin"); 
		SubMenusData sub = new SubMenusData();
		
		sub.addSubMenu(sub1);
		sub.addSubMenu(sub2);
		sub.addSubMenu(sub3);
		
		menu.setSubmenus(sub);

		File file = new File("administration-service-config.xml");

		JAXBContext jaxbContext = JAXBContext.newInstance(MenuData.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(menu, file);
	}
/*
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
*/
}
