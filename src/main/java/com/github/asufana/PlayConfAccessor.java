package com.github.asufana;

import java.io.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import javax.tools.*;

import com.github.asufana.playconf.*;
import com.squareup.javapoet.*;

/** アクセサクラス作成 */
public class PlayConfAccessor {
    
    private final ProcessingEnvironment processingEnv;
    
    //コンストラクタ
    public PlayConfAccessor(final ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
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
    
    /** ログ出力 */
    private void log(final String msg) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, msg);
    }
}
