package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.ParserUtil.parseIndex;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.commands.GroupDeleteCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GroupDeleteCommand object
 */
public class GroupDeleteCommandParser implements Parser<GroupDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GroupDeleteCommand
     * and returns a GroupDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupDeleteCommand.MESSAGE_USAGE));
        }

        try {
            Index index = parseIndex(trimmedArgs);
            return new GroupDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
