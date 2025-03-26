package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(INDEX_FIRST);
        assertParseSuccess(parser, "1", new DeleteCommand(targetedIndices));
    }

    @Test
    public void parse_validArgsMultiple_returnsDeleteCommand() {
        List<Index> targetedIndices = new ArrayList<Index>();
        targetedIndices.add(INDEX_SECOND);
        targetedIndices.add(INDEX_FIRST);
        assertParseSuccess(parser, "1 2", new DeleteCommand(targetedIndices));
        assertParseSuccess(parser, "2 1", new DeleteCommand(targetedIndices));
        assertParseSuccess(parser, "1 1 2", new DeleteCommand(targetedIndices));
        assertParseSuccess(parser, "1 2 1 2", new DeleteCommand(targetedIndices));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsMultiple_throwsParseException() {
        assertParseFailure(parser, "1 2 a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
