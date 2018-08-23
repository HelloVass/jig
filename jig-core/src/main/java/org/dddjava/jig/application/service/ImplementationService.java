package org.dddjava.jig.application.service;

import org.dddjava.jig.annotation.incubation.Progress;
import org.dddjava.jig.domain.basic.ClassFindFailException;
import org.dddjava.jig.domain.model.characteristic.CharacterizedTypeFactory;
import org.dddjava.jig.domain.model.implementation.ProjectData;
import org.dddjava.jig.domain.model.implementation.bytecode.ByteCodeFactory;
import org.dddjava.jig.domain.model.implementation.bytecode.ByteCodeSources;
import org.dddjava.jig.domain.model.implementation.bytecode.TypeByteCodes;
import org.dddjava.jig.domain.model.implementation.datasource.SqlReader;
import org.dddjava.jig.domain.model.implementation.datasource.SqlSources;
import org.dddjava.jig.domain.model.implementation.datasource.Sqls;
import org.dddjava.jig.infrastructure.LocalProject;
import org.springframework.stereotype.Service;

/**
 * 取り込みサービス
 */
@Progress("安定")
@Service
public class ImplementationService {

    final GlossaryService glossaryService;

    final ByteCodeFactory byteCodeFactory;
    final SqlReader sqlReader;
    final CharacterizedTypeFactory characterizedTypeFactory;

    public ImplementationService(ByteCodeFactory byteCodeFactory, GlossaryService glossaryService, SqlReader sqlReader, CharacterizedTypeFactory characterizedTypeFactory) {
        this.byteCodeFactory = byteCodeFactory;
        this.glossaryService = glossaryService;
        this.sqlReader = sqlReader;
        this.characterizedTypeFactory = characterizedTypeFactory;
    }

    /**
     * プロジェクト情報を読み取る
     */
    public ProjectData readProjectData(LocalProject target) {
        TypeByteCodes typeByteCodes = readByteCode(target.getByteCodeSources());
        Sqls sqls = readSql(target.getSqlSources());

        ProjectData projectData = new ProjectData(typeByteCodes, sqls, characterizedTypeFactory);

        glossaryService.importJapanese(target.getTypeNameSources());
        glossaryService.importJapanese(target.getPackageNameSources());

        return projectData;
    }

    /**
     * ソースからバイトコードを読み取る
     */
    public TypeByteCodes readByteCode(ByteCodeSources byteCodeSources) {
        if (byteCodeSources.notFound()) {
            throw new ClassFindFailException();
        }

        return byteCodeFactory.readFrom(byteCodeSources);
    }

    /**
     * ソースからSQLを読み取る
     */
    public Sqls readSql(SqlSources sqlSources) {
        return sqlReader.readFrom(sqlSources);
    }
}
