package org.jeroen.querydsl;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.jeroen.querydsl.PathMatcher.hasValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.jeroen.querydsl.domain.Car;
import org.jeroen.querydsl.domain.QCar;
import org.junit.Test;

public class PathMatcherTest {
    private static final QCar $ = QCar.car;

    @Test
    public void testMatch() {
        Car car = new Car();
        car.setHorsePower(123);
        
        assertThat(car, hasValue($.horsePower));
        assertThat(car, hasValue($.horsePower, equalTo(123)));
    }
    
    @Test
    public void testMismatch() {
        Car car = new Car();
        car.setHorsePower(123);
        
        Description mismatchDescription = new StringDescription();
        hasValue($.horsePower, equalTo(321)).describeMismatch(car, mismatchDescription);
        assertEquals("value \"car.horsePower\" was <123>", mismatchDescription.toString());
    }
    
    @Test
    public void testDescribe() {
        Description description = new StringDescription();
        hasValue($.horsePower, equalTo(321)).describeTo(description);
        assertEquals("valueOf(\"car.horsePower\", <321>)", description.toString());
    }
    
}
