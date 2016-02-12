package com.github.asufana.playconf;

import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import com.github.asufana.playconf.vo.*;

public class PlayConfFactory {
    
    static Map<FrameworkId, List<Config>> create(final List<String> confLines) {
        //不要行除去
        final List<String> filtered = filterIgnoreLine(confLines);
        //PlayFrameworkIdでグルーピング
        final Map<FrameworkId, List<Config>> frameworkIdMap = groupByFrameworkId(filtered);
        return frameworkIdMap;
    }
    
    //PlayFrameworkIdでグルーピング
    static Map<FrameworkId, List<Config>> groupByFrameworkId(final List<String> filtered) {
        final Map<FrameworkId, List<Config>> map = filtered.stream()
                                                           //フレームワークIDを抽出してKeyとする
                                                           .collect(groupingBy(frameworkIdParseFunction,
                                                                               //フレームワークIDを除去した上でKey,Valueのtuple化する
                                                                               Collectors.mapping(frameworkIdRemoveFunction,
                                                                                                  toList())));
        
        return map;
    }
    
    //文頭のフレームワークIDを抽出（%prodなど）
    private static Function<String, FrameworkId> frameworkIdParseFunction = line -> {
        final List<String> columns = Arrays.asList(line.split("\\."));
        //フレームワークIDが付加されていればそれを返却
        if (columns.size() != 0 && columns.get(0).startsWith("%")) {
            return new FrameworkId(columns.get(0));
        }
        //それ以外はデフォルト値を設定
        return new FrameworkId("%default");
    };
    
    //文頭のフレームワークIDを除去
    private static Function<String, Config> frameworkIdRemoveFunction = line -> {
        //フレームワークIDが付加されていれば除去して返却
        if (line.startsWith("%")) {
            final String replaced = line.replaceAll("^%[^\\.]*\\.", "");
            return toTuple(replaced);
        }
        //それ以外はそのまま返却
        return toTuple(line);
    };
    
    //イコールをデリミタとしてKeyValue化する
    private static Config toTuple(final String line) {
        if (line.indexOf("=") == -1) {
            return new Config(line, null);
        }
        final String[] columns = line.split("=");
        return new Config(columns[0], columns[1]);
    }
    
    //不要行除去
    static List<String> filterIgnoreLine(final List<String> confLines) {
        final List<String> filtered = confLines.stream().map(line -> line.replaceAll("^ +", "")) //文頭スペース除去
                                               .filter(line -> line.startsWith("#") == false)
                                               //コメント除去
                                               .filter(line -> isNotEmpty(line))
                                               //空行除去
                                               .collect(toList());
        return filtered;
    }
}
