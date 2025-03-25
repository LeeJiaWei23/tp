package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nusconnect.logic.commands.Command;
import nusconnect.logic.commands.GroupCommand;
import nusconnect.logic.commands.GroupCreateCommand;
import nusconnect.logic.commands.GroupDeleteCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for group commands.
 */
public class GroupCommandParser implements Parser<Command> {

    private static final Pattern GROUP_COMMAND_FORMAT =
            Pattern.compile("(?<subCommand>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of group commands
     * and returns the appropriate command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        Matcher matcher = GROUP_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupCommand.MESSAGE_USAGE));
        }

        String subCommand = matcher.group("subCommand");
        String arguments = matcher.group("arguments").trim();

        switch (subCommand) {
        case GroupCreateCommand.COMMAND_WORD:
            return new GroupCreateCommandParser().parse(arguments);

        case GroupDeleteCommand.COMMAND_WORD:
            return new GroupDeleteCommandParser().parse(arguments);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Unknown group subcommand: " + subCommand));
        }
    }
}
