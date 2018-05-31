package org.dddjava.jig.presentation.view.graphvizj;

import org.dddjava.jig.domain.model.declaration.method.MethodDeclaration;
import org.dddjava.jig.domain.model.identifier.namespace.PackageIdentifier;
import org.dddjava.jig.domain.model.identifier.type.TypeIdentifier;

import java.util.StringJoiner;

/**
 * 関連
 */
public class RelationText {

    StringJoiner stringJoiner = new StringJoiner("\n");

    private void add(String from, String to) {
        // "hoge" -> "fuga";
        String line = '"' + from + '"' + " -> " + '"' + to + '"' + ';';
        stringJoiner.add(line);
    }

    public String asText() {
        return stringJoiner.toString();
    }

    public void add(PackageIdentifier from, PackageIdentifier to) {
        add(from.asText(), to.asText());
    }

    public void add(TypeIdentifier from, TypeIdentifier to) {
        add(from.fullQualifiedName(), to.fullQualifiedName());
    }

    public void add(MethodDeclaration from, MethodDeclaration to) {
        add(from.asFullText(), to.asFullText());
    }
}