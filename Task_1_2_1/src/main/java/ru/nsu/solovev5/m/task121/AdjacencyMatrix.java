package ru.nsu.solovev5.m.task121;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import ru.nsu.solovev5.m.task121.graphs.Edge;
import ru.nsu.solovev5.m.task121.graphs.Graph;
import ru.nsu.solovev5.m.task121.graphs.Vertex;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.DuplicateVertexException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchEdgeException;
import ru.nsu.solovev5.m.task121.graphs.exceptions.NoSuchVertexException;
import ru.nsu.solovev5.m.task121.matrix.Matrix;

/**
 * Represents a graph using an adjacency matrix.
 */
public class AdjacencyMatrix implements Graph {
    private final ArrayList<Vertex> vertices;
    private final Matrix<Boolean> matrix;

    /**
     * Constructs a new AdjacencyMatrix.
     */
    public AdjacencyMatrix() {
        vertices = new ArrayList<>();
        matrix = new Matrix<>();
    }

    @Override
    public void add(Vertex vertex) {
        if (vertices.contains(vertex)) {
            throw new DuplicateVertexException(vertex);
        }

        vertices.add(vertex);
        matrix.addRow();
        matrix.addColumn();
    }

    @Override
    public void add(Edge edge) {
        var from = edge.from();
        if (!vertices.contains(from)) {
            add(from);
        }

        var to = edge.to();
        if (!vertices.contains(to)) {
            add(to);
        }

        var fromIndex = vertices.indexOf(from);
        var toIndex = vertices.indexOf(to);

        var matrixItem = matrix.get(fromIndex, toIndex);
        if (matrixItem != null && matrixItem) {
            throw new DuplicateEdgeException(edge);
        }

        matrix.set(fromIndex, toIndex, true);
    }

    @Override
    public void delete(Vertex vertex) {
        var index = vertices.indexOf(vertex);
        if (index == -1) {
            throw new NoSuchVertexException(vertex);
        }

        vertices.remove(index);
        matrix.removeRow(index);
        matrix.removeColumn(index);
    }

    @Override
    public void delete(Edge edge) {
        var from = edge.from();
        if (!vertices.contains(from)) {
            throw new NoSuchVertexException(from);
        }

        var to = edge.to();
        if (!vertices.contains(to)) {
            throw new NoSuchVertexException(to);
        }

        var fromIndex = vertices.indexOf(from);
        var toIndex = vertices.indexOf(to);

        var item = matrix.get(fromIndex, toIndex);
        if (item == null || !item) {
            throw new NoSuchEdgeException(edge);
        }

        matrix.set(fromIndex, toIndex, false);
    }

    @Override
    public Vertex[] getNeighbours(Vertex vertex) {
        var fromIndex = vertices.indexOf(vertex);
        if (fromIndex == -1) {
            throw new NoSuchVertexException(vertex);
        }

        var neighbours = new ArrayList<Vertex>();

        for (var toIndex = 0; toIndex < matrix.columnsNumber(); toIndex++) {
            var matrixItem = matrix.get(fromIndex, toIndex);
            if (matrixItem != null && matrixItem) {
                neighbours.add(vertices.get(toIndex));
            }
        }

        return neighbours.toArray(new Vertex[0]);
    }

    @Override
    public Vertex[] getVertices() {
        return vertices.toArray(new Vertex[0]);
    }

    @Override
    public boolean has(Edge edge) {
        var fromIndex = vertices.indexOf(edge.from());
        var toIndex = vertices.indexOf(edge.to());
        if (fromIndex == -1 || toIndex == -1) {
            return false;
        }

        var item = matrix.get(fromIndex, toIndex);
        return item != null && item;
    }

    @Override
    public boolean has(Vertex vertex) {
        return vertices.contains(vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(getVertices()), matrix);
    }

    @Override
    public boolean equals(Object o) {
        return equalsTo(o);
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();
        builder.append('\t');
        for (var vertex : vertices) {
            builder.append(vertex).append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);

        for (var i = 0; i < vertices.size(); i++) {
            builder.append('\n').append(vertices.get(i)).append('\t');
            for (var j = 0; j < vertices.size(); j++) {
                var matrixItem = matrix.get(i, j);
                if (matrixItem == null || !matrixItem) {
                    builder.append(0);
                } else {
                    builder.append(1);
                }

                builder.append(' ');
            }
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
