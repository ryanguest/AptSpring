package com.salesforce.aptspring.processor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.common.io.Files;
import com.google.testing.compile.JavaFileObjects;

public class ComponentTests {

  private JavaFileObject definitionClass = JavaFileObjects.forSourceLines(
      "test.TestClass1",
      "package test;",
      "",
      "import org.springframework.beans.factory.annotation.Qualifier;",
      "import org.springframework.context.annotation.Bean;",
      "import org.springframework.context.annotation.ComponentScan;",
      "import org.springframework.context.annotation.Configuration;",
      "import org.springframework.context.annotation.Import;",
      "",
      "  @com.salesforce.aptspring.Verified",
      "  @Configuration",
      "  @Import(TestClass2.class)",
      "  public class TestClass1 {",
      "",
      "    @Bean(name = \"value1\")",
      "    public String value1(@Qualifier(\"value2\") String x) { return \"\";}",
      "",
      "    @Bean(name = \"value2\")",
      "    public String value2(@Qualifier(\"value3\") TestClass2 y) { return \"\";}",
      "",
      "}");
  
  private JavaFileObject componentClassNoDeps = JavaFileObjects.forSourceLines(
      "test.TestClass2",
      "package test;",
      "",
      "import org.springframework.beans.factory.annotation.Qualifier;",
      "import org.springframework.context.annotation.Bean;",
      "import org.springframework.stereotype.Component;",
      "",
      "  @com.salesforce.aptspring.Verified",
      "  @Component(\"value3\")",
      "  public class TestClass2 {",
      "",
      "    public TestClass2() { }",
      "",
      "}");
  
  @Test
  public void testComponentImport() throws IOException {
    File outputDir = Files.createTempDir();
    System.out.println("Generating into " + outputDir.getAbsolutePath());

    assertAbout(javaSources())
            .that(Arrays.asList(definitionClass, componentClassNoDeps))
            .processedWith(new VerifiedSpringConfiguration())
            .compilesWithoutError();  
  }
  
  
}
