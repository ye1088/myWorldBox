package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry.ExtensionInfo;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.UnknownFieldSet.Field;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tools.tar.TarConstants;

public final class TextFormat {
    private static final Printer DEFAULT_PRINTER = new Printer();
    private static final Parser PARSER = Parser.newBuilder().build();
    private static final Printer SINGLE_LINE_PRINTER = new Printer().setSingleLineMode(true);
    private static final Printer UNICODE_PRINTER = new Printer().setEscapeNonAscii(false);
    private static final Logger logger = Logger.getLogger(TextFormat.class.getName());

    private interface ByteSequence {
        byte byteAt(int i);

        int size();
    }

    static class InvalidEscapeSequenceException extends IOException {
        private static final long serialVersionUID = -8164033650142593304L;

        InvalidEscapeSequenceException(String description) {
            super(description);
        }
    }

    public static class ParseException extends IOException {
        private static final long serialVersionUID = 3196188060225107702L;
        private final int column;
        private final int line;

        public ParseException(String message) {
            this(-1, -1, message);
        }

        public ParseException(int line, int column, String message) {
            super(Integer.toString(line) + ":" + column + ": " + message);
            this.line = line;
            this.column = column;
        }

        public int getLine() {
            return this.line;
        }

        public int getColumn() {
            return this.column;
        }
    }

    public static class Parser {
        private static final int BUFFER_SIZE = 4096;
        private final boolean allowUnknownFields;
        private final SingularOverwritePolicy singularOverwritePolicy;

        public static class Builder {
            private boolean allowUnknownFields = false;
            private SingularOverwritePolicy singularOverwritePolicy = SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;

            public Builder setSingularOverwritePolicy(SingularOverwritePolicy p) {
                this.singularOverwritePolicy = p;
                return this;
            }

            public Parser build() {
                return new Parser(this.allowUnknownFields, this.singularOverwritePolicy);
            }
        }

        public enum SingularOverwritePolicy {
            ALLOW_SINGULAR_OVERWRITES,
            FORBID_SINGULAR_OVERWRITES
        }

