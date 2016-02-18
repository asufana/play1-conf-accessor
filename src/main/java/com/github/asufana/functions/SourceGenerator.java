package com.github.asufana.functions;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.*;
import java.util.stream.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;

import com.github.asufana.playconf.*;
import com.github.asufana.playconf.vo.*;
import com.squareup.javapoet.*;
import com.squareup.javapoet.TypeSpec.*;

/** アクセサクラスのソース生成 */
public class SourceGenerator extends AbstractAPFunction {
    
    //コンストラクタ
    public SourceGenerator(final ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }
    
    /** ソース生成 */
    public JavaFile generate(final PlayConf playConf,
                             final String pkgName,
                             final String className) {
        
        //ソース生成
        final Builder builder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
        //メソッド登録
        addMethods(builder, playConf);
        final TypeSpec source = builder.build();
        
        //クラス生成
        final JavaFile javaFile = JavaFile.builder(pkgName, source).build();
        return javaFile;
    }
    
    //メソッド生成
    private void addMethods(final Builder builder, final PlayConf playConf) {
        final List<String> keys = playConf.configKeys();
        keys.forEach(key -> {
            final MethodSpec method = generateMethods(playConf, key);
            builder.addMethod(method);
        });
    }
    
    //メソッド生成
    private MethodSpec generateMethods(final PlayConf playConf, final String key) {
        return MethodSpec.methodBuilder(key)
                         .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                         .returns(String.class)
                         .addCode(switchCaseString(playConf, key))
                         .build();
    }
    
    //SwitchCase文
    private String switchCaseString(final PlayConf playConf, final String configKey) {
        final List<FrameworkId> frameworkIds = playConf.frameworkIds();
        final String caseString = frameworkIds.stream()
                                              .filter(id -> id.equals(PlayConf.DEFAULT_FRAMEWORK_ID) == false)
                                              .map(id -> String.format("  case \"%s\": %s",
                                                                       id,
                                                                       caseValueString(playConf,
                                                                                       id,
                                                                                       configKey)))
                                              .collect(Collectors.joining());
        
        final String switchString = String.format("switch(play.Play.id){\n%s }\n", caseString)
                + defaultValueString(playConf, configKey);
        return switchString;
    }
    
    //SwitchCase値
    private String caseValueString(final PlayConf playConf,
                                   final FrameworkId id,
                                   final String configKey) {
        final String value = playConf.configValue(id, configKey);
        if (isEmpty(value)) {
            return errorMessage(configKey);
        }
        return String.format("return \"%s\";\n", value);
    }
    
    //Switchデフォルト値
    private String defaultValueString(final PlayConf playConf, final String configKey) {
        final String value = playConf.defaultValue(configKey);
        if (isEmpty(value)) {
            return errorMessage(configKey);
        }
        return String.format("return \"%s\";\n", value);
    }
    
    //エラーメッセージ
    private String errorMessage(final String configKey) {
        final String errorMessage = String.format("設定がありません：%s\" + play.Play.id + \".%s",
                                                  "%",
                                                  configKey.replaceAll("_", "\\."));
        return String.format("throw new java.lang.RuntimeException(\"%s\");\n", errorMessage);
    }
    
}
