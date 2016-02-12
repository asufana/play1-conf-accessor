package com.github.asufana.playconf.vo;


public class Config extends AbstractValueObject {
    
    public final String key;
    public final String value;
    
    public Config(final String key, final String value) {
        this.key = key;
        this.value = value;
    }
}
