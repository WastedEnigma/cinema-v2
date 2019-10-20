package database;

import cinema.people.Visitor;

import java.io.*;
import java.util.*;

public class VisitorDataBase implements Serializable {

    private static long serialVersionUID = 1L;
    private static VisitorDataBase db;

    private List<Visitor> visitors;

    private VisitorDataBase() {
        deserialize();
    }

    public static VisitorDataBase getInstance() {
        if (db == null)
            db = new VisitorDataBase();

        return db;
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    public Visitor save(Visitor visitor) {
        if (!visitors.contains(visitor)) {
            visitors.add(visitor);
            serialize();
            deserialize();
        }

        return visitor;
    }

    public void serialize() {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("vdb.out"))
        ) {
            objectOutputStream.writeObject(visitors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deserialize() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("vdb.out")
        )) {
            visitors = (List<Visitor>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            visitors = new ArrayList<>();
            serialize();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() {
        serialize();
    }
}
