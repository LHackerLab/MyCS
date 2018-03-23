package hacker.l.coldstore.model;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class Result {
    private String name;
    private String phone;
    private String Username;
    private String UserPhone;

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }

    private String varietyName;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    private String EmailId;
    private String Address;
    private String City;
    private String PinCode;
    private String EmergencyOne;
    private String EmergencyTwo;
    private String EmergencyThree;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getEmergencyOne() {
        return EmergencyOne;
    }

    public void setEmergencyOne(String emergencyOne) {
        EmergencyOne = emergencyOne;
    }

    public String getEmergencyTwo() {
        return EmergencyTwo;
    }

    public void setEmergencyTwo(String emergencyTwo) {
        EmergencyTwo = emergencyTwo;
    }

    public String getEmergencyThree() {
        return EmergencyThree;
    }

    public void setEmergencyThree(String emergencyThree) {
        EmergencyThree = emergencyThree;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private String barCode;
    private String Password;

    public String getSocialUs() {
        return socialUs;
    }

    public void setSocialUs(String socialUs) {
        this.socialUs = socialUs;
    }

    private String socialUs;
    private int loginId;
    private int rack;
    private int rackId;

    public int getRack() {
        return rack;
    }

    public void setRack(int rack) {
        this.rack = rack;
    }

    public int getRackId() {
        return rackId;
    }

    public void setRackId(int rackId) {
        this.rackId = rackId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    private int capacity;

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    private int scid;

    public int getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(int varietyId) {
        this.varietyId = varietyId;
    }

    private int varietyId;
    private int floor;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    private int floorId;

    public Result(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Result() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
