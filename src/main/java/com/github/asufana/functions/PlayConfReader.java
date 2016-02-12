package com.github.asufana.functions;

import static java.util.stream.Collectors.*;

import java.io.*;
import java.util.*;

import javax.annotation.processing.*;
import javax.tools.*;

public class PlayConfReader extends AbstractAPFunction {
    
    //コンストラクタ
    public PlayConfReader(final ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }
    
    /** 設定ファイル取得・読み込み */
    public List<String> read(final String filePath) {
        final Optional<FileObject> file = getConf(filePath);
        final List<String> confLines = readConf(file);
        return confLines;
    }
    
    /** 設定ファイル取得 */
    Optional<FileObject> getConf(final String filePath) {
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
    List<String> readConf(final Optional<FileObject> file) {
        if (file.isPresent() == false) {
            return Collections.emptyList();
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.get()
                                                                              .openInputStream(),
                                                                          "UTF-8"))) {
            final List<String> confLines = br.lines().collect(toList());
            //設定ファイル保持クラス生成
            return confLines;
        }
        catch (final Exception e) {
            log(String.format("ファイル読み込みエラー。FilePath: %s, Error: %s",
                              file.get().getName(),
                              e.getMessage()));
        }
        return Collections.emptyList();
    }
}
