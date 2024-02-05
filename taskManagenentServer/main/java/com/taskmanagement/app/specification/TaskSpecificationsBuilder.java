package com.taskmanagement.app.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import com.taskmanagement.app.model.Task;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Data
public class TaskSpecificationsBuilder {

  public List<SearchCriteria> params;

  public String search;

  public TaskSpecificationsBuilder(String search){
    this.search = search;
    this.params = new ArrayList<>();
  }

  public TaskSpecificationsBuilder with(String key, String operation, String value, String prefix, String suffix, Boolean orPredicate) {

    SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
    if (op != null) {
      boolean startWithAsterisk = prefix.contains("*");
      boolean endWithAsterisk = suffix.contains("*");
      if (op == SearchOperation.EQUALITY) {
        if (startWithAsterisk && endWithAsterisk) {
          op = SearchOperation.CONTAINS;
        } else if (endWithAsterisk) {
          op = SearchOperation.STARTS_WITH;
        } else if (startWithAsterisk) {
          op = SearchOperation.ENDS_WITH;
        }
      }
      SearchCriteria criteria = new SearchCriteria(key, op, value, orPredicate);
      if(startWithAsterisk || endWithAsterisk)
        criteria.escapeValue();
      params.add(criteria);
    }
    return this;
  }

public Specification<Task> build(){
  Boolean orPredicate = false;
  String[] strings = this.search.split("op:");
  if(strings.length > 1){
      if(strings[1].equals("or")){
          orPredicate = true;
      }
      this.search = strings[0];
  }
  Pattern pattern = Pattern.compile("(\\w+?)([=<~\\[|>\\]])(\\*?)(.+)(\\*?)([,])");
  Matcher matcher = pattern.matcher(search + ",");
  while (matcher.find()) {
      this.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5), orPredicate);
  }
  
  if (params.size() == 0) {
      return null;
  }

    Specification<Task> result = new TaskSpecification(params.get(0));

    for (int i = 1; i < params.size(); i++) {
        result = params.get(i).isOrPredicate()
              ? Specification.where(result).or(new TaskSpecification(params.get(i))) 
              : Specification.where(result).and(new TaskSpecification(params.get(i)));
    }
    return result;
    }
}
