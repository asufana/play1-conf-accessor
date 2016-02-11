package com.github.asufana;

import static java.util.stream.Collectors.*;

import java.io.*;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import javax.tools.*;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotaitonProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return true;
        }
        log("PlayConfAccessor - AnnotationProcessor");
        
        //設定ファイル読み込み
        final List<String> lines = readConf(getConf("../conf/application.conf"));
        if (lines.isEmpty()) {
            return true;
        }
        
        //設定ファイルアクセサ生成
        new PlayConfAccessor(processingEnv).generate(lines);
        
        return true;
    }
    
    /** 設定ファイル取得 */
    private Optional<FileObject> getConf(final String filePath) {
        final Filer filer = processingEnv.getFiler();
        try {
            final FileObject file = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", filePath);
            return Optional.of(file);
        }
        catch (final IOException e) {
            log(String.format("ファイルが見つかりません。FilePath: %s, Error: %s", filePath, e.getMessage()));
        }
        return Optional.empty();
    }
    
    /** 設定ファイル読み込み */
    private List<String> readConf(final Optional<FileObject> file) {
        if (file.isPresent() == false) {
            return Collections.emptyList();
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.get()
                                                                              .openInputStream(),
                                                                          "UTF-8"))) {
            return br.lines().collect(toList());
        }
        catch (final Exception e) {
            log(String.format("ファイル読み込みエラー。FilePath: %s, Error: %s",
                              file.get().getName(),
                              e.getMessage()));
        }
        return Collections.emptyList();
    }
    
    /** ログ出力 */
    private void log(final String msg) {
        final Messager messager = processingEnv.getMessager();
        messager.printMessage(Kind.NOTE, msg);
    }
}
