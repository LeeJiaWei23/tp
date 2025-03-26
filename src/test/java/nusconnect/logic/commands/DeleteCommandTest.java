package nusconnect.logic.commands;

import static nusconnect.logic.commands.CommandTestUtil.assertCommandFailure;
import static nusconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nusconnect.logic.commands.CommandTestUtil.showPersonAtIndex;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalIndexes.INDEX_SECOND;
import static nusconnect.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.Messages;
import nusconnect.model.Model;
import nusconnect.model.ModelManager;
import nusconnect.model.UserPrefs;
import nusconnect.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(INDEX_SECOND);
        targetedIndices.add(INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(secondPersonToDelete)) + "\n"
                + String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(firstPersonToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(secondPersonToDelete);
        expectedModel.deletePerson(firstPersonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndicesUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(outOfBoundIndex);
        targetedIndices.add(INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(outOfBoundIndex);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<Index> firstTargetedIndices = new ArrayList<Index>();
        firstTargetedIndices.add(INDEX_FIRST);
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstTargetedIndices);
        List<Index> secondTargetedIndices = new ArrayList<Index>();
        secondTargetedIndices.add(INDEX_SECOND);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondTargetedIndices);
        List<Index> thirdTargetedIndices = new ArrayList<Index>();
        thirdTargetedIndices.add(INDEX_SECOND);
        thirdTargetedIndices.add(INDEX_FIRST);
        DeleteCommand deleteThirdCommand = new DeleteCommand(thirdTargetedIndices);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        List<Index> firstTargetedIndicesCopy = new ArrayList<Index>();
        firstTargetedIndicesCopy.add(INDEX_FIRST);
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstTargetedIndicesCopy);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person(s) -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertFalse(deleteFirstCommand.equals(deleteThirdCommand));
        assertFalse(deleteThirdCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(targetIndex);
        DeleteCommand deleteCommand = new DeleteCommand(targetedIndices);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=[" + targetIndex + "]}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
