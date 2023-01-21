package com.assignment.repository

import com.assignment.data.database.DbHelper
import com.assignment.data.model.Workshop

class DatabaseRepository(
    private val DBHelper: DbHelper
) {

    fun addWorkShops(workShops: ArrayList<Workshop>) = DBHelper.addWorkshopDetails(workShops)
    fun apply(id: Int) = DBHelper.applied(id)
    fun getWorkShops(): ArrayList<Workshop> = DBHelper.getAllWorkShop()
    fun appliedWorkshops(): ArrayList<Workshop> = DBHelper.getAppliedWorkshops()
    fun deleteAppliedWorkshops() = DBHelper.deleteAppliedWorkshops()
}