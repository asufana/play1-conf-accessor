package com.github.asufana.functions;

import java.io.*;

import javax.annotation.processing.*;
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
        
        final String className = "PlayConf";
        final String pkgName = "generated";
        
        /** クラスソース生成 */
        final JavaFile javaFile = new SourceGenerator(processingEnv).generate(playConf,
                                                                              pkgName,
                                                                              className);
        /** クラスファイル生成 */
        writeClass(String.format("%s/%s", pkgName, className), javaFile);
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
            log(String.format("PlayConfAccessor: クラス書き出しエラー。Error: %s", e));
        }
    }
    
}
