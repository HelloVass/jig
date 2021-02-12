package org.dddjava.jig.presentation.view.graphviz.dot;

import org.dddjava.jig.domain.model.jigdocument.documentformat.JigDiagramFormat;
import org.dddjava.jig.domain.model.jigdocument.stationery.DiagramSource;
import org.dddjava.jig.presentation.view.graphviz.process.ProcessExecutor;
import org.dddjava.jig.presentation.view.graphviz.process.ProcessResult;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

public class DotCommandRunner {
    Logger logger = Logger.getLogger(DotCommandRunner.class.getName());

    ProcessExecutor processExecutor = new ProcessExecutor();
    Path workDirectory;

    public DotCommandRunner() {
        try {
            workDirectory = Files.createTempDirectory("jig");
            workDirectory.toFile().deleteOnExit();
        } catch (IOException e) {
            throw new UncheckedIOException("テンポラリディレクトリの作成に失敗しました。", e);
        }
    }

    public ProcessResult run(JigDiagramFormat documentFormat, Path inputPath, Path outputPath) {
        try {
            if (documentFormat == JigDiagramFormat.DOT) {
                Files.move(inputPath, outputPath, StandardCopyOption.REPLACE_EXISTING);
                logger.fine("dot text file: " + outputPath);
                Files.deleteIfExists(inputPath);
                return ProcessResult.success();
            }

            ProcessResult result = processExecutor.execute(dotCommand(processExecutor),
                    documentFormat.dotOption(),
                    "-o" + outputPath,
                    inputPath.toString());

            if (result.succeed()) {
                logger.fine("diagram path: " + outputPath.toAbsolutePath());
                Files.deleteIfExists(inputPath);
            } else {
                logger.warning("Cannot run dot. write DOT file.");
                Files.copy(inputPath, outputPath.getParent().resolve(inputPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }

            return result;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ProcessResult runVersion() {
        ProcessResult result = processExecutor.execute(dotCommand(processExecutor), "-V");
        if (result.failed()) {
            return result.withMessage("Graphvizのバージョン取得に失敗しました。Graphvizがインストールできていない可能性があります。");
        }
        return result;
    }

    private String dotCommand(ProcessExecutor processExecutor) {
        return processExecutor.isWin() ? "dot.exe" : "dot";
    }

    public void verify() {
        ProcessResult processResult = runVersion();
        if (processResult.failed()) {
            throw new IllegalStateException(processResult.message());
        }
    }

    public Path writeSource(DiagramSource element) {
        try {
            String fileName = element.documentName().withExtension(JigDiagramFormat.DOT);
            Path sourcePath = workDirectory.resolve(fileName);
            Files.write(sourcePath, element.text().getBytes(StandardCharsets.UTF_8));
            logger.fine("temporary DOT file: " + sourcePath.toAbsolutePath());
            return sourcePath;
        } catch (IOException e) {
            throw new UncheckedIOException("テンポラリファイルの出力に失敗しました。", e);
        }
    }
}
