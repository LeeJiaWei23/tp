package nusconnect.model.person;

import java.util.List;
import java.util.function.Predicate;

import nusconnect.commons.util.StringUtil;
import nusconnect.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Module} matches any of the keywords given.
 */
public class ModuleContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ModuleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getModules().stream()
                .anyMatch(module -> keywords.stream()
                        .anyMatch(keyword -> StringUtil.containsPartialIgnoreCase(module.moduleName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleContainsKeywordsPredicate)) {
            return false;
        }

        ModuleContainsKeywordsPredicate otherModuleContainsKeywordsPredicate = (ModuleContainsKeywordsPredicate) other;
        return keywords.equals(otherModuleContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
