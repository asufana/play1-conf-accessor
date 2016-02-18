package com.github.asufana.playconf;

import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

import com.github.asufana.playconf.collections.*;
import com.github.asufana.playconf.vo.*;

/** Play1 applicatoin.conf 内容保持クラス */
public class PlayConf {
    
    public static final FrameworkId DEFAULT_FRAMEWORK_ID = new FrameworkId("default");
    
    public static final PlayConf EMPTY = new PlayConf(null);
    
    //conf設定値
    private final Map<FrameworkId, ConfigList> settings;
    
    //ファクトリ
    public static PlayConf factory(final List<String> confLines) {
        if (confLines.isEmpty()) {
            return EMPTY;
        }
        final Map<FrameworkId, ConfigList> settings = PlayConfFactory.create(confLines);
        return new PlayConf(settings);
    }
    
    //コンストラクタ
    PlayConf(final Map<FrameworkId, ConfigList> settings) {
        this.settings = settings;
    }
    
    //フレームワークIDリスト
    public List<FrameworkId> frameworkIds() {
        final Set<FrameworkId> keySet = this.settings.keySet();
        return new ArrayList<>(keySet);
    }
    
    //設定名リスト
    public List<String> configKeys() {
        final List<String> keys = this.stream()
                                      .flatMap(entry -> entry.getValue().stream())
                                      .map(config -> config.key)
                                      .distinct()
                                      .sorted()
                                      .collect(toList());
        return keys;
    }
    
    //設定値取得
    public String configValue(final FrameworkId frameworkId, final String key) {
        final ConfigList configs = this.settings.get(frameworkId);
        if (configs == null) {
            return null;
        }
        final String value = configs.get(key);
        return value != null
                ? value
                : this.settings.get(DEFAULT_FRAMEWORK_ID).get(key);
    }
    
    //デフォルト値取得
    public String defaultValue(final String key) {
        return this.configValue(DEFAULT_FRAMEWORK_ID, key);
    }
    
    Stream<Entry<FrameworkId, ConfigList>> stream() {
        return this.settings.entrySet().stream();
    }
    
}
