package org.dddjava.jig.domain.model.jigmodel.lowmodel.declaration.method;

/**
 * メソッド数
 */
public class MethodNumber {
    int value;

    public MethodNumber(int value) {
        this.value = value;
    }

    public String asText() {
        return Integer.toString(value);
    }

    public int intValue() {
        return value;
    }
}
