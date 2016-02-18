package com.github.asufana.playconf.vo;

public class FrameworkId extends AbstractValueObject {
    
    public final String value;
    
    public FrameworkId(final String value) {
        this.value = value.replaceFirst("^%", "");
    }
    
    @Override
    public String toString() {
        return value;
    }
}
