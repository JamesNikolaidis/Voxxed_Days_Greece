package voxxed_days_greece.voxxeddays.models;

/**
 * Created by James Nikolaidis on 7/10/2017.
 */

public class workshops {

    public String workshop_brief;
    public String workshop_name;
    public String value;
    public String workshop_photo_url;
    public String workshop_time;

    public String getWorkshop_room() {
        return workshop_room;
    }

    public void setWorkshop_room(String workshop_room) {
        this.workshop_room = workshop_room;
    }

    public String workshop_room;

    public String getWorkshop_photo_url() {
        return workshop_photo_url;
    }

    public void setWorkshop_photo_url(String workshop_photo_url) {
        this.workshop_photo_url = workshop_photo_url;
    }

    public String getWorkshop_time() {
        return workshop_time;
    }

    public void setWorkshop_time(String workshop_time) {
        this.workshop_time = workshop_time;
    }

    public int day,workshop_id,speaker;

    public String getWorkshop_brief() {
        return workshop_brief;
    }

    public void setWorkshop_brief(String workshop_brief) {
        this.workshop_brief = workshop_brief;
    }

    public String getWorkshop_name() {
        return workshop_name;
    }

    public void setWorkshop_name(String workshop_name) {
        this.workshop_name = workshop_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWorkshop_id() {
        return workshop_id;
    }

    public void setWorkshop_id(int workshop_id) {
        this.workshop_id = workshop_id;
    }

    public int getSpeaker() {
        return speaker;
    }

    public void setSpeaker(int speaker) {
        this.speaker = speaker;
    }
}
