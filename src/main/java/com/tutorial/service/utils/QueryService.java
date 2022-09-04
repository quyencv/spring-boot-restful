package com.tutorial.service.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.utils.EscapeUtils;

@Transactional
public abstract class QueryService<ENTITY, DTO> {

    public Specification<ENTITY> createSpec(DTO dto) {
        return Specification.where(null);
    };

    protected <X> Specification<ENTITY> equalsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            final X value) {
        return (root, query, builder) -> builder.equal(metaclassFunction.apply(root), value);
    }

    protected <X> Specification<ENTITY> notEqualsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            final X value) {
        return (root, query, builder) -> builder.not(builder.equal(metaclassFunction.apply(root), value));
    }

    protected Specification<ENTITY> likeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
            final String value) {
        return (root, query, builder) -> builder.like(builder.upper(metaclassFunction.apply(root)),
                wrapLikeQuery(value));
    }

    protected Specification<ENTITY> doesNotContainSpecification(
            Function<Root<ENTITY>, Expression<String>> metaclassFunction, final String value) {
        return (root, query, builder) -> builder
                .not(builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeQuery(value)));
    }

    protected <X> Specification<ENTITY> byFieldSpecified(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            final boolean specified) {
        return specified ? (root, query, builder) -> builder.isNotNull(metaclassFunction.apply(root))
                : (root, query, builder) -> builder.isNull(metaclassFunction.apply(root));
    }

    protected <X> Specification<ENTITY> byFieldEmptiness(Function<Root<ENTITY>, Expression<Set<X>>> metaclassFunction,
            final boolean specified) {
        return specified ? (root, query, builder) -> builder.isNotEmpty(metaclassFunction.apply(root))
                : (root, query, builder) -> builder.isEmpty(metaclassFunction.apply(root));
    }

    protected <X> Specification<ENTITY> valueIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            Collection<X> values) {
        List<List<X>> partitionList = ListUtils.partition(new ArrayList<>(values), 999);
        Specification<ENTITY> spec = Specification.where(null);
        for (List<X> vals : partitionList) {
            Specification<ENTITY> specInner = (root, query, builder) -> {
                In<X> in = builder.in(metaclassFunction.apply(root));
                for (X val : vals) {
                    in = in.value(val);
                }
                return in;
            };
            spec = spec.or(specInner);
        }
        return spec;
    }

    protected <X> Specification<ENTITY> valueIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            Collection<X> values, X defaulVal) {
        List<X> newList = new ArrayList<>(values);
        if (CollectionUtils.isEmpty(newList)) {
            newList.add(defaulVal);
        }
        return valueIn(metaclassFunction, newList);
    }

    protected <X> Specification<ENTITY> valueNotIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            Collection<X> values) {
        List<List<X>> partitionList = ListUtils.partition(new ArrayList<>(values), 999);
        Specification<ENTITY> spec = Specification.where(null);

        for (List<X> vals : partitionList) {
            Specification<ENTITY> specInner = (root, query, builder) -> {
                In<X> in = builder.in(metaclassFunction.apply(root));
                for (X val : vals) {
                    in = in.value(val);
                }
                return builder.not(in);
            };
            spec = spec.and(specInner);
        }
        return spec;
    }

    protected <X> Specification<ENTITY> valueNotIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
            Collection<X> values, X defaulVal) {

        List<X> newList = new ArrayList<>(values);
        if (CollectionUtils.isEmpty(newList)) {
            newList.add(defaulVal);
        }
        return valueNotIn(metaclassFunction, newList);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(
            Function<Root<ENTITY>, Expression<X>> metaclassFunction, final X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(
            Function<Root<ENTITY>, Expression<X>> metaclassFunction, final X value) {
        return (root, query, builder) -> builder.greaterThan(metaclassFunction.apply(root), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(
            Function<Root<ENTITY>, Expression<X>> metaclassFunction, final X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(
            Function<Root<ENTITY>, Expression<X>> metaclassFunction, final X value) {
        return (root, query, builder) -> builder.lessThan(metaclassFunction.apply(root), value);
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + EscapeUtils.escapeWildcardsForMySQL(txt.toUpperCase()) + '%';
    }

}