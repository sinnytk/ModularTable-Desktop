public class Slot
{
    private String _teacher;
    private String _course;
    private String _class;



    public Slot(String _course, String _teacher, String _class)
    {
        this._teacher = _teacher;
        this._course = _course;
        this._class = _class;
    }
    public Slot()
    {
        _teacher = "";
        _class = "";
        _course = "";
    }
    public void print()
    {
        if(getclass()=="")
        {
            System.out.println("Unoccupied");
        }
        else
        {
            System.out.println(getTeacher()+" "+getclass()+" "+getCourse());
        }

    }
    public String values()
    {
        if(getclass()!="")
            return (getCourse()+"\n"+getTeacher()+"\n"+getclass());
        else
            return ("\n\n\n");
    }
    public boolean isEmpty()
    {
        return (getclass().equals(""));
    }

    /**
     * @return the _teacher
     */
    public String getTeacher() {
        return _teacher;
    }

    /**
     * @return the _course
     */
    public String getCourse() {
        return _course;
    }

    /**
     * @return the _class
     */
    public String getclass() {
        return _class;
    }
}

//Bahawal_Khan_Baloch_cs171032
//Tarun_Kumar_cs171024
//Kumail_Raza_cs171095