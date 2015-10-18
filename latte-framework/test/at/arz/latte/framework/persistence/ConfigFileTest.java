package at.arz.latte.framework.persistence;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import at.arz.latte.framework.restapi.MenuData;
import at.arz.latte.framework.restapi.SubMenuData;

/**
 * test class for creating a demo configuration file
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class ConfigFileTest {

	@Test
	public void createConfig() throws JAXBException {
		MenuData menu = new MenuData("Administration", "http://localhost:8080/latte/index.html");
		
		SubMenuData sub1 = new SubMenuData("Module", "http://localhost:8080/latte/module.html", "admin");
		SubMenuData sub2 = new SubMenuData("Benutzer", "http://localhost:8080/latte/user.html", "admin"); 
		SubMenuData sub3 = new SubMenuData("Rollen", "http://localhost:8080/latte/role.html", "admin"); 
		
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
