package org.dddjava.jig.domain.model.jigmodel.jigtype.member;

import org.dddjava.jig.domain.model.jigmodel.lowmodel.alias.MethodAlias;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.annotation.MethodAnnotations;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.method.DecisionNumber;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.method.MethodDeclaration;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.method.MethodDerivation;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.method.Visibility;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.type.TypeIdentifier;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.type.TypeIdentifiers;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.relation.method.MethodDepend;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.relation.method.UsingFields;
import org.dddjava.jig.domain.model.jigmodel.lowmodel.relation.method.UsingMethods;

import java.util.List;

/**
 * メソッド
 */
public class JigMethod {

    MethodDeclaration methodDeclaration;
    MethodAlias methodAlias;

    boolean nullDecision;

    DecisionNumber decisionNumber;
    MethodAnnotations methodAnnotations;
    Visibility visibility;

    MethodDepend methodDepend;
    MethodDerivation methodDerivation;

    public JigMethod(MethodDeclaration methodDeclaration, MethodAlias methodAlias, boolean nullDecision, DecisionNumber decisionNumber, MethodAnnotations methodAnnotations, Visibility visibility, MethodDepend methodDepend, MethodDerivation methodDerivation) {
        this.methodDeclaration = methodDeclaration;
        this.methodAlias = methodAlias;
        this.nullDecision = nullDecision;
        this.decisionNumber = decisionNumber;
        this.methodAnnotations = methodAnnotations;
        this.visibility = visibility;
        this.methodDepend = methodDepend;
        this.methodDerivation = methodDerivation;
    }

    public MethodDeclaration declaration() {
        return methodDeclaration;
    }

    public DecisionNumber decisionNumber() {
        return decisionNumber;
    }

    public MethodAnnotations methodAnnotations() {
        return methodAnnotations;
    }

    public Visibility visibility() {
        return visibility;
    }

    public boolean isPublic() {
        return visibility.isPublic();
    }

    public UsingFields usingFields() {
        return methodDepend.usingFields();
    }

    public UsingMethods usingMethods() {
        return methodDepend.usingMethods();
    }

    public MethodWorries methodWorries() {
        return new MethodWorries(this);
    }

    public boolean conditionalNull() {
        return nullDecision;
    }

    public boolean referenceNull() {
        return methodDepend.hasNullReference();
    }

    public boolean notUseMember() {
        return methodDepend.notUseMember();
    }

    public TypeIdentifiers usingTypes() {
        return methodDepend.usingTypes();
    }

    public String aliasTextOrBlank() {
        return methodAlias.asText();
    }

    public String aliasText() {
        return methodAlias
                .asTextOrDefault(declaration().declaringType().asSimpleText() + "\\n"
                        + declaration().methodSignature().methodName());
    }

    public String descriptionText() {
        return methodAlias.descriptionText();
    }

    public String labelTextWithSymbol() {
        String name = methodAlias.asTextOrDefault(declaration().methodSignature().methodName());
        return visibility.symbol() + ' ' + name;
    }

    public List<TypeIdentifier> listArguments() {
        return declaration().methodSignature().arguments();
    }

    public MethodDerivation derivation() {
        return methodDerivation;
    }

    public boolean objectMethod() {
        return declaration().methodSignature().isObjectMethod();
    }

    public boolean documented() {
        return !methodAlias.descriptionText().isEmpty();
    }

    public boolean remarkable() {
        return visibility == Visibility.PUBLIC
                || methodAlias.exists();
    }
}
