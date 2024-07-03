public class Student {
    private int id;
    private String name;
    private String gender;
    private String idcard;
    private int old;

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Override
    public String toString() {
        return "Student{" +'\n'+
                "id='" + id  +
                " name='" + name  +
                " gender='" + gender +
                " idcard='" + idcard +
                " old='" + old +
                '}'+'\n';
    }
}

