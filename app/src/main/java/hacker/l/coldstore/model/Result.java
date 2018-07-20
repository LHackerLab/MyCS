package hacker.l.coldstore.model;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class Result {
    private String empPhone;
    private int userId;
    private String isActive;
    private String storeprofileId;
    private String storeName;
    private String storePhone;
    private String storeEmail;
    private String storeAddress;
    private String storeOwner;
    private String storeTiming;

    public String getStoreprofileId() {
        return storeprofileId;
    }

    public void setStoreprofileId(String storeprofileId) {
        this.storeprofileId = storeprofileId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreOwner() {
        return storeOwner;
    }

    public void setStoreOwner(String storeOwner) {
        this.storeOwner = storeOwner;
    }

    public String getStoreTiming() {
        return storeTiming;
    }

    public void setStoreTiming(String storeTiming) {
        this.storeTiming = storeTiming;
    }

    public String getStoreProfile() {
        return storeProfile;
    }

    public void setStoreProfile(String storeProfile) {
        this.storeProfile = storeProfile;
    }

    private String storeProfile;

    private String empName;
    private String empFName;
    private String empAddress;
    private String empType;
    private String empjoindate;
    private String empdob;
    private String empQly;
    private String empGender;
    private String empsalary;
    private String empProfile;
    private String empPassword;


    private String AdminName;
    private String AdminEmail;
    private String AdminPassword;
    private String AdminProfile;
    private String AdminPhone;
    private int AdminId;


    private String variety;
    private String rack;
    private int rackId;
    private String capacity;
    private int floorId;
    private int varietyId;
    private int floor;
// for inward data....

    private String userName;
    private String fatherName;
    private String userPhone;
    private String address;
    private String quantity;
    private String rent;
    private String byUser;
    private String advanced;
    private String caseType;
    private String grandTotal;
    private String time;
    private String date;
    private int inwardId;

    public int getOutwardId() {
        return outwardId;
    }

    public void setOutwardId(int outwardId) {
        this.outwardId = outwardId;
    }

    private int outwardId;

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    private int paymentId;
    //for vardana...
    private int vardanaId;

    public int getsRoomId() {
        return sRoomId;
    }

    public void setsRoomId(int sRoomId) {
        this.sRoomId = sRoomId;
    }

    private int sRoomId;
    private String name;
    private String phone;
    private String qty;
    private String type;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String amount;

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    private String departure;


    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVardanaId() {
        return vardanaId;
    }

    public void setVardanaId(int vardanaId) {
        this.vardanaId = vardanaId;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAdvanced() {
        return advanced;
    }

    public void setAdvanced(String advanced) {
        this.advanced = advanced;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return date;
    }

    public void setDay(String date) {
        this.date = date;
    }

    public int getInwardId() {
        return inwardId;
    }

    public void setInwardId(int inwardId) {
        this.inwardId = inwardId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getByUser() {
        return byUser;
    }

    public void setByUser(String byUser) {
        this.byUser = byUser;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }


    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpFName() {
        return empFName;
    }

    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }


    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getEmpjoindate() {
        return empjoindate;
    }

    public void setEmpjoindate(String empjoindate) {
        this.empjoindate = empjoindate;
    }

    public String getEmpdob() {
        return empdob;
    }

    public void setEmpdob(String empdob) {
        this.empdob = empdob;
    }

    public String getEmpQly() {
        return empQly;
    }

    public void setEmpQly(String empQly) {
        this.empQly = empQly;
    }

    public String getEmpsalary() {
        return empsalary;
    }

    public void setEmpsalary(String empsalary) {
        this.empsalary = empsalary;
    }


    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }


    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        AdminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
    }

    public String getAdminProfile() {
        return AdminProfile;
    }

    public void setAdminProfile(String adminProfile) {
        AdminProfile = adminProfile;
    }

    public int getAdminId() {
        return AdminId;
    }

    public void setAdminId(int adminId) {
        AdminId = adminId;
    }


    public String getAdminPhone() {
        return AdminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        AdminPhone = adminPhone;
    }


    public String getEmpProfile() {
        return empProfile;
    }

    public void setEmpProfile(String empProfile) {
        this.empProfile = empProfile;
    }


    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }


    public String getVarietyName() {
        return variety;
    }

    public void setVarietyName(String varietyName) {
        this.variety = varietyName;
    }


    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public int getRackId() {
        return rackId;
    }

    public void setRackId(int rackId) {
        this.rackId = rackId;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(int varietyId) {
        this.varietyId = varietyId;
    }


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

    public Result() {

    }
}
