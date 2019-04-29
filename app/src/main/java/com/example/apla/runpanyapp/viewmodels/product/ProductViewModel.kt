package com.example.apla.runpanyapp.viewmodels.product

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableField
import com.example.apla.runpanyapp.BaseApplication
import com.example.apla.runpanyapp.data.DataRepository
import com.example.apla.runpanyapp.data.local.db.entities.CommentEntity
import com.example.apla.runpanyapp.data.local.db.entities.ProductEntity
import com.example.apla.runpanyapp.viewmodels.BaseViewModel

class ProductViewModel(application: Application, repository: DataRepository,
                       private val mProductId: Int) : BaseViewModel(application) {

    val observableProduct: LiveData<ProductEntity>

    var product: ObservableField<ProductEntity> = ObservableField()

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    val comments: LiveData<List<CommentEntity>>

    init {

        comments = repository.loadComments(mProductId)
        observableProduct = repository.loadProduct(mProductId)
    }

    fun setProduct(product: ProductEntity) {
        this.product.set(product)
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

            return ProductViewModel(mApplication, mRepository, mProductId) as T
        }
    }
}