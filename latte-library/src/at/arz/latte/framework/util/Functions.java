package at.arz.latte.framework.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

public class Functions {

	public static <T, R> List<R> map(	Function<T, R> function,
										TypedQuery<T> query) {
		return map(function, query.getResultList());
	}

	public static <T, R> List<R> map(Function<T, R> function, List<T> list) {
		return list.stream().map(function).collect(Collectors.toList());
	}

}
