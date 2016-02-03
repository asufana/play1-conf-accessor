package com.github.asufana;

import java.io.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import javax.tools.*;

import com.squareup.javapoet.*;

public class PlayConfAccessorGenerator {
    
    private final ProcessingEnvironment processingEnv;
    
    public PlayConfAccessorGenerator(final ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }
    
    public void generate() {
        log("PlayConfAccessor - AnnotationProcessor");
        
        /** クラス定義 */
        final TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                            .build();
        final JavaFile javaFile = JavaFile.builder("generated", helloWorld).build();
        
        /** クラスファイル生成 */
        writeClass("generated/HelloWorld", javaFile);
    }
    
    private void writeClass(final String fqcn, final JavaFile javaFile) {
        try {
            final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(fqcn);
            try (final Writer writer = sourceFile.openWriter()) {
                javaFile.writeTo(writer);
            }
        }
        catch (final Exception e) {
            log(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private void log(final String msg) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, msg);
    }
}
