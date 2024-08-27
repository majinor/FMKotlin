package com.daffamuhtar.fmkotlin.util

class VehicleHelper {
    companion object {
        fun getVehicleName(
            vehicleId: String,
            vehicleBrand: String?,
            vehicleType: String?,
            vehicleVariant: String?,
            vehicleYear: String?,
            vehicleLicenseNumber: String?
        ): String {
            var vehicleName: String

            if (vehicleLicenseNumber != null) {
                vehicleName = "$vehicleBrand $vehicleType $vehicleVariant $vehicleYear\n$vehicleLicenseNumber"
            } else {
                vehicleName = "$vehicleBrand $vehicleType $vehicleVariant $vehicleYear"
            }
            return vehicleName
        }
    }
}