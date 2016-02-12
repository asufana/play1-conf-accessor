package com.github.asufana.functions;

import javax.annotation.processing.*;
import javax.tools.Diagnostic.Kind;

public abstract class AbstractAPFunction {
    
    protected final ProcessingEnvironment processingEnv;
    
    //コンストラクタ
    AbstractAPFunction(final ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }
    
    /** ログ出力 */
    protected void log(final String msg) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, msg);
    }
}
