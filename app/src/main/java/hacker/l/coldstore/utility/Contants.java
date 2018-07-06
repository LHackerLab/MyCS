package hacker.l.coldstore.utility;

import android.os.Environment;

/**
 * Created by lalit on 7/25/2017.
 */
public class Contants {
    public static final Boolean IS_DEBUG_LOG = true;

    public static final String LOG_TAG = "emergency";
    public static final String APP_NAME = "appName"; // Do NOT change this value; meant for user preference

    public static final String DEFAULT_APPLICATION_NAME = "emergency";

    public static final String APP_DIRECTORY = "/E" + DEFAULT_APPLICATION_NAME + "Directory/";
    public static final String DATABASE_NAME = "emergency.db";// Environment.getExternalStorageDirectory() +  APP_DIRECTORY + "evergreen.db";

    public static String SERVICE_BASE_URL = "http://lalitsingh2.esy.es/coldstore/";

    public static String outputBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String outputDirectoryLocation = outputBasePath + "/evergreenUnzipped/";


    public static final int LIST_PAGE_SIZE = 50;
    public static String InternetMessage = " You are Online.";
    public static final String BAD_NETWORK_MESSAGE = "We are trying hard to get latest content but the network seems to be slow. " +
            "You may continue to use app and get latest with in the app itself. ";


    public static final String OFFLINE_MESSAGE = "Oops! You are Offline. Please check your Internet Connection.";
    public static final String SEND_MESSAGE = "OTP Send to Your Phone Number Successfully";
    public static final String ADD_NEW_ADDRESS = "Add New Address Successfully";
    public static final String MESSAGE_FOR_UNREGISTRED_USER = "You Are Not a Registered User!Please Login First..";
    public static final String DoNot_NEW_ADDRESS = "Your Address Do Not Add Successfully";
    public static final String SEND_OTP_MESSAGE = "Your Registration Successfully";
    public static final String DoNot_SEND_OTP_MESSAGE = "OTP NOT Correct.Please Enter Valid OTP ";
    public static final String Dont_SEND_MESSAGE = "OTP Do Not Send Successfully";
    public static final String Dont_GetAddress_MESSAGE = "Some Problem For Geting Address";
    public static final String No_DATA_FOUND_MESSAGE = "No Record Found";

    public static final String Admin = "FetchAdmin.php";
    public static final String getSplashImage = "getSplashImage.php";
    public static final String setSplashImage = "setSplashImage.php";
    public static final String User = "FetchUser.php";
    public static final String AddUser = "addUser.php";
    public static final String AddInward = "addInward.php";
    public static final String getAlInward = "getAllInward.php";
    public static final String deleteInward = "deleteInward.php";
    public static final String AddFloor = "AddFloor.php";
    public static final String getAllFloor = "getAllFloor.php";
    public static final String updateFloor = "updateFloor.php";
    public static final String deleteflor = "deleteFloor.php";
    public static final String updateUser = "updateUser.php";
    public static final String getAllUsers = "getAllUsers.php";
    public static final String deleteUser = "deleteUser.php";

    public static final String addVariety = "addVariety.php";
    public static final String getAllVariety = "getAllVariety.php";
    public static final String updateVariety = "updateVariety.php";
    public static final String deleteVariety = "deleteVariety.php";

    public static final String addRack = "addRack.php";
    public static final String getAllRack = "getAllRack.php";
    public static final String updateRack = "updateRack.php";
    public static final String deleteRack = "deleteRack.php";

    public static final String addVardana = "addVardana.php";
    public static final String getAllVardana = "getAllVardana.php";
    public static final String updateVardana = "updateVardana.php";
    public static final String deleteVardana = "deleteVardana.php";

    public static final String addStoreRoom = "addStoreRoom.php";
    public static final String getAllStoreRoom = "getAllStoreRoom.php";
    public static final String updateStoreRoom = "updateStoreRoom.php";
    public static final String deleteStoreRoom = "deleteStoreRoom.php";

    public static final String addPayment = "addPayment.php";
    public static final String getAllPayment = "getAllPayment.php";
    public static final String deletePayment = "deletePayment.php";

    public static final String addOutward = "addOutward.php";
    public static final String getAllOutward = "getAllOutward.php";
    public static final String deleteOutward = "deleteOutward.php";
}
