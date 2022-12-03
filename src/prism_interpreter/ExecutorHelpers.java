package prism_interpreter;

import prism.*;

import java.util.*;
import java.io.*;

public class ExecutorHelpers {
        static Expression getAtomExpressionFromAtomType(AtomType atom_type, String exception_str) throws Exception {
                if (atom_type instanceof IntegerType) {
                        return new IntegerAtomExpression(((IntegerType) atom_type).getValue());
                } else if (atom_type instanceof BooleanType) {
                        return new BooleanAtomExpression(((BooleanType) atom_type).getValue());
                } else if (atom_type instanceof StringType) {
                        return new StringAtomExpression(((StringType) atom_type).getValue());
                } else if (atom_type instanceof VoidType) {
                        return new VoidAtomExpression();
                } else if (atom_type instanceof ObjectType) {
                        ObjectType obj_type = (ObjectType) atom_type;
                        return new ObjectAtomExpression(obj_type.getClassName(), obj_type.getAttributes());
                } else if (atom_type instanceof ArrayType) {
                        ArrayType array_type = (ArrayType) atom_type;
                        ArrayAtomExpression returner = new ArrayAtomExpression(array_type.getType());
                        returner.setInternalArray(array_type.getInternalArray());
                        return returner;
                } else {
                        throw new RuntimeException(exception_str);
                }
        }

        static AtomType getAtomTypeFromAtomExpression(Expression atom_expr, String exception_str) throws Exception {
                if (atom_expr instanceof IntegerAtomExpression) {
                        return new IntegerType(((IntegerAtomExpression) atom_expr).getValue());
                } else if (atom_expr instanceof BooleanAtomExpression) {
                        return new BooleanType(((BooleanAtomExpression) atom_expr).getValue());
                } else if (atom_expr instanceof StringAtomExpression) {
                        return new StringType(((StringAtomExpression) atom_expr).getValue());
                } else if (atom_expr instanceof VoidAtomExpression) {
                        return new VoidType();
                } else if (atom_expr instanceof ObjectAtomExpression) {
                        ObjectAtomExpression obj_type = (ObjectAtomExpression) atom_expr;
                        return new ObjectType(obj_type.getClassName(), obj_type.getAttributes());
                } else if (atom_expr instanceof ArrayAtomExpression) {
                        ArrayAtomExpression array_type = (ArrayAtomExpression) atom_expr;
                        ArrayType returner = new ArrayType(array_type.getType());
                        returner.setInternalArray(array_type.getInternalArray());
                        return returner;
                } else {
                        throw new RuntimeException(exception_str);
                }
        }

        @SuppressWarnings("unchecked")
        static Map<String, AtomType> deepCopyClassAttributes(Map<String, AtomType> object) throws Exception {
                ByteArrayOutputStream byte_array_output_stream = new ByteArrayOutputStream();
                ObjectOutputStream object_output_stream = new ObjectOutputStream(byte_array_output_stream);
                object_output_stream.writeObject(object);
                ByteArrayInputStream byte_array_input_stream = new ByteArrayInputStream(
                                byte_array_output_stream.toByteArray());
                ObjectInputStream object_input_stream = new ObjectInputStream(byte_array_input_stream);
                return (Map<String, AtomType>) object_input_stream.readObject();
        }

}
