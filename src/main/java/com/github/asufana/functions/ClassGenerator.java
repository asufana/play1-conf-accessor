package com.github.asufana.functions;

import java.io.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;

import com.github.asufana.playconf.*;
import com.squareup.javapoet.*;

/** アクセサクラス作成 */
public class ClassGenerator extends AbstractAPFunction {
    
    //コンストラクタ
    public ClassGenerator(final ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }
    
    /** アクセサクラス生成 */
    public void generate(final PlayConf playConf) {
        
        /** クラス定義 */
        final TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                            .build();
        final JavaFile javaFile = JavaFile.builder("generated", helloWorld).build();
        
        /** クラスファイル生成 */
        writeClass("generated/HelloWorld", javaFile);
    }
    
    /** クラスファイル書き出し */
    private void writeClass(final String fqcn, final JavaFile javaFile) {
        try {
            final JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(fqcn);
            try (final Writer writer = sourceFile.openWriter()) {
                javaFile.writeTo(writer);
            }
        }
        catch (final Exception e) {
            log(String.format("クラス書き出しエラー。Error: %s", e.getMessage()));
        }
    }
    
}
