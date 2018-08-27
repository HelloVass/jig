package org.dddjava.jig.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultLayout implements Layout {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLayout.class);

    private final Path projectPath;
    Path classesDirectory;
    Path resourcesDirectory;
    Path sourcesDirectory;

    public DefaultLayout(String projectPath, String classesDirectory, String resourcesDirectory, String sourcesDirectory) {
        LOGGER.info("Project Path: {}", projectPath);
        LOGGER.info("classes suffix  : {}", classesDirectory);
        LOGGER.info("resources suffix: {}", resourcesDirectory);
        LOGGER.info("sources suffix  : {}", sourcesDirectory);

        this.projectPath = Paths.get(projectPath);
        this.classesDirectory = Paths.get(classesDirectory);
        this.resourcesDirectory = Paths.get(resourcesDirectory);
        this.sourcesDirectory = Paths.get(sourcesDirectory);
    }

    @Override
    public Path[] extractClassPath() {
        DefaultDirectoryVisitor visitor = new DefaultDirectoryVisitor(
                path -> path.endsWith(classesDirectory) || path.endsWith(resourcesDirectory)
        );
        try {
            Files.walkFileTree(projectPath, visitor);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return visitor.asArray();
    }

    @Override
    public Path[] extractSourcePath() {
        DefaultDirectoryVisitor visitor = new DefaultDirectoryVisitor(
                path -> path.endsWith(sourcesDirectory)
        );

        try {
            Files.walkFileTree(projectPath, visitor);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return visitor.asArray();
    }
}
