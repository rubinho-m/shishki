package com.rubinho.shishki.rest.versions;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
public @interface ApiVersioned {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}