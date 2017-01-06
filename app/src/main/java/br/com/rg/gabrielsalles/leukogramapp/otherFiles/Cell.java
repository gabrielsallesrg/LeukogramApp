package br.com.rg.gabrielsalles.leukogramapp.otherFiles;

/**
 * Created by gabriel on 17/10/16.
 */

public class Cell {

    private int id;
    private String name;
    private int count;

    public Cell(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishableName() {
        return this.getName() + "\n" + this.getCount();
    }

    public int getCount() {
        return count;
    }

    public void addOne() {
        this.count++;
    }

    public void minusOne() {
        this.count--;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
