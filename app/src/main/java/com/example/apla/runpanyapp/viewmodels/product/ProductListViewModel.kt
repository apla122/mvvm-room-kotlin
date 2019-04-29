package com.example.apla.runpanyapp.viewmodels.product

import android.app.Application
import androidx.lifecycle.*
import com.example.apla.runpanyapp.BaseApplication
import com.example.apla.runpanyapp.data.DataRepository
import com.example.apla.runpanyapp.data.local.db.entities.ProductEntity
import com.example.apla.runpanyapp.viewmodels.BaseViewModel

class ProductListViewModel(application: Application) : BaseViewModel(application) {

    private val mRepository: DataRepository

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private val mObservableProducts: MediatorLiveData<List<ProductEntity>>

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val products: LiveData<List<ProductEntity>>
        get() = mObservableProducts

    init {

        mObservableProducts = MediatorLiveData()
        // set by default null, until we get data from the database.
        mObservableProducts.value = null

        mRepository = (application as BaseApplication).getRepository()
        val products = mRepository.products

        // observe the changes of the products from the database and forward them
        mObservableProducts.addSource(products, mObservableProducts::setValue)
    }

    fun searchProducts(query: String): LiveData<List<ProductEntity>> {
        return mRepository.searchProducts(query)
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    class Factory(private val mApplication: Application, private val mProductId: Int) : ViewModelProvider.NewInstanceFactory() {

        private val mRepository: DataRepository

        init {
            mRepository = (mApplication as BaseApplication).getRepository()
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return ProductListViewModel(mApplication) as T
        }
    }

}