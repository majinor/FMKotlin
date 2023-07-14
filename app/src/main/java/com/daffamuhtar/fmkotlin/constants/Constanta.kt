package com.daffamuhtar.fmkotlin.constants

object Constanta {
    const val TAG = "TAG FM KOTLIN"

    //Ordertype
    const val ORDERTYPE_ADHOC = "adhoc"
    const val ORDERTYPE_MAINTENANCE = "maintenance"
    const val ORDERTYPE_NPM = "npm"
    const val ORDERTYPE_TIRE = "tire"

    //sharedpreferences
    const val my_shared_preferences = "my_shared_preferences"
    const val my_shared_preferences_showcase = "my_shared_preferences_showcase"
    const val EXTRA_COMPANYTYPE = "companytype"
    const val EXTRA_MESSAGE = "message"

    //Showcase
    const val EXTRA_SHOWCASE_HOME = "showcasehome"

    //Request
    const val CAMERA_REQUEST = 1
    const val CAMERA_REQUEST_INSPECTION = 11
    const val CAMERA_REQUEST_ADDITIONALPARTS = 10
    const val GALERY_REQUEST = 3
    const val CONTACT_REQUEST = 4
    const val SCANNER_REQUEST = 30
    const val SCANNER_REQUEST_TIRE_AFTER_REPAIR = 31
    const val SCANNER_REQUEST_USED_PART = 32

    //Reqcode
    const val REQ_CAMERA_TIRE_INSPECT_ITEM = "REQ_CAMERA_TIRE_INSPECT_ITEM"
    const val EXTRA_STAGE_GROUP = "EXTRA_STAGE_GROUP"

    //Problem
    const val EXTRA_FOTO = "fotmslh"
    const val EXTRA_MASALAH = "ketmslh"
    const val EXTRA_OTP = "otpcode"
    const val EXTRA_SOURCEID = "sourceid"
    const val EXTRA_FTL = "FTL"
    const val REPORT_DRIVER_INSPECTION = "1"
    const val REPORT_UM_PROBLEM = "2"
    const val REPORT_UM_STORING = "3"
    const val REPORT_UM_TIER = "4"
    const val REPORT_MECH_TIER = "5"

    //Tire
    const val EXTRA_UNIQUEID = "uniqueid"
    const val EXTRA_REQUIREDTIREID = "req_tireid"
    const val EXTRA_DETECTEDTIREID = "det_tireid"
    const val EXTRA_TIREID = "tireid"
    const val EXTRA_TIREPOSITIONID = "tirepositionid"
    const val EXTRA_TIREISVULCANIZED = "tireisvulcanized"
    const val EXTRA_TIREBRAND = "tirebrand"
    const val EXTRA_TIREPOSITIONNAME = "tirepositionname"
    const val EXTRA_TIREPHOTO = "tirephoto"
    const val EXTRA_TIRESIZE = "tiresize"
    const val EXTRA_CONDITION = "condition"
    const val EXTRA_FROMTIRENOTMATCH = "fromtirenotmatch"

    //Issue
    const val EXTRA_ISSUEID = "iid"