        private Parser(boolean allowUnknownFields, SingularOverwritePolicy singularOverwritePolicy) {
            this.allowUnknownFields = allowUnknownFields;
            this.singularOverwritePolicy = singularOverwritePolicy;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public void merge(Readable input, com.google.protobuf.Message.Builder builder) throws IOException {
            merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(CharSequence input, com.google.protobuf.Message.Builder builder) throws ParseException {
            merge(input, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(Readable input, ExtensionRegistry extensionRegistry, com.google.protobuf.Message.Builder builder) throws IOException {
            merge(toStringBuilder(input), extensionRegistry, builder);
        }

        private static StringBuilder toStringBuilder(Readable input) throws IOException {
            StringBuilder text = new StringBuilder();
            CharBuffer buffer = CharBuffer.allocate(4096);
            while (true) {
                int n = input.read(buffer);
                if (n == -1) {
                    return text;
                }
                buffer.flip();
                text.append(buffer, 0, n);
            }
        }

        public void merge(CharSequence input, ExtensionRegistry extensionRegistry, com.google.protobuf.Message.Builder builder) throws ParseException {
            Tokenizer tokenizer = new Tokenizer(input);
            BuilderAdapter target = new BuilderAdapter(builder);
            while (!tokenizer.atEnd()) {
                mergeField(tokenizer, extensionRegistry, target);
            }
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MergeTarget target) throws ParseException {
            FieldDescriptor field = null;
            Descriptor type = target.getDescriptorForType();
            ExtensionInfo extension = null;
            if (tokenizer.tryConsume("[")) {
                StringBuilder name = new StringBuilder(tokenizer.consumeIdentifier());
                while (tokenizer.tryConsume(".")) {
                    name.append('.');
                    name.append(tokenizer.consumeIdentifier());
                }
                extension = target.findExtensionByName(extensionRegistry, name.toString());
                if (extension == null) {
                    if (this.allowUnknownFields) {
                        TextFormat.logger.warning("Extension \"" + name + "\" not found in the ExtensionRegistry.");
                    } else {
                        throw tokenizer.parseExceptionPreviousToken("Extension \"" + name + "\" not found in the ExtensionRegistry.");
                    }
                } else if (extension.descriptor.getContainingType() != type) {
                    throw tokenizer.parseExceptionPreviousToken("Extension \"" + name + "\" does not extend message type \"" + type.getFullName() + "\".");
                } else {
                    field = extension.descriptor;
                }
                tokenizer.consume("]");
            } else {
                String name2 = tokenizer.consumeIdentifier();
                field = type.findFieldByName(name2);
                if (field == null) {
                    field = type.findFieldByName(name2.toLowerCase(Locale.US));
                    if (!(field == null || field.getType() == Type.GROUP)) {
                        field = null;
                    }
                }
                if (!(field == null || field.getType() != Type.GROUP || field.getMessageType().getName().equals(name2))) {
                    field = null;
                }
                if (field == null) {
                    if (this.allowUnknownFields) {
                        TextFormat.logger.warning("Message type \"" + type.getFullName() + "\" has no field named \"" + name2 + "\".");
                    } else {
                        throw tokenizer.parseExceptionPreviousToken("Message type \"" + type.getFullName() + "\" has no field named \"" + name2 + "\".");
                    }
                }
            }
            if (field != null) {
                if (field.getJavaType() == JavaType.MESSAGE) {
                    tokenizer.tryConsume(":");
                } else {
                    tokenizer.consume(":");
                }
                if (field.isRepeated() && tokenizer.tryConsume("[")) {
                    while (true) {
                        consumeFieldValue(tokenizer, extensionRegistry, target, field, extension);
                        if (!tokenizer.tryConsume("]")) {
                            tokenizer.consume(MiPushClient.ACCEPT_TIME_SEPARATOR);
                        } else {
                            return;
                        }
                    }
                }
                consumeFieldValue(tokenizer, extensionRegistry, target, field, extension);
            } else if (!tokenizer.tryConsume(":") || tokenizer.lookingAt("{") || tokenizer.lookingAt(SimpleComparison.LESS_THAN_OPERATION)) {
                skipFieldMessage(tokenizer);
            } else {
                skipFieldValue(tokenizer);
            }
        }

        private void consumeFieldValue(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MergeTarget target, FieldDescriptor field, ExtensionInfo extension) throws ParseException {
            Object value = null;
            if (field.getJavaType() != JavaType.MESSAGE) {
                switch (field.getType()) {
                    case INT32:
                    case SINT32:
                    case SFIXED32:
                        value = Integer.valueOf(tokenizer.consumeInt32());
                        break;
                    case INT64:
                    case SINT64:
                    case SFIXED64:
                        value = Long.valueOf(tokenizer.consumeInt64());
                        break;
                    case BOOL:
                        value = Boolean.valueOf(tokenizer.consumeBoolean());
                        break;
                    case FLOAT:
                        value = Float.valueOf(tokenizer.consumeFloat());
                        break;
                    case DOUBLE:
                        value = Double.valueOf(tokenizer.consumeDouble());
                        break;
                    case UINT32:
                    case FIXED32:
                        value = Integer.valueOf(tokenizer.consumeUInt32());
                        break;
                    case UINT64:
                    case FIXED64:
                        value = Long.valueOf(tokenizer.consumeUInt64());
                        break;
                    case STRING:
                        value = tokenizer.consumeString();
                        break;
                    case BYTES:
                        value = tokenizer.consumeByteString();
                        break;
                    case ENUM:
                        EnumDescriptor enumType = field.getEnumType();
                        if (tokenizer.lookingAtInteger()) {
                            int number = tokenizer.consumeInt32();
                            value = enumType.findValueByNumber(number);
                            if (value == null) {
                                throw tokenizer.parseExceptionPreviousToken("Enum type \"" + enumType.getFullName() + "\" has no value with number " + number + '.');
                            }
                        }
                        String id = tokenizer.consumeIdentifier();
                        value = enumType.findValueByName(id);
                        if (value == null) {
                            throw tokenizer.parseExceptionPreviousToken("Enum type \"" + enumType.getFullName() + "\" has no value named \"" + id + "\".");
                        }
                        break;
                    case MESSAGE:
                    case GROUP:
                        throw new RuntimeException("Can't get here.");
                    default:
                        break;
                }
            }
            String endToken;
            if (tokenizer.tryConsume(SimpleComparison.LESS_THAN_OPERATION)) {
                endToken = SimpleComparison.GREATER_THAN_OPERATION;
            } else {
                tokenizer.consume("{");
                endToken = "}";
            }
            MergeTarget subField = target.newMergeTargetForField(field, extension == null ? null : extension.defaultInstance);
            while (!tokenizer.tryConsume(endToken)) {
                if (tokenizer.atEnd()) {
                    throw tokenizer.parseException("Expected \"" + endToken + "\".");
                }
                mergeField(tokenizer, extensionRegistry, subField);
            }
            value = subField.finish();
            if (field.isRepeated()) {
                target.addRepeatedField(field, value);
            } else if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && target.hasField(field)) {
                throw tokenizer.parseExceptionPreviousToken("Non-repeated field \"" + field.getFullName() + "\" cannot be overwritten.");
            } else if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && field.getContainingOneof() != null && target.hasOneof(field.getContainingOneof())) {
                OneofDescriptor oneof = field.getContainingOneof();
                throw tokenizer.parseExceptionPreviousToken("Field \"" + field.getFullName() + "\" is specified along with field \"" + target.getOneofFieldDescriptor(oneof).getFullName() + "\", another member of oneof \"" + oneof.getName() + "\".");
            } else {
                target.setField(field, value);
            }
        }

        private void skipField(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsume("[")) {
                do {
                    tokenizer.consumeIdentifier();
                } while (tokenizer.tryConsume("."));
                tokenizer.consume("]");
            } else {
                tokenizer.consumeIdentifier();
            }
            if (!tokenizer.tryConsume(":") || tokenizer.lookingAt(SimpleComparison.LESS_THAN_OPERATION) || tokenizer.lookingAt("{")) {
                skipFieldMessage(tokenizer);
            } else {
                skipFieldValue(tokenizer);
            }
            if (!tokenizer.tryConsume(";")) {
                tokenizer.tryConsume(MiPushClient.ACCEPT_TIME_SEPARATOR);
            }
        }

        private void skipFieldMessage(Tokenizer tokenizer) throws ParseException {
            String delimiter;
            if (tokenizer.tryConsume(SimpleComparison.LESS_THAN_OPERATION)) {
                delimiter = SimpleComparison.GREATER_THAN_OPERATION;
            } else {
                tokenizer.consume("{");
                delimiter = "}";
            }
            while (!tokenizer.lookingAt(SimpleComparison.GREATER_THAN_OPERATION) && !tokenizer.lookingAt("}")) {
                skipField(tokenizer);
            }
            tokenizer.consume(delimiter);
        }

        private void skipFieldValue(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.tryConsumeString()) {
                do {
                } while (tokenizer.tryConsumeString());
            } else if (!tokenizer.tryConsumeIdentifier() && !tokenizer.tryConsumeInt64() && !tokenizer.tryConsumeUInt64() && !tokenizer.tryConsumeDouble() && !tokenizer.tryConsumeFloat()) {
                throw tokenizer.parseException("Invalid field value: " + tokenizer.currentToken);
            }
        }
    }

