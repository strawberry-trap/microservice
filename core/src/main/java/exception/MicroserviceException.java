package exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MicroserviceException extends Exception {

    // ID for serialization, de-serialization
    private static final long serialVersionUID = 1L;

    public MicroserviceException(String message) {
        super(message);
    }
}
