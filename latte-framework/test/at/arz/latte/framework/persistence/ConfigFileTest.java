package at.arz.latte.framework.persistence;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import at.arz.latte.framework.restful.dta.MenuData;
import at.arz.latte.framework.restful.dta.SubMenuData;

public class ConfigFileTest {

	@Test
	public void createConfig() throws JAXBException {
		MenuData menu = new MenuData("Administration", "http://localhost:8080/latte/index.html", 20);
		
		SubMenuData sub1 = new SubMenuData("Module", "http://localhost:8080/latte/module.html", "admin"); 
		SubMenuData sub2 = new SubMenuData("Benutzer", "#", "admin"); 
		SubMenuData sub3 = new SubMenuData("Rollen", "#", "admin"); 
		
		menu.addSubMenu(sub1);
		menu.addSubMenu(sub2);
		menu.addSubMenu(sub3);

		File file = new File("administration-service-config.xml");

		JAXBContext jaxbContext = JAXBContext.newInstance(MenuData.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(menu, file);
	}
	
}