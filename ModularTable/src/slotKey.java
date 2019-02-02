public class slotKey
{
    private final String venue;
    private final String timeslot;

    public slotKey(String _venue, String _timeslot)
    {
        venue = _venue;
        timeslot = _timeslot;
    }
    public slotKey()
    {
        venue = "";
        timeslot = "";
    }
    public void print()
    {
        System.out.println(venue+"|"+timeslot);
    }
    @Override
    public int hashCode()
    {
        int room = Integer.parseInt(venue.substring(3));
        room*=100;
        int time = Integer.parseInt(timeslot.substring(0,2) + timeslot.substring(3,5));
        int result = room+time;
        return result;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if  (obj == null || getClass()!= obj.getClass()) return false;
        final slotKey key = (slotKey) obj;
        if ((this.venue == null) ? (key.venue != null) : !this.venue.equals(key.venue)) {
            return false;
        }

        if (this.timeslot.equals(key.timeslot)){
            return true;
        }
        return true;
    }

}

//Bahawal_Khan_Baloch_cs171032
//Tarun_Kumar_cs171024
//Kumail_Raza_cs171095