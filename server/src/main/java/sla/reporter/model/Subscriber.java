package sla.reporter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import org.json.simple.JSONObject;
import sla.reporter.utils.RegexUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class that represents object of Subscriber.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public class Subscriber implements Serializable {

    /**
     * The subscriber email.
     */
    private final String email;
    /**
     * The optional subscriber first name.
     */
    private final Optional<String> firstName;
    /**
     * The optional subscriber {@link Gender gender}.
     */
    private final Gender gender;
    /**
     * The subscriber birthday.
     *
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html">LocalDate</a>.
     */
    private final LocalDate birthDay;
    /**
     * The flag indicating whether the subscriber consents;
     */
    private final boolean consent;
    /**
     * The newsletter id corresponding the campaign.
     */
    private final String newsletterId;

    /**
     * The date formatter used to convert birthday to and from String.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Private constructor.
     *
     * @param builder The subscriber builder.
     */
    private Subscriber(final Builder builder) {
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.gender = builder.gender;
        this.birthDay = builder.birthDay;
        this.consent = builder.consent;
        this.newsletterId = builder.newsletterId;
    }
    /**
     * Gets the email.
     *
     * @return The email.
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * Gets the optional first name.
     *
     * @return The optional first name.
     */
    public String getFirstName() {
        return this.firstName.or("None");
    }
    /**
     * Gets the {@link Gender gender}.
     *
     * @return The {@link Gender gender}.
     */
    public String getGender() {
        return this.gender.toString();
    }
    /**
     * Gets the birthday.
     *
     * @return The birthday.
     */
    public String getBirthDay() {
        return this.birthDay.toString();
    }
    /**
     * Gets the birthday.
     *
     * @return {@code true} if the subscriber consents, {@code false} otherwise.
     */
    public String getConsent() {
        return String.valueOf(this.consent);
    }
    /**
     * Gets the newsletter id.
     *
     * @return the newsletter id.
     */
    public String getNewsletterId() {
        return this.newsletterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Subscriber that = (Subscriber) o;
        return this.consent == that.consent
                && Objects.equals(this.email, that.email)
                && Objects.equals(this.birthDay, that.birthDay)
                && Objects.equals(this.newsletterId, that.newsletterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.email, this.birthDay, this.consent, this.newsletterId);
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "email='" + this.email + '\'' +
                ", firstName=" + this.firstName.or("none") +
                ", gender=" + this.gender +
                ", birthDay=" + this.birthDay.format(FORMATTER) +
                ", consent=" + this.consent +
                ", newsletterId='" + this.newsletterId + '\'' +
                '}';
    }

    /**
     * Gets (@link JsonObject} from subscriber.
     *
     * @return The {@link JSONObject} of this subscriber.
     */
    @JsonIgnore
    public JSONObject getJsonObject() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", this.email);
        jsonObject.put("firstName", this.firstName.or("none"));
        jsonObject.put("gender", this.gender.toString());
        jsonObject.put("birthDay", this.birthDay.format(FORMATTER));
        jsonObject.put("consent", String.valueOf(this.consent));
        jsonObject.put("newsletterId", this.newsletterId);
        return jsonObject;
    }

    /**
     * Builder used to create instances of Subscriber.
     */
    public static class Builder {

        /**
         * The subscriber email.
         */
        private String email;
        /**
         * The optional subscriber first name.
         *
         * @implSpec By default the optional is absent.
         */
        private Optional<String> firstName = Optional.absent();
        /**
         * The optional subscriber {@link Gender gender}.
         *
         * @implSpec By default the gender is {@link Gender#NONE}.
         */
        private Gender gender = Gender.NONE;
        /**
         * The subscriber birthday.
         *
         * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html">LocalDate</a>.
         */
        private LocalDate birthDay;
        /**
         * The flag indicating whether the subscriber consents;
         */
        private boolean consent = false;
        /**
         * The newsletter id corresponding the campaign.
         */
        private String newsletterId;

        /**
         * Sets the email.
         *
         * @return This builder instance.
         */
        public Builder email(final String email) {
            this.email = email;
            return this;
        }
        /**
         * Sets the first name.
         *
         * @return This builder instance.
         */
        public Builder firstName(final String firstName) {
            this.firstName = Optional.of(firstName);
            return this;
        }
        /**
         * Sets the gender.
         *
         * @return This builder instance.
         */
        public Builder gender(final Gender gender) {
            this.gender = gender;
            return this;
        }
        /**
         * Sets the birthday.
         *
         * @return This builder instance.
         */
        public Builder birthDay(final LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }
        /**
         * Sets the consent.
         *
         * @return This builder instance.
         */
        public Builder consent(final boolean consent) {
            this.consent = consent;
            return this;
        }
        /**
         * Sets the newsletter id.
         *
         * @return This builder instance.
         */
        public Builder newsletterId(final String newsletterId) {
            this.newsletterId = newsletterId;
            return this;
        }
        /**
         * Builds a new {@link Subscriber} from the information supplied to this {@link Subscriber.Builder}.
         *
         * @return A new subscriber instance.
         */
        public Subscriber build() {
            checkNotNull(this.email, "Email can't be null");
            checkArgument(RegexUtils.isEmailValid(this.email), "Email is not valid");
            checkNotNull(this.birthDay, "Birthday can't be null");
            checkNotNull(this.newsletterId, "NewsletterId can't be null");
            return new Subscriber(this);
        }

        /**
         * Fill a builder with attribute values from the provided {@link JSONObject}.
         *
         * @param jsonObject The expected{@link JSONObject}.
         * @return This builder instance.
         */
        public Builder fromJsonObject(final JSONObject jsonObject) {
            checkNotNull(jsonObject.get("email"), "Email can't be null");
            checkNotNull(jsonObject.get("firstName"), "Birthday can't be null");
            checkNotNull(jsonObject.get("gender"), "Birthday can't be null");
            checkNotNull(jsonObject.get("birthDay"), "Birthday can't be null");
            checkNotNull(jsonObject.get("consent"), "Birthday can't be null");
            checkNotNull(jsonObject.get("newsletterId"), "NewsletterId can't be null");
            this.email = (String) jsonObject.get("email");
            this.firstName = Optional.of((String) jsonObject.get("firstName"));
            this.gender = Gender.fromString((String) jsonObject.get("gender")).or(Gender.NONE);
            this.birthDay = LocalDate.parse((String) jsonObject.get("birthDay"), FORMATTER);
            this.consent = Boolean.parseBoolean((String) jsonObject.get("consent"));
            this.newsletterId = (String) jsonObject.get("newsletterId");
            return this;
        }
    }
}
