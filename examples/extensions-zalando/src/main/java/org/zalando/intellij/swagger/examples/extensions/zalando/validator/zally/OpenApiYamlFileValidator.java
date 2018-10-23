package org.zalando.intellij.swagger.examples.extensions.zalando.validator.zally;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.zalando.intellij.swagger.file.FileDetector;
import org.zalando.intellij.swagger.traversal.path.PathFinder;

public class OpenApiYamlFileValidator extends ZallyYamlFileValidator {

  private final FileDetector fileDetector = new FileDetector();

  public OpenApiYamlFileValidator() {
    this(ServiceManager.getService(ZallyService.class), new PathFinder());
  }

  public OpenApiYamlFileValidator(ZallyService zallyService, PathFinder pathFinder) {
    super(zallyService, pathFinder);
  }

  @Override
  public ProblemDescriptor[] checkFile(
      @NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
    return checkViolations(file, manager, isOnTheFly);
  }

  public boolean supportsFile(final PsiFile file) {
    return fileDetector.isMainOpenApiYamlFile(file);
  }

  @Override
  Optional<PsiElement> getRootElement(final PsiFile file) {
    return pathFinder.findByPathFrom("$.openapi", file);
  }
}
