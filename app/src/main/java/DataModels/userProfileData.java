package DataModels;

public class userProfileData {

    private String uid, name, bloodGroup, divison, district, phone, date, availability;

    public userProfileData(String uid, String name, String bloodGroup, String divison, String district, String phone, String date, String availability) {
        this.uid = uid;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.divison = divison;
        this.district = district;
        this.phone = phone;
        this.date = date;
        this.availability = availability;
    }

    public userProfileData() {

    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDivison() {
        return divison;
    }

    public void setDivison(String divison) {
        this.divison = divison;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
