package com.example.jeepni.util

import com.example.jeepni.R
import com.example.jeepni.core.data.model.NotificationContent

object Constants {
    const val CHANNEL_ID = "jeepni_by_algofirst_notification_channel_id"
    //shown in user settings
    const val CHANNEL_NAME = "JeepNi Checkup Notifications"

    const val DRIVING_MODE_TURNED_ON_NOTIFICATION = 0
    const val CHANGE_OIL_NOTIFICATION = 1
    const val LTFRB_INSPECTION_NOTIFICATION = 2
    const val LTO_INSPECTION_NOTIFICATION = 3
    const val TIRE_CHANGE_NOTIFICATION = 4
    const val SIDE_MIRRORS_REPAIR_NOTIFICATION = 5
    const val WIPERS_REPAIR_NOTIFICATION = 6
    const val ENGINE_REPAIR_NOTIFICATION = 7
    const val SEATBELT_REPAIR_NOTIFICATION = 8
    const val BATTERY_REPAIR_NOTIFICATION = 9

    const val NOTIFICATION_OBJECT = "notification_object"

    const val FIRST_NOTIFICATION_ID = 999

    const val TIRES = "Tires"
    const val MIRRORS = "Mirrors"
    const val WIPERS = "Wipers"
    const val ENGINE = "Engine"
    const val SEATBELT = "Seatbelt"
    const val BATTERY = "Battery"
    const val OIL_CHANGE = "Oil Change"
    const val LTFRB_INSPECTION = "LTFRB Inspection"
    const val LTO_INSPECTION = "LTO Inspection"

    val COMPONENTS = listOf(
        TIRES,
        MIRRORS,
        WIPERS,
        ENGINE,
        SEATBELT,
        BATTERY,
        OIL_CHANGE,
        LTFRB_INSPECTION,
        LTO_INSPECTION,
    )
    val ICON_MAP = mapOf<String, Int>(
        TIRES to R.drawable.tire,
        OIL_CHANGE to R.drawable.oil,
        MIRRORS to R.drawable.mirror,
        LTFRB_INSPECTION to R.drawable.checked,
        LTO_INSPECTION to R.drawable.checked,
        SEATBELT to R.drawable.safety_belt,
        WIPERS to R.drawable.wiper,
        ENGINE to R.drawable.car_engine,
        BATTERY to R.drawable.car_battery,
    ).withDefault { R.drawable.app_logo_light }

    val NOTIFICATION_MAP = mapOf<String, NotificationContent>(
        TIRES to NotificationContent(
            notificationId = TIRE_CHANGE_NOTIFICATION,
            title = "Repair Your Tires Today",
            content = "Your tires may be in need of a repair. Consider repairing them as soon as possible"
        ),
        MIRRORS to NotificationContent(
            notificationId = SIDE_MIRRORS_REPAIR_NOTIFICATION,
            title = "Repair Your Side Mirrors Today",
            content = "Your side mirrors may be in need of a repair. Consider repairing them as soon as possible"
        ),
        WIPERS to NotificationContent(
            notificationId = Constants.WIPERS_REPAIR_NOTIFICATION,
            title = "Repair Your Wipers Today",
            content = "Your wipers may be in need of a repair. Consider repairing them as soon as possible"
        ),
        ENGINE to NotificationContent(
            notificationId = Constants.ENGINE_REPAIR_NOTIFICATION,
            title = "Repair Your Engine Today",
            content = "Your engine may be malfunctioning. Consider repairing it as soon as possible"
        ),
        SEATBELT to NotificationContent(
            notificationId = Constants.SEATBELT_REPAIR_NOTIFICATION,
            title = "Repair Your Seatbelt Today",
            content = "Your seatbelt may be in need of a repair. Consider repairing it as soon as possible"
        ),
        BATTERY to NotificationContent(
            notificationId = Constants.BATTERY_REPAIR_NOTIFICATION,
            title = "Replace Your Battery Today",
            content = "Your battery may be malfunctioning. Consider replacing it as soon as possible"
        ),
        OIL_CHANGE to NotificationContent(
            notificationId = Constants.CHANGE_OIL_NOTIFICATION,
            title = "Change Your Oil Today",
            content = "It's been three months since your last known oil change, please check your vehicle's oil"
        ),
        LTFRB_INSPECTION to NotificationContent(
            notificationId = Constants.LTFRB_INSPECTION_NOTIFICATION,
            title = "Prepare for LTFRB inspection",
            content ="It's been a while since the last known LTFRB inspection, please prepare for any possible upcoming inspections"
        ),
        LTO_INSPECTION to NotificationContent(
            notificationId = Constants.LTO_INSPECTION_NOTIFICATION,
            title = "Prepare for LTO inspection",
            content ="It's been a while since the last known LTO inspection, please prepare for any possible upcoming inspections"
        ),
    ).withDefault { alarmName ->
        NotificationContent(
            notificationId = alarmName.hashCode(),
            title = "JeepNi Reminder: ${alarmName.uppercase()}",
            content = "Be reminded of ${alarmName.uppercase()}. Take care of your Jeepney!"
        )
    }
}