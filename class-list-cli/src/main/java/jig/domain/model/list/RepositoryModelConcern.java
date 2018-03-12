package jig.domain.model.list;

import jig.domain.model.relation.RelationRepository;
import jig.domain.model.usage.ModelMethod;
import jig.domain.model.usage.ModelType;

import java.util.Arrays;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.joining;

public enum RepositoryModelConcern implements Converter {
    クラス名((modelType, m) ->
            modelType.name().value()),
    クラス和名((modelType, m) ->
            modelType.japaneseName().value()),
    メソッド名((t, method) ->
            method.name()),
    メソッド戻り値の型((t, method) ->
            method.returnType().getSimpleName()),
    メソッド引数型((t, method) ->
            Arrays.stream(method.parameters())
                    .map(Class::getSimpleName)
                    .collect(joining(",")));

    private final BiFunction<ModelType, ModelMethod, String> function;

    RepositoryModelConcern(BiFunction<ModelType, ModelMethod, String> function) {
        this.function = function;
    }

    public String convert(ModelType type, ModelMethod method, RelationRepository relationRepository) {
        return function.apply(type, method);
    }
}
