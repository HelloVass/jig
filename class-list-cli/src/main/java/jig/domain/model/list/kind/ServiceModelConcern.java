package jig.domain.model.list.kind;

import jig.domain.model.list.ConverterCondition;
import jig.domain.model.thing.Name;

import java.util.function.Function;

import static java.util.stream.Collectors.joining;

public enum ServiceModelConcern implements Converter {
    クラス名(condition -> condition.className().value()),
    クラス和名(condition -> condition.japaneseName().value()),
    メソッド名(condition -> condition.methodName().shortValue()),
    メソッド戻り値の型(condition -> condition.returnTypeName().value()),
    メソッド引数型(condition -> condition.parameterTypeNames().stream().map(Name::value).collect(joining(","))),
    使用しているフィールドの型(condition -> condition.instructFields().stream().map(Name::value).collect(joining(",")));

    private final Function<ConverterCondition, String> function;

    ServiceModelConcern(Function<ConverterCondition, String> function) {
        this.function = function;
    }

    public String convert(ConverterCondition converterCondition) {
        return function.apply(converterCondition);
    }
}
