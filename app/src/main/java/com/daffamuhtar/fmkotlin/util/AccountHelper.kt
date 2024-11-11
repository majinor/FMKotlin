package com.daffamuhtar.fmkotlin.util

import com.daffamuhtar.fmkotlin.data.model.Profile
import com.daffamuhtar.fmkotlin.data.response.ProfileResponse

class AccountHelper {

    companion object {
        fun mapProfile(profileResponse: List<ProfileResponse>): Profile {
            var profile = Profile(
                profileResponse[0].mechanicName,
                profileResponse[0].mechanicPhoto,
                profileResponse[0].mechanicPhone,
                profileResponse[0].mechanicEmail,
                profileResponse[0].mechanicUsername,
                profileResponse[0].mechanicWorkshop,
                profileResponse[0].mechanicNIK,
                profileResponse[0].mechanicKTP,
                profileResponse[0].mechanicKTPPhoto,
                profileResponse[0].mechanicBirthDate,
                profileResponse[0].mechanicBirthPlace,
                profileResponse[0].mechanicAddress,
            )

            return profile
        }
    }
}