    private static final class Printer {
        boolean escapeNonAscii;
        boolean singleLineMode;

        private Printer() {
            this.singleLineMode = false;
            this.escapeNonAscii = true;
        }

        private Printer setSingleLineMode(boolean singleLineMode) {
            this.singleLineMode = singleLineMode;
            return this;
        }

        private Printer setEscapeNonAscii(boolean escapeNonAscii) {
            this.escapeNonAscii = escapeNonAscii;
            return this;
        }

        private void print(MessageOrBuilder message, TextGenerator generator) throws IOException {
            for (Entry<FieldDescriptor, Object> field : message.getAllFields().entrySet()) {
                printField((FieldDescriptor) field.getKey(), field.getValue(), generator);
            }
            printUnknownFields(message.getUnknownFields(), generator);
        }

        private void printField(FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            if (field.isRepeated()) {
                for (Object element : (List) value) {
                    printSingleField(field, element, generator);
                }
                return;
            }
            printSingleField(field, value, generator);
        }

        private void printSingleField(FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            if (field.isExtension()) {
                generator.print("[");
                if (field.getContainingType().getOptions().getMessageSetWireFormat() && field.getType() == Type.MESSAGE && field.isOptional() && field.getExtensionScope() == field.getMessageType()) {
                    generator.print(field.getMessageType().getFullName());
                } else {
                    generator.print(field.getFullName());
                }
                generator.print("]");
            } else if (field.getType() == Type.GROUP) {
                generator.print(field.getMessageType().getName());
            } else {
                generator.print(field.getName());
            }
            if (field.getJavaType() != JavaType.MESSAGE) {
                generator.print(": ");
            } else if (this.singleLineMode) {
                generator.print(" { ");
            } else {
                generator.print(" {\n");
                generator.indent();
            }
            printFieldValue(field, value, generator);
            if (field.getJavaType() == JavaType.MESSAGE) {
                if (this.singleLineMode) {
                    generator.print("} ");
                    return;
                }
                generator.outdent();
                generator.print("}\n");
            } else if (this.singleLineMode) {
                generator.print(" ");
            } else {
                generator.print(SpecilApiUtil.LINE_SEP);
            }
        }

