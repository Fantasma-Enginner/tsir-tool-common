package org.tsir.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.HttpHeaders;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

public class PagingUtils {

	private PagingUtils() {
	}

	static final List<String> headersPaging = Arrays.asList("X-Paging-Pages", "X-Paging-Elements");
	public static final String ACCESS_CONTROL_EXPOSE_HEADER_NAME = "Access-Control-Expose-Headers";

	public static HttpHeaders buildPaginationHeaders(int pages, long elements) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(headersPaging.get(0), String.valueOf(pages));
		headers.add(headersPaging.get(1), String.valueOf(elements));
		headers.addAll(ACCESS_CONTROL_EXPOSE_HEADER_NAME, headersPaging);
		return headers;
	}

	public static PageRequest getPageable(Map<String, Integer> properties, Sort sort) {
		int page = properties.getOrDefault(PagingKey.INDEX.toString(), 1);
		int size = properties.getOrDefault(PagingKey.SIZE.toString(), 5);
		if (page < 1) {
			page = 1;
		}
		return PageRequest.of(page - 1, size, sort);
	}

	public static PageRequest getPageableString(Map<String, String> properties, Sort sort) {
		int page = Integer.parseInt(properties.getOrDefault(PagingKey.INDEX.toString(), "1"));
		int size = Integer.parseInt(properties.getOrDefault(PagingKey.SIZE.toString(), "5"));
		if (page < 1) {
			page = 1;
		}
		return PageRequest.of(page - 1, size, sort);
	}

	public static Sort getSortEspecification(Map<String, String> properties, ComparableExpressionBase<?> expression) {
		SortingValue order = SortingValue
				.valueOf(properties.getOrDefault(SortingKey.ORDER.toString(), SortingValue.ASC.toString()));
		OrderSpecifier<?> orderSpecifier = SortingValue.DESC.equals(order) ? expression.desc() : expression.asc();
		return new QSort(orderSpecifier);
	}

}
