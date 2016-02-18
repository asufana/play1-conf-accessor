package com.github.asufana.playconf.collections;

import java.util.*;
import java.util.stream.*;

import org.apache.commons.lang3.builder.*;

import com.github.asufana.playconf.vo.*;

public class ConfigList {
    
    private final List<Config> list;
    
    public ConfigList(final List<Config> list) {
        this.list = Collections.unmodifiableList(list);
    }
    
    //値を取得
    public String get(final String key) {
        final Optional<String> value = list.stream()
                                           .filter(config -> config.key.toLowerCase()
                                                                       .equals(key.toLowerCase()))
                                           .map(config -> config.value)
                                           .findAny();
        return value.orElse(null);
    }
    
    public Stream<Config> stream() {
        return list.stream();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, other);
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
