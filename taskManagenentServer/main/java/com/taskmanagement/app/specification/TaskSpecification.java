package com.taskmanagement.app.specification;

import org.springframework.data.jpa.domain.Specification;
import com.taskmanagement.app.model.Task;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Expression;
import java.util.Arrays;

public class TaskSpecification implements Specification<Task> {

    private SearchCriteria criteria;
    
    public TaskSpecification(final SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Object value = criteria.getValue();
        switch (criteria.getOperation()) {
            case EQUALITY:
                return "null".equals(value.toString()) ? builder.isNull(getPath(criteria, root))
                        : builder.equal(getPath(criteria, root), value);
            case NEGATION:
                return "null".equals(value.toString()) ? builder.isNotNull(getPath(criteria, root))
                        : builder.notEqual(getPath(criteria, root), value);
            case GREATER_THAN:
                return builder.greaterThan(getPath(criteria, root), value.toString());
            case LESS_THAN:
                return builder.lessThan(getPath(criteria, root), value.toString());
            case LIKE:
                return builder.like(getPath(criteria, root), value.toString());
            case STARTS_WITH:
                return builder.like(getPath(criteria, root), value + "%");
            case ENDS_WITH:
                return builder.like(getPath(criteria, root), "%" + value);
            case CONTAINS:
                return builder.like(builder.lower(getPath(criteria, root)),
                        "%" + value.toString().toLowerCase() + "%");
            case IN:
                return getPath(criteria, root).in(Arrays.asList(value.toString().split("#")));
            case NOT_IN:
                return getPath(criteria, root).in(Arrays.asList(value.toString().split("#"))).not();
            default:
                return null;
        }
    }
    
    protected Expression<String> getPath(SearchCriteria criteria, Root<Task> root) {
        return root.get(criteria.getKey());
    }

}