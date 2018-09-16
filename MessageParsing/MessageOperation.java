package MessageParsing;

/**
 * Created by renim on 16/09/2018.
 * Enum containing the current supported message operations
 */
enum MessageOperation {

    ADD("add"),
    SUBTRACT("subtract"),
    MULTIPLY("multiply");

    private final String type;

    MessageOperation(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
