package com.yourrights.repository.specifications;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.domain.Specification;

import com.yourrights.repository.beans.ProtestEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtestsSpecification implements Specification<ProtestEntity> {

    private static final long serialVersionUID = -881227770280314222L;

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<ProtestEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
	if (criteria == null)
	    return null;
	if (criteria.getOperation().equalsIgnoreCase(">")) {
	    return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
	} else if (criteria.getOperation().equalsIgnoreCase("<")) {
	    return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
	} else if (criteria.getOperation().equalsIgnoreCase(":")) {
	    if (root.get(criteria.getKey()).getJavaType() == String.class) {
		return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
	    } else {
		return builder.equal(root.get(criteria.getKey()), criteria.getValue());
	    }
	} else if (criteria.getOperation().equalsIgnoreCase("compare")) {
	    return builder.between(root.<Date>get(criteria.getKey()), criteria.getFrom(), criteria.getTo());
	}
	return null;
    }
}
