package com.j256.ormlite.android;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

public class OrmliteTransactionalProcessor implements Processor {
    private static final Set<Class<? extends Annotation>> annotationSet = new HashSet();
    private ProcessingEnvironment processingEnv;

    static {
        annotationSet.add(DatabaseTable.class);
        annotationSet.add(DatabaseField.class);
    }

    public Set<String> getSupportedOptions() {
        return Collections.emptySet();
    }

    public Set<String> getSupportedAnnotationTypes() {
        Set<String> typeSet = new HashSet();
        for (Class<? extends Annotation> clazz : annotationSet) {
            typeSet.add(clazz.getName());
        }
        return typeSet;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_6;
    }

    public void init(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return Collections.emptyList();
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Class<? extends Annotation> annotationClazz : annotationSet) {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(annotationClazz)) {
                System.out.println("-- Element " + element + " has annotation " + annotationClazz);
                this.processingEnv.getMessager().printMessage(Kind.ERROR, element + " error");
            }
        }
        return true;
    }
}
