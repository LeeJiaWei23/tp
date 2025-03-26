package nusconnect.logic.commands;

import static nusconnect.logic.commands.CommandTestUtil.assertCommandFailure;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalIndexes.INDEX_SECOND;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.Messages;
import nusconnect.model.AddressBook;
import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.group.Group;
import nusconnect.testutil.AddressBookBuilder;
import nusconnect.testutil.TypicalGroups;

public class GroupDeleteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        AddressBook ab = new AddressBookBuilder(getTypicalAddressBook())
                .withGroup(TypicalGroups.GROUP_CCA)
                .withGroup(TypicalGroups.GROUP_CS2100)
                .build();
        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() {
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST.getZeroBased());
        GroupDeleteCommand groupDeleteCommand = new GroupDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(GroupDeleteCommand.MESSAGE_SUCCESS,
                groupToDelete.getGroupName());

        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(groupDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGroupList().size() + 1);
        GroupDeleteCommand groupDeleteCommand = new GroupDeleteCommand(outOfBoundIndex);

        assertCommandFailure(groupDeleteCommand, model, Messages.MESSAGE_INVALID_GROUP_INDEX);
    }

    @Test
    public void equals() {
        GroupDeleteCommand deleteFirstCommand = new GroupDeleteCommand(INDEX_FIRST);
        GroupDeleteCommand deleteSecondCommand = new GroupDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        GroupDeleteCommand deleteFirstCommandCopy = new GroupDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        GroupDeleteCommand groupDeleteCommand = new GroupDeleteCommand(targetIndex);
        String expected = GroupDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, groupDeleteCommand.toString());
    }
}
