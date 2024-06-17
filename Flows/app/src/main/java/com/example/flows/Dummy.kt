//package com.example.flows
//
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//
////producer
//fun getShippingDetails(userList: List<User>):Flow<ShippingDetail>{
//    return flow {
//        userList.forEach{
//            val user=getUser(it.id)
//            val shippingAddress=getShippingAddress(user.address)
//            val shippingDetails=calculateShippingCharges(shippingAddress)
//        }
//    }
//}
//
//fun main(){
//    val userList=getUserList()
//    val shippingDetails: Flow<ShippingDetail> = getShippingDetails(userList)
//    //when collect is called or consumer is present then only producer will produce
//}