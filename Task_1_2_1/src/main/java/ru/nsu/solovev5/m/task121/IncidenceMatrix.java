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
 * Represents a graph using an incidence matrix.
 */
public class IncidenceMatrix implements Graph {
    private final int EDGE_START = 1;
    private final int EDGE_END = 2;
    private final ArrayList<Vertex> vertices;
    private final Matrix<Integer> matrix;

    public IncidenceMatrix() {
        vertices = new ArrayList<>();
        matrix = new Matrix<>();
    }

    @Override
    public void add(Vertex vertex) {
        if (vertices.contains(vertex)) {
            throw new DuplicateVertexException(vertex);
        }

        vertices.add(vertex);
        matrix.addColumn();
    }

    @Override
    public void delete(Vertex vertex) {
        var vertexIndex = vertices.indexOf(vertex);
        if (vertexIndex == -1) {
            throw new NoSuchVertexException(vertex);
        }

        vertices.remove(vertexIndex);
        for (var i = 0; i < matrix.rowsNumber(); i++) {
            if (matrix.get(i, vertexIndex) != null) {
                matrix.removeRow(i--);
            }
        }

        matrix.removeColumn(vertexIndex);
    }

    @Override
    public boolean has(Vertex vertex) {
        return vertices.contains(vertex);
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

        for (var i = 0; i < matrix.rowsNumber(); i++) {
            var start = matrix.get(i, fromIndex);
            var end = matrix.get(i, toIndex);
            if (start != null && start == EDGE_START
                && end != null && end == EDGE_END) {
                throw new DuplicateEdgeException(edge);
            }
        }

        var row = matrix.rowsNumber();
        matrix.addRow();
        matrix.set(row, fromIndex, EDGE_START);
        matrix.set(row, toIndex, EDGE_END);
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


        for (var i = 0; i < matrix.rowsNumber(); i++) {
            var start = matrix.get(i, fromIndex);
            var end = matrix.get(i, toIndex);
            if (start != null && start == EDGE_START
                && end != null && end == EDGE_END) {
                matrix.removeRow(i);
                return;
            }
        }

        throw new NoSuchEdgeException(edge);
    }

    @Override
    public boolean has(Edge edge) {
        if (!has(edge.from()) || !has(edge.to())) {
            return false;
        }
        var fromIndex = vertices.indexOf(edge.from());
        var toIndex = vertices.indexOf(edge.to());

        for (var i = 0; i < matrix.rowsNumber(); i++) {
            var start = matrix.get(i, fromIndex);
            var end = matrix.get(i, toIndex);
            if (start != null && start == EDGE_START
                && end != null && end == EDGE_END) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Vertex[] getVertices() {
        return vertices.toArray(new Vertex[0]);
    }

    @Override
    public Vertex[] getNeighbours(Vertex vertex) {
        var fromIndex = vertices.indexOf(vertex);
        if (fromIndex == -1) {
            throw new NoSuchVertexException(vertex);
        }

        var neighbours = new ArrayList<Vertex>();

        for (var row = 0; row < matrix.rowsNumber(); row++) {
            var start = matrix.get(row, fromIndex);
            if (start == null || start != EDGE_START) {
                continue;
            }
            for (var toIndex = 0; toIndex < matrix.columnsNumber(); toIndex++) {
                if (toIndex == fromIndex) {
                    continue;
                }
                var end = matrix.get(row, toIndex);
                if (end != null && end == EDGE_END) {
                    neighbours.add(vertices.get(toIndex));
                    break;
                }
            }
        }

        return neighbours.toArray(new Vertex[0]);
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();
        builder.append('\t');
        for (var vertex : vertices) {
            builder.append(vertex).append(' ');
        }
        builder.deleteCharAt(builder.length() - 1);

        for (var i = 0; i < matrix.rowsNumber(); i++) {
            builder.append('\n').append(i + 1).append('\t');
            for (var j = 0; j < matrix.columnsNumber(); j++) {
                var matrixItem = matrix.get(i, j);
                if (matrixItem == null) {
                    builder.append(0);
                } else {
                    builder.append(matrixItem);
                }

                builder.append(' ');
            }
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        return equalsTo(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(getVertices()), matrix);
    }
}
