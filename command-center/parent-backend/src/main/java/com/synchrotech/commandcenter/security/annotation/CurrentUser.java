package com.synchrotech.commandcenter.security.annotation;

import java.lang.annotation.*;

/**
 * Annotation to inject the current authenticated user.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
} 