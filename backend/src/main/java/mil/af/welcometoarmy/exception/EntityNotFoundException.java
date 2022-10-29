package mil.af.welcometoarmy.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1215236468162067230L;

    private final String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }
}
