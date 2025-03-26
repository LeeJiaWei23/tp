package nusconnect.logic.parser;

import static nusconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nusconnect.logic.commands.CommandTestUtil.ALIAS_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.ALIAS_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.COURSE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.COURSE_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_ALIAS_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_NOTE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static nusconnect.logic.commands.CommandTestUtil.INVALID_WEBSITE_DESC;
import static nusconnect.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nusconnect.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nusconnect.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_ALIAS_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_ALIAS_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_COURSE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_COURSE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2103T;
import static nusconnect.logic.commands.CommandTestUtil.VALID_MODULE_CS2106;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static nusconnect.logic.commands.CommandTestUtil.VALID_WEBSITE_AMY;
import static nusconnect.logic.commands.CommandTestUtil.VALID_WEBSITE_BOB;
import static nusconnect.logic.commands.CommandTestUtil.WEBSITE_DESC_AMY;
import static nusconnect.logic.commands.CommandTestUtil.WEBSITE_DESC_BOB;
import static nusconnect.logic.parser.CliSyntax.PREFIX_ALIAS;
import static nusconnect.logic.parser.CliSyntax.PREFIX_EMAIL;
import static nusconnect.logic.parser.CliSyntax.PREFIX_MODULE;
import static nusconnect.logic.parser.CliSyntax.PREFIX_PHONE;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nusconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nusconnect.testutil.TypicalIndexes.INDEX_FIRST;
import static nusconnect.testutil.TypicalIndexes.INDEX_SECOND;
import static nusconnect.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import nusconnect.commons.core.index.Index;
import nusconnect.logic.Messages;
import nusconnect.logic.commands.EditCommand;
import nusconnect.logic.commands.EditCommand.EditPersonDescriptor;
import nusconnect.model.module.Module;
import nusconnect.model.person.Alias;
import nusconnect.model.person.Course;
import nusconnect.model.person.Email;
import nusconnect.model.person.Name;
import nusconnect.model.person.Note;
import nusconnect.model.person.Phone;
import nusconnect.model.person.Telegram;
import nusconnect.model.person.Website;
import nusconnect.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_MODULE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ALIAS_DESC, Alias.MESSAGE_CONSTRAINTS); // invalid alias
        assertParseFailure(parser, "1" + INVALID_COURSE_DESC, Course.MESSAGE_CONSTRAINTS); // invalid course
        assertParseFailure(parser, "1" + INVALID_NOTE_DESC, Note.MESSAGE_CONSTRAINTS); // invalid note
        assertParseFailure(parser, "1" + INVALID_TELEGRAM_DESC, Telegram.MESSAGE_CONSTRAINTS); // invalid telegram
        assertParseFailure(parser, "1" + INVALID_WEBSITE_DESC, Website.MESSAGE_CONSTRAINTS); // invalid website

        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Module.MESSAGE_CONSTRAINTS); // invalid module

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_MODULE} alone will reset the Modules of the {@code Person} being edited,
        // parsing it together with a valid module results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Module.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Module.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Module.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY
                        + VALID_ALIAS_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + ALIAS_DESC_BOB + COURSE_DESC_BOB + NOTE_DESC_BOB
                + TELEGRAM_DESC_BOB + WEBSITE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND;



        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .withAlias(VALID_ALIAS_BOB)
                .withCourse(VALID_COURSE_BOB)
                .withNote(VALID_NOTE_BOB)
                .withTelegram(VALID_TELEGRAM_BOB)
                .withWebsite(VALID_WEBSITE_BOB)
                .withTags(VALID_MODULE_CS2103T, VALID_MODULE_CS2106)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // alias
        userInput = targetIndex.getOneBased() + ALIAS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAlias(VALID_ALIAS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // course
        userInput = targetIndex.getOneBased() + COURSE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withCourse(VALID_COURSE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetIndex.getOneBased() + NOTE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNote(VALID_NOTE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // telegram
        userInput = targetIndex.getOneBased() + TELEGRAM_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withTelegram(VALID_TELEGRAM_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // website
        userInput = targetIndex.getOneBased() + WEBSITE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withWebsite(VALID_WEBSITE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // Modules
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_MODULE_CS2106).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased()
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ALIAS_DESC_AMY + COURSE_DESC_AMY
                + NOTE_DESC_AMY + TELEGRAM_DESC_AMY + WEBSITE_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased()
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC + INVALID_ALIAS_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC + INVALID_ALIAS_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ALIAS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
