package org.dddjava.jig.domain.model.services;

import org.dddjava.jig.domain.model.characteristic.CharacterizedTypes;
import org.dddjava.jig.domain.model.declaration.method.MethodDeclaration;
import org.dddjava.jig.domain.model.declaration.method.MethodDeclarations;
import org.dddjava.jig.domain.model.implementation.bytecode.MethodUsingFields;
import org.dddjava.jig.domain.model.implementation.relation.MethodRelations;

import java.util.ArrayList;
import java.util.List;

public class ServiceAngles {

    List<ServiceAngle> list;

    public ServiceAngles(List<ServiceAngle> list) {
        this.list = list;
    }

    public List<ServiceAngle> list() {
        return list;
    }

    public static ServiceAngles of(MethodDeclarations serviceMethods,
                                   MethodRelations methodRelations,
                                   CharacterizedTypes characterizedTypes,
                                   MethodUsingFields methodUsingFields) {
        List<ServiceAngle> list = new ArrayList<>();
        for (MethodDeclaration serviceMethod : serviceMethods.list()) {
            list.add(ServiceAngle.of(serviceMethod, methodRelations, characterizedTypes, methodUsingFields));
        }
        return new ServiceAngles(list);
    }
}
