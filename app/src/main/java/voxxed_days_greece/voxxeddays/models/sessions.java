package voxxed_days_greece.voxxeddays.models;

/**
 * Created by James Nikolaidis on 6/16/2017.
 */

public class sessions {
    public String brief="",name="",room="",time;
    public long id=0,speaker=0;
    public boolean keynote=false;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public long getSpeaker() {
        return speaker;
    }

    public void setSpeaker(long speaker) {
        this.speaker = speaker;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isKeynote() {
        return keynote;
    }

    public void setKeynote(boolean keynote) {
        this.keynote = keynote;
    }
}
