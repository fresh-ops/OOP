package graphs;

/**
 * Represents a graph vertex.
 *
 * @param name a vertex name
 */
public record Vertex(String name) {
    @Override
    public String toString() {
        return name;
    }
}
