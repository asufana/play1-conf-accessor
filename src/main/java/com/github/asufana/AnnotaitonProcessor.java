package com.github.asufana;

import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotaitonProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            return true;
        }
        
        //generate accessor
        new PlayConfAccessorGenerator(processingEnv).generate();
        
        return true;
    }
}
