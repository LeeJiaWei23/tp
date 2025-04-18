package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nusconnect.commons.core.LogsCenter;
import nusconnect.logic.LogicManager;
import nusconnect.logic.commands.AddCommand;
import nusconnect.logic.commands.ClearCommand;
import nusconnect.logic.commands.Command;
import nusconnect.logic.commands.DeleteCommand;
import nusconnect.logic.commands.EditCommand;
import nusconnect.logic.commands.ExitCommand;
import nusconnect.logic.commands.ExportCommand;
import nusconnect.logic.commands.FindCommand;
import nusconnect.logic.commands.GroupCommand;
import nusconnect.logic.commands.HelpCommand;
import nusconnect.logic.commands.ImportCommand;
import nusconnect.logic.commands.ListCommand;
import nusconnect.logic.commands.SortCommand;
import nusconnect.logic.commands.ViewCommand;
import nusconnect.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    public final LogicManager logicManager;

    public AddressBookParser(LogicManager logicManager) {
        this.logicManager = logicManager;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser(logicManager).parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser(logicManager).parse(arguments);
        case GroupCommand.COMMAND_WORD:
            return new GroupCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
