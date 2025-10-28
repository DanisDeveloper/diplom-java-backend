package danis.galimullin.diplomback.exception;

public class ShaderNotFoundException extends RuntimeException {
    public ShaderNotFoundException() {
        super("SHADER_NOT_FOUND");
    }
}

