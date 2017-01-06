package br.com.rg.gabrielsalles.leukogramapp.otherFiles;

/**
 * Created by gabriel on 04/11/16.
 */

public class Exam {

    private String patiendId;
    private String examDate;
    private String firstName;
    private String lastName;
    private String sex;
    private String birthday;
    private String cell0;
    private String cell1;
    private String cell2;
    private String cell3;
    private String cell4;
    private String cell5;
    private String cell6;
    private String cell7;
    private String cell8;
    private String cell9;
    private String cell10;
    private String cell11;
    private String totalCells;
    private boolean checked;

    public Exam(String patiendId, String examDate, String firstName, String lastName, String sex, String birthday, String cell0, String cell1, String cell2, String cell3, String cell4, String cell5, String cell6, String cell7, String cell8, String cell9, String cell10, String cell11, String totalCells) {
        this.patiendId = patiendId;
        this.examDate = examDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthday = birthday;
        this.cell0 = cell0;
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.cell3 = cell3;
        this.cell4 = cell4;
        this.cell5 = cell5;
        this.cell6 = cell6;
        this.cell7 = cell7;
        this.cell8 = cell8;
        this.cell9 = cell9;
        this.cell10 = cell10;
        this.cell11 = cell11;
        this.totalCells = totalCells;
        this.checked = false;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {

        return checked;
    }

    public String getPatiendId() {
        return patiendId;
    }

    public String getExamDate() {
        return examDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCell0() {
        return cell0;
    }

    public String getCell1() {
        return cell1;
    }

    public String getCell2() {
        return cell2;
    }

    public String getCell3() {
        return cell3;
    }

    public String getCell4() {
        return cell4;
    }

    public String getCell5() {
        return cell5;
    }

    public String getCell6() {
        return cell6;
    }

    public String getCell7() {
        return cell7;
    }

    public String getCell8() {
        return cell8;
    }

    public String getCell9() {
        return cell9;
    }

    public String getCell10() {
        return cell10;
    }

    public String getCell11() {
        return cell11;
    }

    public String getTotalCells() {
        return totalCells;
    }

    public Double getDoubleTotalCells() {
        return Double.parseDouble(this.getTotalCells());
    }

    public Double getCell(int i) {
        switch (i) {
            case 0:
                return Double.parseDouble(this.getCell0());
            case 1:
                return Double.parseDouble(this.getCell1());
            case 2:
                return Double.parseDouble(this.getCell2());
            case 3:
                return Double.parseDouble(this.getCell3());
            case 4:
                return Double.parseDouble(this.getCell4());
            case 5:
                return Double.parseDouble(this.getCell5());
            case 6:
                return Double.parseDouble(this.getCell6());
            case 7:
                return Double.parseDouble(this.getCell7());
            case 8:
                return Double.parseDouble(this.getCell8());
            case 9:
                return Double.parseDouble(this.getCell9());
            case 10:
                return Double.parseDouble(this.getCell10());
            case 11:
                return Double.parseDouble(this.getCell11());
            default:
                return 0.0;
        }
    }
}
