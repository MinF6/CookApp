package com.zongmin.cook.data.source

import androidx.lifecycle.LiveData
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.source.remote.CookRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import com.zongmin.cook.data.Result

class DefaultCookRepository(
    private val cookRemoteDataSource: CookDataSource,
//    private val stylishLocalDataSource: CookDataSource,
//    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CookRepository {

    //    override suspend fun getMarketingHots(): Result<List<HomeItem>> {
//        return stylishRemoteDataSource.getMarketingHots()
//    }
//
//    override suspend fun getProductList(type: String, paging: String?): Result<ProductListResult> {
//        return stylishRemoteDataSource.getProductList(type = type, paging = paging)
//    }
//
//    override suspend fun getUserProfile(token: String): Result<User> {
//        return stylishRemoteDataSource.getUserProfile(token)
//    }
//
//    override suspend fun userSignIn(fbToken: String): Result<UserSignInResult> {
//        return stylishRemoteDataSource.userSignIn(fbToken)
//    }
//
//    override suspend fun checkoutOrder(
//        token: String,
//        orderDetail: OrderDetail
//    ): Result<CheckoutOrderResult> {
//        return stylishRemoteDataSource.checkoutOrder(token, orderDetail)
//    }
//
//    override fun getProductsInCart(): LiveData<List<Product>> {
//        return stylishLocalDataSource.getProductsInCart()
//    }
//
//    override suspend fun isProductInCart(id: Long, colorCode: String, size: String): Boolean {
//        return stylishLocalDataSource.isProductInCart(id, colorCode, size)
//    }
//
//    override suspend fun insertProductInCart(product: Product) {
//        stylishLocalDataSource.insertProductInCart(product)
//    }
//
//    override suspend fun updateProductInCart(product: Product) {
//        stylishLocalDataSource.updateProductInCart(product)
//    }
//
//    override suspend fun removeProductInCart(id: Long, colorCode: String, size: String) {
//        stylishLocalDataSource.removeProductInCart(id, colorCode, size)
//    }
//
//    override suspend fun clearProductInCart() {
//        stylishLocalDataSource.clearProductInCart()
//    }
//
//    override suspend fun getUserInformation(key: String?): String {
//        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
//    }
    override suspend fun getRecipes(): Result<List<Recipes>> {

        return cookRemoteDataSource.getRecipes()
    }
}
