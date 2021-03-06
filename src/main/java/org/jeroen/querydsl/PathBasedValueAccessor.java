package org.jeroen.querydsl;

import static java.lang.String.format;

import com.mysema.query.types.Expression;
import com.mysema.query.types.Path;

/**
 * Access the current value of a bean, using paths.
 * 
 * @author Jeroen van Schagen
 * @since 10-08-2011
 */
public class PathBasedValueAccessor {
    private final PropertyAccessor propertyAccessor;
    
    public PathBasedValueAccessor() {
        this(new SimplePropertyAccessor());
    }
    
    public PathBasedValueAccessor(PropertyAccessor propertyAccessor) {
        this.propertyAccessor = propertyAccessor;
    }

    public <T> T getPathValue(Object bean, Path<T> path) {
        checkBeanWithPathRoot(bean, path);
        
        // Retrieve property container recursively
        if(!path.getMetadata().isRoot()) {
            bean = getPathValue(bean, path.getMetadata().getParent());
        }

        Object result = null;
        if(bean != null) {
            switch(path.getMetadata().getPathType()) {
            case PROPERTY:
                String propertyName = expressionOf(path).toString();
                result = propertyAccessor.getPropertyValue(bean, propertyName);
                break;
            case VARIABLE:
                result = bean;
                break;
            }
        }
        return (T) result;
    }
    
    private static Expression<?> expressionOf(Path<?> path) {
        return path.getMetadata().getExpression();
    }
    
    private static void checkBeanWithPathRoot(Object bean, Path<?> path) {
        Class<?> rootClass = path.getRoot().getType();
        if(! rootClass.isAssignableFrom(bean.getClass()) ) {
            throw new IllegalArgumentException(format("Bean is not of path root type '%s'.", rootClass.getSimpleName()));
        }
    }
    
}