    //Vehicle
    const val EXTRA_VID = "vid"
    const val EXTRA_VPHOTO = "vphoto"
    const val EXTRA_VDIS = "vdis"
    const val EXTRA_VNAME = "vname"
    const val EXTRA_VBRAND = "vbrand"
    const val EXTRA_VTYPE = "vtype"
    const val EXTRA_VVAR = "vvar"
    const val EXTRA_VYEAR = "vyear"
    const val EXTRA_VLID = "vlid"
    const val EXTRA_VLICEN = "vlicen"
    const val EXTRA_VCHASSISTYPE = "vchassistype"
    const val EXTRA_DISTANCE_UNIT = "distanceUnit"
    const val EXTRA_VSTATUS = "EXTRA_VSTATUS"
    const val VEHICLE_CHASSIS_TYPE_CDE = "CDE"
    const val VEHICLE_CHASSIS_TYPE_CDD = "CDD"
    const val VEHICLE_CHASSIS_TYPE_TRN = "TRT"
    const val VEHICLE_CHASSIS_TYPE_MGD = "MGD"
    const val VEHICLE_CHASSIS_TYPE_VBR = "VBR"
    const val VEHICLE_CHASSIS_TYPE_WHL = "WHL"
    const val EXTRA_DNAME = "dname"
    const val EXTRA_DPHONE = "dphone"
    const val EXTRA_ISOURCE = "isource"
    const val EXTRA_USERIDATE = "idate"
    const val EXTRA_SPKID = "sid"
    const val EXTRA_STAGEID = "stid"
    const val EXTRA_STAGENAME = "EXTRA_STAGENAME"
    const val EXTRA_LATESTSTAGE = "lateststage"
    const val EXTRA_NOTESA = "nsa"
    const val EXTRA_SASSIGN = "sass"
    const val EXTRA_IS_USING_TMS = "EXTRA_IS_USING_TMS"
    const val EXTRA_INSPECTID = "insid"
    const val EXTRA_INSTYPE = "instype"
    const val EXTRA_CUSTOM_ORDER_MECHANIC_PART_ID = "EXTRA_CUSTOM_ORDER_MECHANIC_PART_ID"
    const val EXTRA_ODO = "odo"
    const val session_status = "session_status"
    const val EXTRA_TOKEN = "token"
    const val EXTRA_USERID = "userid"
    const val EXTRA_USERLEVELID = "userlevelid"
    const val EXTRA_NAME = "name"
    const val EXTRA_UNAME = "uname"
    const val EXTRA_EMAIL = "email"
    const val EXTRA_PHONE = "phone"
    const val EXTRA_PHOTO = "photo"
    const val EXTRA_ADDRESS = "address"
    const val EXTRA_POSITION = "position"
    const val EXTRA_KTP = "ktp"
    const val EXTRA_NIK = "nik"
    const val EXTRA_BDATE = "bdate"
    const val EXTRA_BPLACE = "bplace"
    const val EXTRA_KTPPHOTO = "ktpphoto"
    const val EXTRA_IS_USING_DYNAMIC_MAX_PHOTO = "EXTRA_IS_USING_DYNAMIC_MAX_PHOTO"
    const val EXTRA_MAX_PHOTO = "EXTRA_MAX_PHOTO"

    //Notif
    const val EXTRA_NOTIFID = "notifid"

    //Workshop
    const val EXTRA_WORKSHOPID = "workshopid"
    const val EXTRA_LOCOPTION = "locoption"
    const val EXTRA_RATING = "rating"

    //Part
    const val EXTRA_ORDERID = "orderid"
    const val EXTRA_ORDERTYPE = "ordertype"
    const val EXTRA_PBID = "pbid"
    const val EXTRA_PARTID = "partid"
    const val EXTRA_UNIQUE_PART_ID = "EXTRA_UNIQUE_PART_ID"
    const val EXTRA_UNIQUE_PART_ID_LIST = "EXTRA_UNIQUE_PART_ID"
    const val EXTRA_PART_SKU = "EXTRA_PART_SKU"
    const val EXTRA_IS_ALLOW_TO_SKIP = "EXTRA_IS_ALLOW_TO_SKIP"
    const val EXTRA_ISSTORING = "isstoring"
    const val EXTRA_REAL_PART_QUANTITY = "EXTRA_REAL_PART_QUANTITY"
    const val EXTRA_PART_QUANTITY_LEFT = "EXTRA_PART_QUANTITY_LEFT"
    const val EXTRA_REQCODE = "reqcode"
    const val EXTRA_REQTYPE = "reqtype"
    const val TAG_CHANGEPASS = "p2"
    const val TAG_EDITPROFILE = "p1"
    const val TAG_OPS = "aa"

    //Scanner
    const val EXTRA_IS_POST_REGISTER_USED_PART_FAILURE = "EXTRA_IS_POST_REGISTER_USED_PART_FAILURE"

    //preview
    const val EXTRA_PREVIEW = "preview"
    const val EXTRA_PATH = "path"

    //Warehouse
    const val EXTRA_POID = "poid"
    const val EXTRA_DATE = "date"
    const val EXTRA_SUPPLIER_NAME = "supppliername"
    const val EXTRA_WORKSHOP_NAME = "EXTRA_WORKSHOP_NAME"
    const val EXTRA_ADMIN_NAME = "adminname"
    const val EXTRA_SKU = "sku"
    const val EXTRA_PART_NAME = "partname"
    const val EXTRA_PART_QUANTITY = "EXTRA_PART_QUANTITY"
    const val EXTRA_PART_ID_FROM_FLEETIFY = "partsidfromfleetiy"
    const val EXTRA_RACKID = "rackid"
    const val EXTRA_PACKAGEID = "rackid"
    const val EXTRA_IS_SKIP = "RESULT"
}