package sla.reporter.model;

import com.google.common.base.Optional;
import sla.reporter.utils.RegexUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class that represents object of Subscriber.
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
     * Private constructor.
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
     * @return The email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Gets the optional first name.
     * @return The optional first name.
     */
    public Optional<String> getFirstName() {
        return firstName;
    }
    /**
     * Gets the {@link Gender gender}.
     * @return The {@link Gender gender}.
     */
    public Gender getGender() {
        return gender;
    }
    /**
     * Gets the birthday.
     * @return The birthday.
     */
    public LocalDate getBirthDay() {
        return birthDay;
    }
    /**
     * Gets the birthday.
     * @return {@code true} if the subscriber consents, {@code false} otherwise.
     */
    public boolean isConsent() {
        return consent;
    }
    /**
     * Gets the newsletter id.
     * @return the newsletter id.
     */
    public String getNewsletterId() {
        return newsletterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Subscriber that = (Subscriber) o;
        return consent == that.consent
                && Objects.equals(email, that.email)
                && Objects.equals(birthDay, that.birthDay)
                && Objects.equals(newsletterId, that.newsletterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, birthDay, consent, newsletterId);
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "email='" + email + '\'' +
                ", firstName=" + firstName.or("none") +
                ", gender=" + gender +
                ", birthDay=" + birthDay +
                ", consent=" + consent +
                ", newsletterId='" + newsletterId + '\'' +
                '}';
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
         * @implSpec By default the optional is absent.
         */
        private Optional<String> firstName = Optional.absent();
        /**
         * The optional subscriber {@link Gender gender}.
         * @implSpec By default the gender is {@link Gender#NONE}.
         */
        private Gender gender = Gender.NONE;
        /**
         * The subscriber birthday.
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
         */
        public Builder email(final String email) {
            this.email = email;
            return this;
        }
        /**
         * Sets the first name.
         */
        public Builder firstName(final String firstName) {
            this.firstName = Optional.of(firstName);
            return this;
        }
        /**
         * Sets the gender.
         */
        public Builder gender(final Gender gender) {
            this.gender = gender;
            return this;
        }
        /**
         * Sets the birthday.
         */
        public Builder birthDay(final LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }
        /**
         * Sets the consent.
         */
        public Builder consent(final boolean consent) {
            this.consent = consent;
            return this;
        }
        /**
         * Sets the newsletter id.
         */
        public Builder newsletterId(final String newsletterId) {
            this.newsletterId = newsletterId;
            return this;
        }
        /**
         * Builds a new {@link Subscriber} from the information supplied to this {@link Subscriber.Builder}.
         */
        public Subscriber build() {
            checkNotNull(this.email, "Email can't be null");
            checkArgument(RegexUtils.isEmailValid(this.email), "Email is not valid");
            checkNotNull(this.birthDay, "Birthday can't be null");
            checkNotNull(this.newsletterId, "NewsletterId can't be null");
            return new Subscriber(this);
        }
    }
}
