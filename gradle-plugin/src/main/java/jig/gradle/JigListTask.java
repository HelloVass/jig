package jig.gradle;

import jig.application.usecase.ReportService;
import jig.domain.model.report.Reports;
import jig.infrastructure.LocalProject;
import jig.infrastructure.poi.writer.ExcelWriter;
import org.gradle.api.DefaultTask;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JigListTask extends DefaultTask {

    ServiceFactory serviceFactory = new ServiceFactory();

    @TaskAction
    public void apply() {
        ExtensionContainer extensions = getProject().getExtensions();
        JigListExtension extension = extensions.findByType(JigListExtension.class);

        LocalProject localProject = serviceFactory.localProject(getProject());
        serviceFactory.importService(getProject()).importSources(
                localProject.getSpecificationSources(),
                localProject.getSqlSources(),
                localProject.getTypeNameSources(),
                localProject.getPackageNameSources());

        String outputPath = extension.getOutputPath();
        ensureExists(outputPath);

        ReportService reportService = serviceFactory.reportService(outputPath);
        Reports reports = reportService.reports();

        new ExcelWriter().writeTo(reports, Paths.get(outputPath));
    }

    private void ensureExists(String outputPath) {
        Path outputDirPath = Paths.get(outputPath);
        try {
            Files.createDirectories(outputDirPath.getParent());
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