        private void printFieldValue(FieldDescriptor field, Object value, TextGenerator generator) throws IOException {
            switch (field.getType()) {
                case INT32:
                case SINT32:
                case SFIXED32:
                    generator.print(((Integer) value).toString());
                    return;
                case INT64:
                case SINT64:
                case SFIXED64:
                    generator.print(((Long) value).toString());
                    return;
                case BOOL:
                    generator.print(((Boolean) value).toString());
                    return;
                case FLOAT:
                    generator.print(((Float) value).toString());
                    return;
                case DOUBLE:
                    generator.print(((Double) value).toString());
                    return;
                case UINT32:
                case FIXED32:
                    generator.print(TextFormat.unsignedToString(((Integer) value).intValue()));
                    return;
                case UINT64:
                case FIXED64:
                    generator.print(TextFormat.unsignedToString(((Long) value).longValue()));
                    return;
                case STRING:
                    generator.print("\"");
                    generator.print(this.escapeNonAscii ? TextFormat.escapeText((String) value) : TextFormat.escapeDoubleQuotesAndBackslashes((String) value));
                    generator.print("\"");
                    return;
                case BYTES:
                    generator.print("\"");
                    if (value instanceof ByteString) {
                        generator.print(TextFormat.escapeBytes((ByteString) value));
                    } else {
                        generator.print(TextFormat.escapeBytes((byte[]) value));
                    }
                    generator.print("\"");
                    return;
                case ENUM:
                    generator.print(((EnumValueDescriptor) value).getName());
                    return;
                case MESSAGE:
                case GROUP:
                    print((Message) value, generator);
                    return;
                default:
                    return;
            }
        }

        private void printUnknownFields(UnknownFieldSet unknownFields, TextGenerator generator) throws IOException {
            for (Entry<Integer, Field> entry : unknownFields.asMap().entrySet()) {
                int number = ((Integer) entry.getKey()).intValue();
                Field field = (Field) entry.getValue();
                printUnknownField(number, 0, field.getVarintList(), generator);
                printUnknownField(number, 5, field.getFixed32List(), generator);
                printUnknownField(number, 1, field.getFixed64List(), generator);
                printUnknownField(number, 2, field.getLengthDelimitedList(), generator);
                for (UnknownFieldSet value : field.getGroupList()) {
                    generator.print(((Integer) entry.getKey()).toString());
                    if (this.singleLineMode) {
                        generator.print(" { ");
                    } else {
                        generator.print(" {\n");
                        generator.indent();
                    }
                    printUnknownFields(value, generator);
                    if (this.singleLineMode) {
                        generator.print("} ");
                    } else {
                        generator.outdent();
                        generator.print("}\n");
                    }
                }
            }
        }

        private void printUnknownField(int number, int wireType, List<?> values, TextGenerator generator) throws IOException {
            for (Object value : values) {
                generator.print(String.valueOf(number));
                generator.print(": ");
                TextFormat.printUnknownFieldValue(wireType, value, generator);
                generator.print(this.singleLineMode ? " " : SpecilApiUtil.LINE_SEP);
            }
        }
    }

    private static final class TextGenerator {
        private boolean atStartOfLine;
        private final StringBuilder indent;
        private final Appendable output;

        private TextGenerator(Appendable output) {
            this.indent = new StringBuilder();
            this.atStartOfLine = true;
            this.output = output;
        }

        public void indent() {
            this.indent.append("  ");
        }

        public void outdent() {
            int length = this.indent.length();
            if (length == 0) {
                throw new IllegalArgumentException(" Outdent() without matching Indent().");
            }
            this.indent.delete(length - 2, length);
        }

        public void print(CharSequence text) throws IOException {
            int size = text.length();
            int pos = 0;
            for (int i = 0; i < size; i++) {
                if (text.charAt(i) == '\n') {
                    write(text.subSequence(pos, i + 1));
                    pos = i + 1;
                    this.atStartOfLine = true;
                }
            }
            write(text.subSequence(pos, size));
        }

        private void write(CharSequence data) throws IOException {
            if (data.length() != 0) {
                if (this.atStartOfLine) {
                    this.atStartOfLine = false;
                    this.output.append(this.indent);
                }
                this.output.append(data);
            }
        }
    }

