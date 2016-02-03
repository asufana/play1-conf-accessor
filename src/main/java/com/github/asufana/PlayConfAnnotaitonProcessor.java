package com.github.asufana;

import java.io.*;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import javax.tools.*;

import com.squareup.javapoet.*;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PlayConfAnnotaitonProcessor extends AbstractProcessor {
    
    private void log(final String msg) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, msg);
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return true;
        }
        log("PlayConfAnnotationProcessor");
        
        /** クラス定義 */
        final TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                            .build();
        final JavaFile javaFile = JavaFile.builder("generated", helloWorld).build();
        
        /** クラスファイル生成 */
        writeClass("generated/HelloWorld", javaFile);
        
        return true;
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
}
