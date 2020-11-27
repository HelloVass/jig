package org.dddjava.jig.domain.model.jigmodel.collections;

import org.dddjava.jig.domain.model.jigmodel.businessrules.BusinessRule;
import org.dddjava.jig.domain.model.jigmodel.businessrules.BusinessRules;

import java.util.ArrayList;
import java.util.List;

/**
 * コレクション一覧
 */
public class CollectionTypes {

    List<CollectionType> list;

    public CollectionTypes(BusinessRules businessRules) {
        list = new ArrayList<>();
        for (BusinessRule businessRule : businessRules.listCollection()) {
            list.add(new CollectionType(businessRule.jigType()));
        }
    }

    public List<CollectionType> list() {
        return list;
    }
}
