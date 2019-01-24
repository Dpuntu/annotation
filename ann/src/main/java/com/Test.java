package com;

import com.ann.Autowired;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import jdk.internal.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: fangmingxing
 * @Date: 2019-01-21 17:14
 */
public class Test {

    @Autowired
    private Person person;

    public void student() {
//        Person person = new Student();
        person.eat(null);
        person.run(null);
        person.sing(null);
        person.work(null);
    }


    public void asm() {
        try {
            ClassReader classReader = new ClassReader(new FileInputStream("target/classes/com/People.class"));
//            ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5, new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)) {
//                @Override
//                public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
//                    System.out.println("属性 : " + name);
//                    return super.visitField(access, name, desc, signature, value);
//                }
//
//                @Override
//                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//                    System.out.println("方法 : " + name);
//                    return super.visitMethod(access, name, desc, signature, exceptions);
//                }
//
//                @Override
//                public AnnotationVisitor visitAnnotation(String name, boolean b) {
//                    System.out.println("注解 : " + name);
//                    return super.visitAnnotation(name, b);
//                }
//
//            };
//            classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);

            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, ClassReader.SKIP_DEBUG);
            System.out.println("Class Name: " + classNode.name);
            AnnotationNode anNode = null;
            if (classNode.invisibleAnnotations.size() == 1) {
                anNode = classNode.invisibleAnnotations.get(0);
                System.out.println("Annotation Descriptor : " + anNode.desc);
                System.out.println("Annotation attribute pairs : " + anNode.values);
            }

            File file = new File("target/classes/com/People.class");
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(copyFromBytecode(anNode == null ? 0 : (int) anNode.values.get(1)));

        } catch (IOException e) {
            e.printStackTrace();
        }

        People people = new People();
        System.out.println("people : " + people.size);
    }

    private byte[] copyFromBytecode(int value) {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(52, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "com/People", null, "java/lang/Object", null);

        cw.visitSource("People.java", null);

        {
            av0 = cw.visitAnnotation("Lcom.ann.Prinln1;", false);
            av0.visit("value", new Integer(12));
            av0.visitEnd();
        }
        {
            fv = cw.visitField(0, "size", "I", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(0, "phone", "D", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(0, "sex", "Ljava/lang/Boolean;", null, null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(0, "name", "Ljava/lang/String;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(10, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(12, l1);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitIntInsn(Opcodes.BIPUSH, value);
            mv.visitFieldInsn(Opcodes.PUTFIELD, "com/People", "size", "I");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(14, l2);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn(new Double("12.0"));
            mv.visitFieldInsn(Opcodes.PUTFIELD, "com/People", "phone", "D");
            mv.visitInsn(Opcodes.RETURN);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lcom/People;", null, l0, l3, 0);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
