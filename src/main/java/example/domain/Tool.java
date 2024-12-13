package example.domain;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a tool with specific properties such as code, type, and brand.
 * This class is used to identify and categorize tools in the system.
 */
@Data
@Builder
public class Tool {
    private String code;

    private String type;

    private String brand;
}
