package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import nusconnect.commons.core.LogsCenter;
import nusconnect.commons.core.index.Index;
import nusconnect.commons.util.ToStringBuilder;
import nusconnect.logic.Messages;
import nusconnect.logic.commands.exceptions.CommandException;
import nusconnect.model.Model;
import nusconnect.model.group.Group;

/**
 * Deletes a group identified using it's displayed index.
 */
public class GroupDeleteCommand extends GroupCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = GroupCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the group at the specified index in the group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: group " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted group: %1$s";

    private static final Logger logger = LogsCenter.getLogger(GroupDeleteCommand.class);

    private final Index targetIndex;

    /**
     * Creates a GroupDeleteCommand to delete the specified group
     */
    public GroupDeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        logger.info("GroupDeleteCommand created with target index: " + targetIndex.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getFilteredGroupList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_INDEX);
        }

        Group deletedGroup = model.getFilteredGroupList().get(targetIndex.getZeroBased());
        model.deleteGroup(deletedGroup);
        return new CommandResult(String.format(MESSAGE_SUCCESS, deletedGroup.getGroupName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupDeleteCommand)) {
            return false;
        }

        GroupDeleteCommand otherDeleteCommand = (GroupDeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
