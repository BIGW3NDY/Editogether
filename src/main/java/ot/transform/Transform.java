package ot.transform;

import ot.operation.Change;
import ot.operation.Delete;
import ot.operation.Insert;
import ot.operation.Operation;

public class Transform {
    public static void transform(Operation operation1, Operation operation2){
        if(operation1.getIndex()>operation2.getIndex()){
            Operation op = operation1;
            operation1 = operation2;
            operation2 = op;
        }
        if(operation1.getChange() instanceof Insert){
            if(operation2.getChange() instanceof Insert){
                transform((Insert)operation1.getChange(), (Insert)operation2.getChange());
            }else if(operation2.getChange() instanceof Delete){
                transform((Insert)operation1.getChange(), (Delete)operation2.getChange());
            }
        } else if(operation1.getChange() instanceof  Delete){
            if(operation2.getChange() instanceof  Insert){
                transform((Delete)operation1.getChange(), (Insert)operation2.getChange());
            } else if(operation2.getChange() instanceof  Delete){
                transform((Delete)operation1.getChange(), (Delete)operation2.getChange());
            }

        }
    }

    public static void transform(Insert insert1, Insert insert2){
        if(insert1.getOffset()>=insert2.getOffset()){
            insert1.setOffset(insert1.getOffset()+insert2.getNewContent().length());
        }
    }

    public static void transform(Insert insert1, Delete delete1){
        if(insert1.getOffset() > delete1.getOffset() && insert1.getOffset() < delete1.getOffset()+delete1.getLength()){
            insert1.setOffset(delete1.getOffset());
        } else if(insert1.getOffset() > delete1.getOffset()){
            insert1.setOffset(insert1.getOffset()-delete1.getLength());
        }
    }

    public static void transform(Delete delete1, Insert insert1){
        if(delete1.getOffset() >= insert1.getOffset()){
            delete1.setOffset(delete1.getOffset() + insert1.getNewContent().length());
        }
    }

    public static void transform(Delete delete1, Delete delete2){
        if(delete1.getOffset()>delete2.getOffset()){
            delete1.setOffset(delete1.getOffset() - delete2.getLength());
        }
    }
}
