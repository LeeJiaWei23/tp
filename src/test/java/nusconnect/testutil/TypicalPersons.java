package nusconnect.testutil;

import static nusconnect.logic.commands.CommandTestUtil.VALID_ALIAS_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_ALIAS_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MAJOR_ANY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2103T;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2106;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nusconnect.model.AddressBook;
import nusconnect.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withAlias("AliceAlias")
            .withMajor("Engineering")
            .withNote("Enjoys reading")
            .withTelegram("@alice")
            .withWebsite("https://alicepauline.com")
            .withModules("CS2103T").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withAlias("BensonAlias")
            .withMajor("Comp Science")
            .withNote("Great at teamwork")
            .withTelegram("@benson")
            .withWebsite("https://bensonmeier.com")
            .withModules("CS2106", "CS2107").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAlias("CarlAlias")
            .withMajor("Business")
            .withNote("Detail-oriented")
            .withTelegram("@carlkurz")
            .withWebsite("https://carlkurz.com")
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAlias("DanielAlias")
            .withMajor("Mech Eng")
            .withNote("Enthusiastic learner")
            .withTelegram("@daniel")
            .withWebsite("https://danielmeier.com")
            .withModules("CS2106").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94822242")
            .withEmail("werner@example.com")
            .withAlias("ElleAlias")
            .withMajor("Comp Science")
            .withNote("Creative thinker")
            .withTelegram("@ellemeyer")
            .withWebsite("https://ellemeyer.com")
            .build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94824272")
            .withEmail("lydia@example.com")
            .withAlias("FionaAlias")
            .withMajor("Engineering")
            .withNote("Tech savvy")
            .withTelegram("@fionakunz")
            .withWebsite("https://fionakunz.com")
            .build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94824422")
            .withEmail("anna@example.com")
            .withAlias("GeorgeAlias")
            .withMajor("Business")
            .withNote("Sports enthusiast")
            .withTelegram("@georgebest")
            .withWebsite("https://georgebest.com")
            .withModules("CS2100").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84824242")
            .withEmail("stefan@example.com")
            .withAlias("HoonAlias")
            .withMajor("Engineering")
            .withNote("Friendly")
            .withTelegram("@hoonmeier")
            .withWebsite("https://hoonmeier.com")
            .build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84821312")
            .withEmail("hans@example.com")
            .withAlias("IdaAlias")
            .withMajor("Comp Science")
            .withNote("Reliable")
            .withTelegram("@idamueller")
            .withWebsite("https://idamueller.com")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAlias(VALID_ALIAS_AMY)
            .withMajor(VALID_MAJOR_ANY)
            .withNote(VALID_NOTE_AMY)
            .withTelegram(VALID_TELEGRAM_AMY)
            .withWebsite(VALID_WEBSITE_AMY)
            .withModules(VALID_MODULE_CS2106)
            .build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAlias(VALID_ALIAS_BOB)
            .withMajor(VALID_MAJOR_BOB)
            .withNote(VALID_NOTE_BOB)
            .withTelegram(VALID_TELEGRAM_BOB)
            .withWebsite(VALID_WEBSITE_BOB)
            .withModules(VALID_MODULE_CS2103T, VALID_MODULE_CS2106)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
