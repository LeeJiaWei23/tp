package nusconnect.logic.commands;

import static nusconnect.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CCA;
import static nusconnect.logic.commands.CommandTestUtil.VALID_GROUP_NAME_CS2100;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandFailure;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.group.Group;

public class GroupCreateCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGroup_success() {

        GroupCreateCommand groupCreateCommand = new GroupCreateCommand(VALID_GROUP_NAME_CS2100);

        String expectedMessage = String.format(GroupCreateCommand.MESSAGE_SUCCESS, VALID_GROUP_NAME_CS2100);

        expectedModel.addGroup(new Group(VALID_GROUP_NAME_CS2100));

        assertCommandSuccess(groupCreateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {

        // First add the group to the model
        model.addGroup(new Group(VALID_GROUP_NAME_CCA));

        // Try to add the same group again
        GroupCreateCommand groupCreateCommand = new GroupCreateCommand(VALID_GROUP_NAME_CCA);

        assertCommandFailure(groupCreateCommand, model, GroupCreateCommand.MESSAGE_DUPLICATE_GROUP);
    }

    @Test
    public void equals() {
        GroupCreateCommand addCs2100Command = new GroupCreateCommand(VALID_GROUP_NAME_CS2100);
        GroupCreateCommand addCcaCommand = new GroupCreateCommand(VALID_GROUP_NAME_CCA);

        // same object -> returns true
        assertTrue(addCs2100Command.equals(addCs2100Command));

        // same values -> returns true
        GroupCreateCommand addCs2100CommandCopy = new GroupCreateCommand(VALID_GROUP_NAME_CS2100);
        assertTrue(addCs2100Command.equals(addCs2100CommandCopy));

        // different types -> returns false
        assertFalse(addCs2100Command.equals(1));

        // null -> returns false
        assertFalse(addCs2100Command.equals(null));

        // different group -> returns false
        assertFalse(addCs2100Command.equals(addCcaCommand));
    }

    @Test
    public void toStringMethod() {
        GroupCreateCommand command = new GroupCreateCommand(VALID_GROUP_NAME_CS2100);
        String expected = GroupCreateCommand.class.getCanonicalName() + "{groupName=" + VALID_GROUP_NAME_CS2100 + "}";
        assertEquals(expected, command.toString());
    }
}
