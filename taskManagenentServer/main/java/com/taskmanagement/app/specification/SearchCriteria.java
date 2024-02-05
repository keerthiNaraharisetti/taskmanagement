package com.taskmanagement.app.specification;

import lombok.Data;
import org.springframework.data.jpa.repository.query.EscapeCharacter;

@Data
public class SearchCriteria {
    private String key;
    private SearchOperation operation;
    private Object value;
    private boolean orPredicate;

    public SearchCriteria(String key, SearchOperation operation, Object value, Boolean orPredicate) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.orPredicate = orPredicate;
    }
    public void escapeValue() {
        this.value = EscapeCharacter.DEFAULT.escape(String.valueOf(this.value));
    }
}