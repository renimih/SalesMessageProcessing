package MessageParsing;

/**
 * Created by renim on 16/09/2018.
 * Enum containing the current supported message types
 */
public enum MessageType {

    SINGLE("single"),
    MULTIPLE("multiple"),
    ADJUSTMENT("adjustment");

    private final String type;

    MessageType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
