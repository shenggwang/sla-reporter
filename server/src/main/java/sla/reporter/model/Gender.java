package sla.reporter.model;

/**
 * An enumeration that defines Gender.
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public enum Gender {
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
    /**
     * Gets the gender.
     */
    public String getGender() {
        return this.gender;
    }
}
