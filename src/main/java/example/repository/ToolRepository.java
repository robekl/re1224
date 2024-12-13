package example.repository;

import example.domain.Tool;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static example.utils.Constants.*;

/**
 * The ToolRepository class provides a repository for managing tools
 * by their unique tool codes. The repository stores tool details such
 * as code, type, and brand, and allows retrieval and listing of tool data.
 *
 * As part of the exercise, tools are initialized as a static collection,
 * making them accessible across instances of the repository. Looking forward,
 * this can be moved to a database with minimal changes in other classes.
 */
public class ToolRepository {
    private static final Map<String, Tool> tools = Stream.of(
                    Tool.builder().code("CHNS").type(CHAINSAW).brand("Stihl").build(),
                    Tool.builder().code("LADW").type(LADDER).brand("Werner").build(),
                    Tool.builder().code("JAKD").type(JACKHAMMER).brand("DeWalt").build(),
                    Tool.builder().code("JAKR").type(JACKHAMMER).brand("Ridgid").build()
            )
            .collect(Collectors.toMap(Tool::getCode, Function.identity()));

    public Tool getTool(String toolCode) {
        return tools.get(toolCode);
    }

    public String getAllToolCodes() {
        return String.join(",", tools.keySet());
    }
}
