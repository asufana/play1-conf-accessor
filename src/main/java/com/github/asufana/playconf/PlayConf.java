package com.github.asufana.playconf;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import com.github.asufana.playconf.vo.*;

/** Play1 applicatoin.conf 内容保持クラス */
public class PlayConf {
    
    public static final PlayConf EMPTY = new PlayConf(null);
    
    //conf設定値
    private final Map<FrameworkId, List<Config>> settings;
    
    //ファクトリ
    public static PlayConf factory(final List<String> confLines) {
        if (confLines.isEmpty()) {
            return EMPTY;
        }
        final Map<FrameworkId, List<Config>> settings = PlayConfFactory.create(confLines);
        return new PlayConf(settings);
    }
    
    //コンストラクタ
    PlayConf(final Map<FrameworkId, List<Config>> settings) {
        this.settings = settings;
    }
    
    public Stream<Entry<FrameworkId, List<Config>>> stream() {
        return settings.entrySet().stream();
    }
}
