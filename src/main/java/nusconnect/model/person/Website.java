package nusconnect.model.person;

import static java.util.Objects.requireNonNull;
import static nusconnect.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person's website in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebsite(String)}
 */
public class Website {

    public static final String MESSAGE_CONSTRAINTS =
            "Websites may start with 'http://' or 'https://', followed by a valid URL!";

    // Regex for validating website format
    public static final String VALIDATION_REGEX = "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}(/[\\w-]*)*$";

    public final String value;

    /**
     * Constructs a {@code Website}.
     *
     * @param website A valid website.
     */
    public Website(String website) {
        requireNonNull(website, "Website cannot be null");
        checkArgument(isValidWebsite(website), MESSAGE_CONSTRAINTS);
        value = website;
    }

    /**
     * Returns true if a given string is a valid website.
     */
    public static boolean isValidWebsite(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Website)) {
            return false;
        }

        Website otherWebsite = (Website) other;
        return value.equals(otherWebsite.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
