package com.hidiscuss.backend.utils;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class JPAUtil {
    public static <T, K extends EntityPathBase<T>> JPAQuery<T> paging(Pageable pageable, JPAQuery<T> query, K entityPath) {
        query.offset(pageable.getOffset()).limit(pageable.getPageSize());
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(entityPath.getType(), entityPath.getMetadata());
            OrderSpecifier orderSpecifier = new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty()));
            query.orderBy(orderSpecifier);
        }
        return query;
    }
}
