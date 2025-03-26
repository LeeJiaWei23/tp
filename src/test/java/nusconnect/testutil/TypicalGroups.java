package nusconnect.testutil;

import static nusconnect.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CCA;
import static nusconnect.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nusconnect.model.AddressBook;
import nusconnect.model.group.Group;

/**
 * A utility class containing a list of {@code Group} objects to be used in tests.
 */
public class TypicalGroups {
    public static final Group GROUP_CCA = new GroupBuilder().withName(VALID_GROUP_NAME_CCA)
            .build();
    public static final Group GROUP_CS2100 = new GroupBuilder().withName(VALID_GROUP_NAME_CS2100)
            .build();

    // Manually added - Different Group name
    public static final Group GROUP_MA1521 = new GroupBuilder().withName("MA1521 Revision")
            .build();
    public static final Group GROUP_GEN2061 = new GroupBuilder().withName("GEN2061 Project")
            .build();

    private TypicalGroups() {}

    /**
     * Returns an {@code AddressBook} with all the typical groups.
     */
    public static AddressBook getTypicalAddressBookWithGroups() {
        AddressBook ab = new AddressBook();
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }

    public static List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(GROUP_CCA, GROUP_CS2100, GROUP_MA1521, GROUP_GEN2061));
    }

}
