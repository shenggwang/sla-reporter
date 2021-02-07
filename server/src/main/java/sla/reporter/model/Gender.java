package sla.reporter.model;

import com.google.common.base.Optional;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * An enumeration that defines Gender.
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public enum Gender implements Serializable {
    /**
     * Male.
     */
    MALE("male"),
    /**
     * Female.
     */
    FEMALE("female"),
    /**
     * None.
     * @implNote This indicates that subscriber doesn't want to share gender.
     */
    NONE("none");

    /**
     * The gender.
     */
    private final String gender;

    /**
     * Constructor.
     */
    Gender(final String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.gender;
    }

    // Implementing a from string method on an enum type
    private static final Map<String, Gender> stringToEnum = Stream.of(values()).collect(toMap(Object::toString, e -> e));
    // returns operation for string, if any
    public static Optional<Gender> fromString(final String gender) {
        return Optional.fromNullable(stringToEnum.get(gender));
    }
}
