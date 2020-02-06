package com.sample.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used on methods which call customer services 
 * for apply metric aspect on them.  
 *
 * @author Mahnaz
 * @Jan 31, 2020
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Metric {

}

