package nusconnect.logic.parser;

import static nusconnect.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static nusconnect.testutil.Assert.assertThrows;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nusconnect.logic.parser.exceptions.ParseException;
import nusconnect.model.module.Module;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Email;
import nusconnect.model.person.Major;
import nusconnect.model.person.Name;
import nusconnect.model.person.Note;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_MODULE = "#CS2103T";
    private static final String INVALID_ALIAS = "";
    private static final String INVALID_MAJOR = "";
    private static final String INVALID_NOTE = "";
    private static final String INVALID_TELEGRAM = "";
    private static final String INVALID_WEBSITE = "";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "87654321";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_MODULE_1 = "CS2106";
    private static final String VALID_MODULE_2 = "CS2103T";
    private static final String VALID_ALIAS = "RachelWalker";
    private static final String VALID_MAJOR = "Computer Science";
    private static final String VALID_NOTE = "This is a valid note.";
    private static final String VALID_TELEGRAM = "@rachel123";
    private static final String VALID_WEBSITE = "https://rachel.example.com";


    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAlias_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias((String) null));
    }

    @Test
    public void parseAlias_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAlias(INVALID_ALIAS));
    }

    @Test
    public void parseAlias_validValueWithoutWhitespace_returnsAlias() throws Exception {
        Alias expectedAlias = new Alias(VALID_ALIAS);
        assertEquals(expectedAlias, ParserUtil.parseAlias(VALID_ALIAS));
    }

    @Test
    public void parseAlias_validValueWithWhitespace_returnsTrimmedAlias() throws Exception {
        String aliasWithWhitespace = WHITESPACE + VALID_ALIAS + WHITESPACE;
        Alias expectedAlias = new Alias(VALID_ALIAS);
        assertEquals(expectedAlias, ParserUtil.parseAlias(aliasWithWhitespace));
    }

    @Test
    public void parseMajor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((String) null));
    }

    @Test
    public void parseMajor_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseMajor(INVALID_MAJOR));
    }

    @Test
    public void parseMajor_validValueWithoutWhitespace_returnsMajor() throws Exception {
        Major expectedMajor = new Major(VALID_MAJOR);
        assertEquals(expectedMajor, ParserUtil.parseMajor(VALID_MAJOR));
    }

    @Test
    public void parseMajor_validValueWithWhitespace_returnsTrimmedMajor() throws Exception {
        String majorWithWhitespace = WHITESPACE + VALID_MAJOR + WHITESPACE;
        Major expectedMajor = new Major(VALID_MAJOR);
        assertEquals(expectedMajor, ParserUtil.parseMajor(majorWithWhitespace));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote((String) null));
    }

    @Test
    public void parseNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote(INVALID_NOTE));
    }

    @Test
    public void parseNote_validValueWithoutWhitespace_returnsNote() throws Exception {
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(VALID_NOTE));
    }

    @Test
    public void parseNote_validValueWithWhitespace_returnsTrimmedNote() throws Exception {
        String noteWithWhitespace = WHITESPACE + VALID_NOTE + WHITESPACE;
        Note expectedNote = new Note(VALID_NOTE);
        assertEquals(expectedNote, ParserUtil.parseNote(noteWithWhitespace));
    }

    @Test
    public void parseTelegram_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTelegram((String) null));
    }

    @Test
    public void parseTelegram_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTelegram(INVALID_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithoutWhitespace_returnsTelegram() throws Exception {
        Telegram expectedTelegram = new Telegram(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(VALID_TELEGRAM));
    }

    @Test
    public void parseTelegram_validValueWithWhitespace_returnsTrimmedTelegram() throws Exception {
        String telegramWithWhitespace = WHITESPACE + VALID_TELEGRAM + WHITESPACE;
        Telegram expectedTelegram = new Telegram(VALID_TELEGRAM);
        assertEquals(expectedTelegram, ParserUtil.parseTelegram(telegramWithWhitespace));
    }

    @Test
    public void parseWebsite_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWebsite((String) null));
    }

    @Test
    public void parseWebsite_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWebsite(INVALID_WEBSITE));
    }

    @Test
    public void parseWebsite_validValueWithoutWhitespace_returnsWebsite() throws Exception {
        Website expectedWebsite = new Website(VALID_WEBSITE);
        assertEquals(expectedWebsite, ParserUtil.parseWebsite(VALID_WEBSITE));
    }

    @Test
    public void parseWebsite_validValueWithWhitespace_returnsTrimmedWebsite() throws Exception {
        String websiteWithWhitespace = WHITESPACE + VALID_WEBSITE + WHITESPACE;
        Website expectedWebsite = new Website(VALID_WEBSITE);
        assertEquals(expectedWebsite, ParserUtil.parseWebsite(websiteWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseModule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModule(null));
    }

    @Test
    public void parseModule_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModule(INVALID_MODULE));
    }

    @Test
    public void parseModule_validValueWithoutWhitespace_returnsModule() throws Exception {
        Module expectedModule = new Module(VALID_MODULE_1);
        assertEquals(expectedModule, ParserUtil.parseModule(VALID_MODULE_1));
    }

    @Test
    public void parseModule_validValueWithWhitespace_returnsTrimmedModule() throws Exception {
        String moduleWithWhitespace = WHITESPACE + VALID_MODULE_1 + WHITESPACE;
        Module expectedModule = new Module(VALID_MODULE_1);
        assertEquals(expectedModule, ParserUtil.parseModule(moduleWithWhitespace));
    }

    @Test
    public void parseModules_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModules(null));
    }

    @Test
    public void parseModules_collectionWithInvalidModules_throwsParseException() {
        assertThrows(
                ParseException.class, () -> ParserUtil.parseModules(Arrays.asList(VALID_MODULE_1, INVALID_MODULE)));
    }

    @Test
    public void parseModules_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseModules(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseModules_collectionWithValidModules_returnsModuleSet() throws Exception {
        Set<Module> actualModuleSet = ParserUtil.parseModules(Arrays.asList(VALID_MODULE_1, VALID_MODULE_2));
        Set<Module> expectedModuleSet = new HashSet<Module>(Arrays.asList(new Module(VALID_MODULE_1),
                new Module(VALID_MODULE_2)));

        assertEquals(expectedModuleSet, actualModuleSet);
    }
}