    private static final class Tokenizer {
        private static final Pattern DOUBLE_INFINITY = Pattern.compile("-?inf(inity)?", 2);
        private static final Pattern FLOAT_INFINITY = Pattern.compile("-?inf(inity)?f?", 2);
        private static final Pattern FLOAT_NAN = Pattern.compile("nanf?", 2);
        private static final Pattern TOKEN = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_+-]*+|[.]?[0-9+-][0-9a-zA-Z_.+-]*+|\"([^\"\n\\\\]|\\\\.)*+(\"|\\\\?$)|'([^'\n\\\\]|\\\\.)*+('|\\\\?$)", 8);
        private static final Pattern WHITESPACE = Pattern.compile("(\\s|(#.*$))++", 8);
        private int column;
        private String currentToken;
        private int line;
        private final Matcher matcher;
        private int pos;
        private int previousColumn;
        private int previousLine;
        private final CharSequence text;

        private Tokenizer(CharSequence text) {
            this.pos = 0;
            this.line = 0;
            this.column = 0;
            this.previousLine = 0;
            this.previousColumn = 0;
            this.text = text;
            this.matcher = WHITESPACE.matcher(text);
            skipWhitespace();
            nextToken();
        }

        public boolean atEnd() {
            return this.currentToken.length() == 0;
        }

        public void nextToken() {
            this.previousLine = this.line;
            this.previousColumn = this.column;
            while (this.pos < this.matcher.regionStart()) {
                if (this.text.charAt(this.pos) == '\n') {
                    this.line++;
                    this.column = 0;
                } else {
                    this.column++;
                }
                this.pos++;
            }
            if (this.matcher.regionStart() == this.matcher.regionEnd()) {
                this.currentToken = "";
                return;
            }
            this.matcher.usePattern(TOKEN);
            if (this.matcher.lookingAt()) {
                this.currentToken = this.matcher.group();
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            } else {
                this.currentToken = String.valueOf(this.text.charAt(this.pos));
                this.matcher.region(this.pos + 1, this.matcher.regionEnd());
            }
            skipWhitespace();
        }

        private void skipWhitespace() {
            this.matcher.usePattern(WHITESPACE);
            if (this.matcher.lookingAt()) {
                this.matcher.region(this.matcher.end(), this.matcher.regionEnd());
            }
        }

        public boolean tryConsume(String token) {
            if (!this.currentToken.equals(token)) {
                return false;
            }
            nextToken();
            return true;
        }

        public void consume(String token) throws ParseException {
            if (!tryConsume(token)) {
                throw parseException("Expected \"" + token + "\".");
            }
        }

        public boolean lookingAtInteger() {
            if (this.currentToken.length() == 0) {
                return false;
            }
            char c = this.currentToken.charAt(0);
            if (('0' <= c && c <= '9') || c == '-' || c == '+') {
                return true;
            }
            return false;
        }

        public boolean lookingAt(String text) {
            return this.currentToken.equals(text);
        }

        public String consumeIdentifier() throws ParseException {
            for (int i = 0; i < this.currentToken.length(); i++) {
                char c = this.currentToken.charAt(i);
                if (('a' > c || c > 'z') && (('A' > c || c > 'Z') && !(('0' <= c && c <= '9') || c == '_' || c == '.'))) {
                    throw parseException("Expected identifier. Found '" + this.currentToken + "'");
                }
            }
            String result = this.currentToken;
            nextToken();
            return result;
        }

        public boolean tryConsumeIdentifier() {
            try {
                consumeIdentifier();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public int consumeInt32() throws ParseException {
            try {
                int result = TextFormat.parseInt32(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public int consumeUInt32() throws ParseException {
            try {
                int result = TextFormat.parseUInt32(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public long consumeInt64() throws ParseException {
            try {
                long result = TextFormat.parseInt64(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeInt64() {
            try {
                consumeInt64();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public long consumeUInt64() throws ParseException {
            try {
                long result = TextFormat.parseUInt64(this.currentToken);
                nextToken();
                return result;
            } catch (NumberFormatException e) {
                throw integerParseException(e);
            }
        }

        public boolean tryConsumeUInt64() {
            try {
                consumeUInt64();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public double consumeDouble() throws ParseException {
            if (DOUBLE_INFINITY.matcher(this.currentToken).matches()) {
                boolean negative = this.currentToken.startsWith("-");
                nextToken();
                return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else if (this.currentToken.equalsIgnoreCase("nan")) {
                nextToken();
                return Double.NaN;
            } else {
                try {
                    double result = Double.parseDouble(this.currentToken);
                    nextToken();
                    return result;
                } catch (NumberFormatException e) {
                    throw floatParseException(e);
                }
            }
        }

        public boolean tryConsumeDouble() {
            try {
                consumeDouble();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public float consumeFloat() throws ParseException {
            if (FLOAT_INFINITY.matcher(this.currentToken).matches()) {
                boolean negative = this.currentToken.startsWith("-");
                nextToken();
                return negative ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            } else if (FLOAT_NAN.matcher(this.currentToken).matches()) {
                nextToken();
                return Float.NaN;
            } else {
                try {
                    float result = Float.parseFloat(this.currentToken);
                    nextToken();
                    return result;
                } catch (NumberFormatException e) {
                    throw floatParseException(e);
                }
            }
        }

        public boolean tryConsumeFloat() {
            try {
                consumeFloat();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public boolean consumeBoolean() throws ParseException {
            if (this.currentToken.equals("true") || this.currentToken.equals("t") || this.currentToken.equals("1")) {
                nextToken();
                return true;
            } else if (this.currentToken.equals("false") || this.currentToken.equals("f") || this.currentToken.equals("0")) {
                nextToken();
                return false;
            } else {
                throw parseException("Expected \"true\" or \"false\".");
            }
        }

        public String consumeString() throws ParseException {
            return consumeByteString().toStringUtf8();
        }

        public boolean tryConsumeString() {
            try {
                consumeString();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        public ByteString consumeByteString() throws ParseException {
            Iterable list = new ArrayList();
            consumeByteString(list);
            while (true) {
                if (!this.currentToken.startsWith("'") && !this.currentToken.startsWith("\"")) {
                    return ByteString.copyFrom(list);
                }
                consumeByteString(list);
            }
        }

        private void consumeByteString(List<ByteString> list) throws ParseException {
            char quote = '\u0000';
            if (this.currentToken.length() > 0) {
                quote = this.currentToken.charAt(0);
            }
            if (quote != StringUtil.DOUBLE_QUOTE && quote != '\'') {
                throw parseException("Expected string.");
            } else if (this.currentToken.length() < 2 || this.currentToken.charAt(this.currentToken.length() - 1) != quote) {
                throw parseException("String missing ending quote.");
            } else {
                try {
                    ByteString result = TextFormat.unescapeBytes(this.currentToken.substring(1, this.currentToken.length() - 1));
                    nextToken();
                    list.add(result);
                } catch (InvalidEscapeSequenceException e) {
                    throw parseException(e.getMessage());
                }
            }
        }

        public ParseException parseException(String description) {
            return new ParseException(this.line + 1, this.column + 1, description);
        }

        public ParseException parseExceptionPreviousToken(String description) {
            return new ParseException(this.previousLine + 1, this.previousColumn + 1, description);
        }

        private ParseException integerParseException(NumberFormatException e) {
            return parseException("Couldn't parse integer: " + e.getMessage());
        }

        private ParseException floatParseException(NumberFormatException e) {
            return parseException("Couldn't parse number: " + e.getMessage());
        }
    }

    private TextFormat() {
    }

    public static void print(MessageOrBuilder message, Appendable output) throws IOException {
        DEFAULT_PRINTER.print(message, new TextGenerator(output));
    }

    public static void print(UnknownFieldSet fields, Appendable output) throws IOException {
        DEFAULT_PRINTER.printUnknownFields(fields, new TextGenerator(output));
    }

    public static void printUnicode(MessageOrBuilder message, Appendable output) throws IOException {
        UNICODE_PRINTER.print(message, new TextGenerator(output));
    }

    public static void printUnicode(UnknownFieldSet fields, Appendable output) throws IOException {
        UNICODE_PRINTER.printUnknownFields(fields, new TextGenerator(output));
    }

    public static String shortDebugString(MessageOrBuilder message) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.print(message, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String shortDebugString(UnknownFieldSet fields) {
        try {
            StringBuilder sb = new StringBuilder();
            SINGLE_LINE_PRINTER.printUnknownFields(fields, new TextGenerator(sb));
            return sb.toString().trim();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(MessageOrBuilder message) {
        try {
            Appendable text = new StringBuilder();
            print(message, text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToString(UnknownFieldSet fields) {
        try {
            Appendable text = new StringBuilder();
            print(fields, text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(MessageOrBuilder message) {
        try {
            StringBuilder text = new StringBuilder();
            UNICODE_PRINTER.print(message, new TextGenerator(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String printToUnicodeString(UnknownFieldSet fields) {
        try {
            StringBuilder text = new StringBuilder();
            UNICODE_PRINTER.printUnknownFields(fields, new TextGenerator(text));
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printField(FieldDescriptor field, Object value, Appendable output) throws IOException {
        DEFAULT_PRINTER.printField(field, value, new TextGenerator(output));
    }

    public static String printFieldToString(FieldDescriptor field, Object value) {
        try {
            StringBuilder text = new StringBuilder();
            printField(field, value, text);
            return text.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void printFieldValue(FieldDescriptor field, Object value, Appendable output) throws IOException {
        DEFAULT_PRINTER.printFieldValue(field, value, new TextGenerator(output));
    }

    public static void printUnknownFieldValue(int tag, Object value, Appendable output) throws IOException {
        printUnknownFieldValue(tag, value, new TextGenerator(output));
    }

    private static void printUnknownFieldValue(int tag, Object value, TextGenerator generator) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                generator.print(unsignedToString(((Long) value).longValue()));
                return;
            case 1:
                generator.print(String.format((Locale) null, "0x%016x", new Object[]{(Long) value}));
                return;
            case 2:
                generator.print("\"");
                generator.print(escapeBytes((ByteString) value));
                generator.print("\"");
                return;
            case 3:
                DEFAULT_PRINTER.printUnknownFields((UnknownFieldSet) value, generator);
                return;
            case 5:
                generator.print(String.format((Locale) null, "0x%08x", new Object[]{(Integer) value}));
                return;
            default:
                throw new IllegalArgumentException("Bad tag: " + tag);
        }
    }

    public static String unsignedToString(int value) {
        if (value >= 0) {
            return Integer.toString(value);
        }
        return Long.toString(((long) value) & 4294967295L);
    }

    public static String unsignedToString(long value) {
        if (value >= 0) {
            return Long.toString(value);
        }
        return BigInteger.valueOf(Long.MAX_VALUE & value).setBit(63).toString();
    }

    public static Parser getParser() {
        return PARSER;
    }

    public static void merge(Readable input, Builder builder) throws IOException {
        PARSER.merge(input, builder);
    }

    public static void merge(CharSequence input, Builder builder) throws ParseException {
        PARSER.merge(input, builder);
    }

    public static void merge(Readable input, ExtensionRegistry extensionRegistry, Builder builder) throws IOException {
        PARSER.merge(input, extensionRegistry, builder);
    }

    public static void merge(CharSequence input, ExtensionRegistry extensionRegistry, Builder builder) throws ParseException {
        PARSER.merge(input, extensionRegistry, builder);
    }

    private static String escapeBytes(ByteSequence input) {
        StringBuilder builder = new StringBuilder(input.size());
        for (int i = 0; i < input.size(); i++) {
            byte b = input.byteAt(i);
            switch (b) {
                case (byte) 7:
                    builder.append("\\a");
                    break;
                case (byte) 8:
                    builder.append("\\b");
                    break;
                case (byte) 9:
                    builder.append("\\t");
                    break;
                case (byte) 10:
                    builder.append("\\n");
                    break;
                case (byte) 11:
                    builder.append("\\v");
                    break;
                case (byte) 12:
                    builder.append("\\f");
                    break;
                case (byte) 13:
                    builder.append("\\r");
                    break;
                case (byte) 34:
                    builder.append("\\\"");
                    break;
                case (byte) 39:
                    builder.append("\\'");
                    break;
                case (byte) 92:
                    builder.append("\\\\");
                    break;
                default:
                    if (b < (byte) 32) {
                        builder.append('\\');
                        builder.append((char) (((b >>> 6) & 3) + 48));
                        builder.append((char) (((b >>> 3) & 7) + 48));
                        builder.append((char) ((b & 7) + 48));
                        break;
                    }
                    builder.append((char) b);
                    break;
            }
        }
        return builder.toString();
    }

    static String escapeBytes(final ByteString input) {
        return escapeBytes(new ByteSequence() {
            public int size() {
                return input.size();
            }

            public byte byteAt(int offset) {
                return input.byteAt(offset);
            }
        });
    }

    static String escapeBytes(final byte[] input) {
        return escapeBytes(new ByteSequence() {
            public int size() {
                return input.length;
            }

            public byte byteAt(int offset) {
                return input[offset];
            }
        });
    }

    static ByteString unescapeBytes(CharSequence charString) throws InvalidEscapeSequenceException {
        ByteString input = ByteString.copyFromUtf8(charString.toString());
        byte[] result = new byte[input.size()];
        int pos = 0;
        int i = 0;
        while (i < input.size()) {
            byte c = input.byteAt(i);
            int pos2;
            if (c != (byte) 92) {
                pos2 = pos + 1;
                result[pos] = c;
                pos = pos2;
            } else if (i + 1 < input.size()) {
                i++;
                c = input.byteAt(i);
                int code;
                if (isOctal(c)) {
                    code = digitValue(c);
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        i++;
                        code = (code * 8) + digitValue(input.byteAt(i));
                    }
                    if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
                        i++;
                        code = (code * 8) + digitValue(input.byteAt(i));
                    }
                    pos2 = pos + 1;
                    result[pos] = (byte) code;
                    pos = pos2;
                } else {
                    switch (c) {
                        case (byte) 34:
                            pos2 = pos + 1;
                            result[pos] = (byte) 34;
                            pos = pos2;
                            break;
                        case (byte) 39:
                            pos2 = pos + 1;
                            result[pos] = (byte) 39;
                            pos = pos2;
                            break;
                        case (byte) 92:
                            pos2 = pos + 1;
                            result[pos] = (byte) 92;
                            pos = pos2;
                            break;
                        case (byte) 97:
                            pos2 = pos + 1;
                            result[pos] = (byte) 7;
                            pos = pos2;
                            break;
                        case (byte) 98:
                            pos2 = pos + 1;
                            result[pos] = (byte) 8;
                            pos = pos2;
                            break;
                        case (byte) 102:
                            pos2 = pos + 1;
                            result[pos] = (byte) 12;
                            pos = pos2;
                            break;
                        case (byte) 110:
                            pos2 = pos + 1;
                            result[pos] = (byte) 10;
                            pos = pos2;
                            break;
                        case (byte) 114:
                            pos2 = pos + 1;
                            result[pos] = (byte) 13;
                            pos = pos2;
                            break;
                        case (byte) 116:
                            pos2 = pos + 1;
                            result[pos] = (byte) 9;
                            pos = pos2;
                            break;
                        case (byte) 118:
                            pos2 = pos + 1;
                            result[pos] = (byte) 11;
                            pos = pos2;
                            break;
                        case (byte) 120:
                            if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                i++;
                                code = digitValue(input.byteAt(i));
                                if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                                    i++;
                                    code = (code * 16) + digitValue(input.byteAt(i));
                                }
                                pos2 = pos + 1;
                                result[pos] = (byte) code;
                                pos = pos2;
                                break;
                            }
                            throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
                        default:
                            throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\" + ((char) c) + '\'');
                    }
                }
            } else {
                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
            }
            i++;
        }
        return ByteString.copyFrom(result, 0, pos);
    }

    static String escapeText(String input) {
        return escapeBytes(ByteString.copyFromUtf8(input));
    }

    public static String escapeDoubleQuotesAndBackslashes(String input) {
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    static String unescapeText(String input) throws InvalidEscapeSequenceException {
        return unescapeBytes(input).toStringUtf8();
    }

    private static boolean isOctal(byte c) {
        return TarConstants.LF_NORMAL <= c && c <= TarConstants.LF_CONTIG;
    }

    private static boolean isHex(byte c) {
        return (TarConstants.LF_NORMAL <= c && c <= (byte) 57) || (((byte) 97 <= c && c <= (byte) 102) || ((byte) 65 <= c && c <= (byte) 70));
    }

    private static int digitValue(byte c) {
        if (TarConstants.LF_NORMAL <= c && c <= (byte) 57) {
            return c - 48;
        }
        if ((byte) 97 > c || c > (byte) 122) {
            return (c - 65) + 10;
        }
        return (c - 97) + 10;
    }

    static int parseInt32(String text) throws NumberFormatException {
        return (int) parseInteger(text, true, false);
    }

    static int parseUInt32(String text) throws NumberFormatException {
        return (int) parseInteger(text, false, false);
    }

    static long parseInt64(String text) throws NumberFormatException {
        return parseInteger(text, true, true);
    }

    static long parseUInt64(String text) throws NumberFormatException {
        return parseInteger(text, false, true);
    }

    private static long parseInteger(String text, boolean isSigned, boolean isLong) throws NumberFormatException {
        int pos = 0;
        boolean negative = false;
        if (text.startsWith("-", 0)) {
            if (isSigned) {
                pos = 0 + 1;
                negative = true;
            } else {
                throw new NumberFormatException("Number must be positive: " + text);
            }
        }
        int radix = 10;
        if (text.startsWith("0x", pos)) {
            pos += 2;
            radix = 16;
        } else if (text.startsWith("0", pos)) {
            radix = 8;
        }
        String numberText = text.substring(pos);
        if (numberText.length() < 16) {
            long result = Long.parseLong(numberText, radix);
            if (negative) {
                result = -result;
            }
            if (isLong) {
                return result;
            }
            if (isSigned) {
                if (result <= 2147483647L && result >= -2147483648L) {
                    return result;
                }
                throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
            } else if (result < 4294967296L && result >= 0) {
                return result;
            } else {
                throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
            }
        }
        BigInteger bigValue = new BigInteger(numberText, radix);
        if (negative) {
            bigValue = bigValue.negate();
        }
        if (isLong) {
            if (isSigned) {
                if (bigValue.bitLength() > 63) {
                    throw new NumberFormatException("Number out of range for 64-bit signed integer: " + text);
                }
            } else if (bigValue.bitLength() > 64) {
                throw new NumberFormatException("Number out of range for 64-bit unsigned integer: " + text);
            }
        } else if (isSigned) {
            if (bigValue.bitLength() > 31) {
                throw new NumberFormatException("Number out of range for 32-bit signed integer: " + text);
            }
        } else if (bigValue.bitLength() > 32) {
            throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + text);
        }
        return bigValue.longValue();
    }
}
