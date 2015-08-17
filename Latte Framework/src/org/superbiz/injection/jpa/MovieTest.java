package org.superbiz.injection.jpa;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.Test;

public class MovieTest {

	@Test
	public void test() throws Exception {

		final Properties p = new Properties();
		p.put("LatteFrameworkDS", "new://Resource?type=DataSource");
		p.put("LatteFrameworkDS.JdbcDriver", "com.mysql.jdbc.Driver");
		p.put("LatteFrameworkDS.JdbcUrl", "jdbc:mysql://localhost:3306/latte");
		p.put("LatteFrameworkDS.userName", "latte");
		p.put("LatteFrameworkDS.password", "latte");

		final Context context = EJBContainer.createEJBContainer(p).getContext();

		Movies movies = (Movies) context
				.lookup("java:global/Latte Framework/Movies");

		movies.addMovie(new Movie("Quentin Tarantino", "Reservoir Dogs", 1992));
		movies.addMovie(new Movie("Joel Coen", "Fargo", 1996));
		movies.addMovie(new Movie("Joel Coen", "The Big Lebowski", 1998));

		List<Movie> list = movies.getMovies();
		assertEquals("List.size()", 3, list.size());

		for (Movie movie : list) {
			movies.deleteMovie(movie);
		}

		assertEquals("Movies.getMovies()", 0, movies.getMovies().size());

	}

}
