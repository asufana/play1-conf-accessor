package com.github.asufana;

import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;

import com.github.asufana.functions.*;
import com.github.asufana.playconf.*;

/** アノテーションプロセッサ・エントリポイント */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotaitonProcessor extends AbstractProcessor {
    
    private static final String PLAY_CONF_PATH = "../conf/application.conf";
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return true;
        }
        log("PlayConfAccessor - AnnotationProcessor");
        
        //設定ファイル読み込み
        final List<String> confLines = new PlayConfReader(processingEnv).read(PLAY_CONF_PATH);
        
        //設定保持クラス生成
        final PlayConf playConf = PlayConf.factory(confLines);
        
        //アクセサクラス生成
        new ClassGenerator(processingEnv).generate(playConf);
        
        return true;
    }
    
    /** ログ出力 */
    private void log(final String msg) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, msg);
    }
}
