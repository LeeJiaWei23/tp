package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import nusconnect.logic.commands.GroupDeleteCommand;

public class GroupDeleteCommandParserTest {

    private GroupDeleteCommandParser parser = new GroupDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsGroupDeleteCommand() {
        assertParseSuccess(parser, "1", new GroupDeleteCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-5", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "some random text 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 some random text", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GroupDeleteCommand.MESSAGE_USAGE));
    }

}
